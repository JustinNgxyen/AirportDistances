//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-F23
//
// Description:
// An class that uses a hashed dictionary to retrieve values using keys.
//

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V>
{
	
	private int numberOfEntries;
	private int collisionCount;
	private final static int DEFAULT_CAPACITY = 100;
	private Entry<K, V>[] hashTable;
	private int tableSize;
	private boolean integrityOK = false;
	protected final Entry<K, V> AVAILABLE = new Entry<>(null, null);
	
	/** Creates an empty dictionary with a default capacity of 5. */
	public HashedDictionary()
	{
		this(DEFAULT_CAPACITY);
	}
	
	/** Creates an empty dictionary with a specified capacity.
	 @param initialCapacity The given capacity. */
	public HashedDictionary(int initialCapacity)
	{
		numberOfEntries = 0;
		tableSize = getNextPrime(initialCapacity);
	
		@SuppressWarnings("unchecked")
		Entry<K, V>[] temp = (Entry<K, V>[])new Entry[tableSize];
		hashTable = temp;
		integrityOK = true;
	}
	
	// Throws an exception if this object is not initialized.
	private void checkIntegrity()
	{
		if (!integrityOK)
			throw new SecurityException("HashedDictionary object is corrupt.");
	}
	
	/** Returns the next prime number. 
	 @param n The number to be turned into the next prime number.
	 @return The integer number of the next prime number. */
	public int getNextPrime(int n) 
	{

	    boolean isPrime;
	    n++;
	    while (true) 
	    {
	        int sqrt = (int) Math.sqrt(n);
	        isPrime = true;
	        for (int i = 2; i <= sqrt; i++) 
	        {
	            if (n % i == 0) 
	            	isPrime = false;
	        }
	        if (isPrime)
	            return n;
	        else
	            n++;
	    }
	}
	
	/** Adds a new entry to the dictionary. It replaces the corresponding value
	 if the given search key already exists in the dictionary.
	 @param key An object search key of the new entry.
	 @param value An object associated with the search key.
	 @return Either null if the new entry was added to the dictionary
	 or the value that was associated with key if that value
	 was replaced. */
	public V add(K key, V value)
	{
		checkIntegrity();
		if((key == null) || (value == null))
			throw new IllegalArgumentException("Cannot add null to this dictionary.");
		
		int index = getHashIndex(key);
		V oldValue;
		index = linearProbe(index, key);
		
			
		if((hashTable[index] == null) || (hashTable[index] == AVAILABLE))
		{
			hashTable[index] = new Entry<>(key, value);
			numberOfEntries++;	
			return null;	
		}
		else
		{
			oldValue = hashTable[index].getValue(); 				
			hashTable[index].setValue(value);	
		}
		
		return oldValue;
	}
	
	// Returns the hash index for the given key
	private int getHashIndex(K key) 
	{
		 int hashIndex = Math.abs(key.hashCode()) % hashTable.length;   
		 return hashIndex;
	}

	/** Performs linear probing to resolve collisions in the hash table.
	 @param index The starting index for probing.
	 @param key The key to search for or to probe.
	 @return The index where the key can be placed or is found after probing. */
	private int linearProbe(int index, K key)
	{
		checkIntegrity();
		boolean found = false;
		int availableStateIndex = -1;
		
		if (hashTable[index] != null)
		    collisionCount++; 
		
		while(!found && (hashTable[index] != null))
		{
			if(hashTable[index] != AVAILABLE)
			{
				if(key.equals(hashTable[index].getKey()))
					found = true;
				else
					index = (index + 1) % hashTable.length;
			}
			else
			{
				if(availableStateIndex == -1) 
					availableStateIndex = index;
				index = (index + 1) % hashTable.length;
			}
		}
		if(found || availableStateIndex == -1)
			return index;
		else
			return availableStateIndex;
	}
	
	/** Retrieves from this dictionary the value associated with a given
	 search key.
	 @param key An object search key of the entry to be retrieved.
	 @return Either the value that is associated with the search key
	 or null if no such object exists. */
	public V getValue(K key)
	{
		checkIntegrity();
		V result = null;
		
		int index = getHashIndex(key);
		while((hashTable[index] != null) && (hashTable[index] != AVAILABLE) && !hashTable[index].getKey().equals(key))
			index = (index + 1) % tableSize;
		
		if((hashTable[index] != null) && (hashTable[index] != AVAILABLE))
			result = hashTable[index].getValue();
		
		return result;
		
	}
	
	/** Creates an iterator that traverses all search keys in this dictionary.
	 @return An iterator that provides sequential access to the search
	 keys in the dictionary. */
	public Iterator<K> getKeyIterator()
	{
	    return new KeyIterator();
	}
	
	/** Returns the number of collisions that occurred. Defined as when a new key is added to the
	 dictionary and the entry at the hash index is occupied.
	 @return The integer number of collisions. */
	public int getCollisionCount()
	{
		return collisionCount;
	}

	public V remove(K key)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	public boolean contains(K key)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	public Iterator<V> getValueIterator()
	{
		return new ValueIterator();
	}
	
	public boolean isEmpty()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	public int getSize()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	public void clear()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	/* A class that creates a key-value entry into the hash table. 
	 Can also retrieve the key and value and set the value. */
	private class Entry<S, T>
	{
		private S key;
		private T value;
		
		private Entry(S searchKey, T dataValue)
		{
			key = searchKey;
			value = dataValue;
		}
		
		private S getKey()
		{
			return key;
		}
		
		private T getValue()
		{
			return value;
		}
		
		private void setValue(T dataValue)
		{
			value = dataValue;
		}
		
	}
	
	//Traverses through the keys in a hash table.
	private class KeyIterator implements Iterator<K>
	{
		private int currentIndex;
		private int numberLeft;
		
		private KeyIterator()
		{
			currentIndex = 0;
			numberLeft = numberOfEntries;
		}
		
		public boolean hasNext()
		{
			return numberLeft > 0;
		}

		public K next() 
		{
			K result = null;
			
			if(hasNext())
			{
				while((hashTable[currentIndex] == null) || (hashTable[currentIndex] == AVAILABLE))
				{
					currentIndex++;
				}
				
				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			}
			else
				throw new NoSuchElementException();
			
			return result;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
	
	//Traverses through the values in a hash table.
		private class ValueIterator implements Iterator<V>
		{
			private int currentIndex;
			private int numberLeft;
			
			private ValueIterator()
			{
				currentIndex = 0;
				numberLeft = numberOfEntries;
			}
			
			public boolean hasNext()
			{
				return numberLeft > 0;
			}

			public V next() 
			{
				V result = null;
				
				if(hasNext())
				{
					while((hashTable[currentIndex] == null) || (hashTable[currentIndex] == AVAILABLE))
					{
						currentIndex++;
					}
					
					result = hashTable[currentIndex].getValue();
					numberLeft--;
					currentIndex++;
				}
				else
					throw new NoSuchElementException();
				
				return result;
			}
			
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		}
	
	

}
