package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
	}

	@Override
	public T top() {
		if (isEmpty()) {
			return null;
		}
		return array[top];
	}

	@Override
	public boolean isEmpty() {
		return (this.top == -1);
	}

	@Override
	public boolean isFull() {
		return (this.top == this.array.length -1);
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element == null) return;
		
		if (isFull()) {
			throw new StackOverflowException();
		}
		this.top++;
		this.array[top] = element;
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		}
		T value = this.array[top];
		this.top--;
		return value;
	}

}
