//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-F23
//
// Description:
// An class that creates a stack of objects using nodes.
//

import java.util.EmptyStackException;


public class LinkedStack<T> implements StackInterface<T> 
{
	
	private Node topNode;
	
	/** Adds a new entry to the top of the stack.
	 @param newEntry The object to be added to the top of the stack. */
	public void push(T newEntry)
	{
		Node newNode = new Node(newEntry, topNode);
		topNode = newNode;
	}
	
	/** Removes the entry at the top of the stack.
	 @return The object at the top of the stack.
	 @throws EmptyStackException if the stack is empty. */
	public T pop()
	{
		T top;
		if(topNode != null) {
			top = peek();
			topNode = topNode.getNextNode();
		}else
			throw new EmptyStackException();
		
		return top; 
	}
	
	/** Retrieves the entry at the top of the stack.
	 @return The object at the top of the stack. 
	 @throws EmptyStackException if the stack is empty. */
	public T peek()
	{
		if(isEmpty())
			throw new EmptyStackException();
		else
			return topNode.getData();
	}
	
	/** Checks if the stack is empty or not.
	 @return True if the stack is empty, false if not. */
	public boolean isEmpty() {
		return topNode == null;
	}
	
	/** Removes all entries from the stack. */
	public void clear()
	{
		while(!isEmpty())
			pop();
	}
	
	
	private class Node
	{
		private T data;
		private Node next;
		
		private Node(T dataPortion)
		{
			this(dataPortion, null);
		}
		
		private Node(T dataPortion, Node nextNode)
		{
			data = dataPortion;
			next = nextNode;
		}
		
		private T getData()
		{
			return data;
		}
		
		private Node getNextNode()
		{
			return next;
		}
		
	}
	

}
