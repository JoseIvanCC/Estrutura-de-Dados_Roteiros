package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (!isFull()) top.insert(element);
		else {
			throw new StackOverflowException();
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		}
		T element = ((RecursiveDoubleLinkedListImpl<T>) top).getLast();
		top.removeLast();
		return element;
	}

	@Override
	public T top() {
		RecursiveDoubleLinkedListImpl<T> newElement = (RecursiveDoubleLinkedListImpl<T>) top;
		return newElement.getLast();
	}

	@Override
	public boolean isEmpty() {
		return (top.isEmpty());
	}

	@Override
	public boolean isFull() {
		return (top.size() == this.size);
	}

}
