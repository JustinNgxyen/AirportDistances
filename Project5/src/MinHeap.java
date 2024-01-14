//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// A class that creates a heap in which the value in each internal node is 
// smaller than or equal to the values in the children of that node.
//

public class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T> 
{
	
	private T[] heap;
	private int lastIndex;
	
	private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 64;
	private static final int MAX_CAPACITY = 10000;
	
	public MinHeap()
	{
		this(DEFAULT_CAPACITY);
	}
	
	public MinHeap(int initialCapacity)
	{
		if(initialCapacity < DEFAULT_CAPACITY)
			initialCapacity = DEFAULT_CAPACITY;
		else
			checkCapacity(initialCapacity);
		
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[])new Comparable[initialCapacity + 1];
		heap = tempHeap;
		lastIndex = 0;
		integrityOK = true;
	}
	
	// Checks the capacity of the initialized heap.
	private void checkCapacity(int capacity)
	{
		if (capacity > MAX_CAPACITY)
		throw new IllegalStateException("Attempt to create a heap whose " +
		"capacity exeeds allowed " +
		"maximum of " + MAX_CAPACITY);
	}
	
	// Throws an exception if this object is not initialized.
	private void checkIntegrity()
	{
		if (!integrityOK)
			throw new SecurityException("Heap object is corrupt.");
	}
	
	// Throws an exception if the heap is full. 
	public void ensureCapacity()
	{
		if(heap == null || lastIndex >= heap.length)
            throw new IllegalStateException("Heap is at full capacity");
			
	}
	
	/** Adds a new entry to this heap.
	 @param newEntry An object to be added. */
	public void add(T newEntry)
	{
		checkIntegrity();
		int newIndex = lastIndex + 1;
		int parentIndex = newIndex / 2;
		while((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0)
		{
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex / 2;
		}
		
		heap[newIndex] = newEntry;
		lastIndex++;
		ensureCapacity();
	}
	
	/** Removes and returns the largest item in this heap.
	@return Either the largest object in the heap or,
	if the heap is empty before the operation, null. */
	public T removeMin()
	{
		checkIntegrity();
		T root = null;
		
		if(!isEmpty())
		{
			root = heap[1];
			heap[1] = heap[lastIndex];
			lastIndex--;
			reheap(1);
		}
		
		return root;
	}
	
	// Transforms a semiheap to a heap.
	private void reheap(int rootIndex)
	{
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;
		while(!done && (leftChildIndex <= lastIndex))
		{
			int smallerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			
			if((rightChildIndex <= lastIndex) && 
					heap[rightChildIndex].compareTo(heap[smallerChildIndex]) < 0)
				smallerChildIndex = rightChildIndex;
			
			if(orphan.compareTo(heap[smallerChildIndex]) > 0)
			{
				heap[rootIndex] = heap[smallerChildIndex];
				rootIndex = smallerChildIndex;
				leftChildIndex = 2* rootIndex;
				
			}
			else
				done = true;
		}
		
		heap[rootIndex] = orphan;
	}
	 
	/** Retrieves the largest item in this heap.
	 @return Either the largest object in the heap or, if the heap is empty, null. */
	public T getMin()
	{
		checkIntegrity();
		T root = null;
		if(!isEmpty())
			root = heap[1];
		return root;
	}
	
	/** Detects whether this heap is empty.
	 @return True if the heap is empty, or false otherwise. */
	public boolean isEmpty()
	{
		return lastIndex < 1;
	}
	
	/** Gets the size of this heap.
	 @return The number of entries currently in the heap. */
	public int getSize()
	{
		return lastIndex;
	}
	
	/** Removes all entries from this heap. */
	public void clear()
	{
		checkIntegrity();
		while(!isEmpty()) 
		{
			heap[lastIndex] = null;
			lastIndex--;
		}
		lastIndex = 0;
	}


}
