//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// A class that uses a minheap to store entries based on their priorities.
//

public class PriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> 
{
	
	MinHeapInterface<T> minHeap;
	
	public PriorityQueue()
	{
		this.minHeap = new MinHeap<T>();
	}
	
	/** Adds a new entry to this priority queue.
	@param newEntry An object to be added. */
	@Override
	public void add(T newEntry)
	{
		minHeap.add(newEntry);
	}
	
	/** Removes and returns the entry having the highest priority.
	@return Either the object having the highest priority or, if the
	priority queue is empty before the operation, null. */
	@Override
	public T remove()
	{
		return minHeap.removeMin();
	}

	/** Retrieves the entry having the highest priority.
	@return Either the object having the highest priority or, if the
	priority queue is empty, null. */
	@Override
	public T peek()
	{
		return minHeap.getMin();
	}
	
	/** Detects whether this priority queue is empty.
	@return True if the priority queue is empty, or false otherwise. */
	@Override
	public boolean isEmpty()
	{
		return minHeap.isEmpty();
	}
	
	/** Gets the size of this priority queue.
	@return The number of entries currently in the priority queue. */
	@Override
	public int getSize()
	{
		return minHeap.getSize();
	}

	/** Removes all entries from this priority queue. */
	@Override
	public void clear()
	{
		minHeap.clear();
	}


}
