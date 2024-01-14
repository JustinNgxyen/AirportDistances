//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-F23
//
// Description:
// An interface that lists the various methods of a stack of objects.
//

public interface StackInterface<T> 
{
	/** Adds a new entry to the top of the stack.
	 @param newEntry The object to be added to the top of the stack. */
	public void push(T newEntry);
	
	/** Removes the entry at the top of the stack.
	 @return The object at the top of the stack.
	 @throws EmptyStackException if the stack is empty. */
	public T pop();
	
	/** Retrieves the entry at the top of the stack.
	 @return The object at the top of the stack. 
	 @throws EmptyStackException if the stack is empty. */
	public T peek();
	
	/** Checks if the stack is empty or not.
	 @return True if the stack is empty, false if not. */
	public boolean isEmpty();
	
	/** Removes all entries from the stack. */
	public void clear();

}
