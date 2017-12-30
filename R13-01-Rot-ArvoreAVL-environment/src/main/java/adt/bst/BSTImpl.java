package adt.bst;

import adt.bt.Util;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	protected int height(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		}

		int heightLeft = height((BSTNode<T>) node.getLeft());
		int heighRight = height((BSTNode<T>) node.getRight());

		int max = (heightLeft > heighRight) ? heightLeft : heighRight;

		return 1 + max;
	}

	@Override
	public BSTNode<T> search(T element) {
		if (this.root.isEmpty()) {
			return new BSTNode<T>();
		} else {
			return search(this.root, element);
		}
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.getData().equals(element)) {
			return node;
		} else {
			if (node.getData().compareTo(element) > 0) {
				if (node.getLeft().isEmpty()) {
					return new BSTNode<T>();
				} else {
					return search((BSTNode<T>) node.getLeft(), element);
				}
			} else {
				if (node.getRight().isEmpty()) {
					return new BSTNode<T>();
				} else {
					return search((BSTNode<T>) node.getRight(), element);
				}
			}
		}
	}

	@Override
	public void insert(T element) {
		if (this.root.isEmpty()) {
			this.root.setData(element);
			this.root.setLeft(new BSTNode<T>());
			this.root.setRight(new BSTNode<T>());
			this.root.setParent(new BSTNode<T>());
			this.root.getLeft().setParent(this.root);
			this.root.getRight().setParent(this.root);
		} else {
			insert(this.root, element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (node.getData().compareTo(element) > 0) {
				insert((BSTNode<T>) node.getLeft(), element);
			} else {
				insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		if (this.root.isEmpty())
			return null;
		BSTNode<T> maximo = this.root;
		while (!maximo.getRight().isEmpty()) {
			maximo = (BSTNode<T>) maximo.getRight();
		}
		return maximo;

	}

	@Override
	public BSTNode<T> minimum() {
		if (this.root.isEmpty())
			return null;
		BSTNode<T> minimo = this.root;
		while (!minimo.getLeft().isEmpty()) {
			minimo = (BSTNode<T>) minimo.getLeft();
		}
		return minimo;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return null;
		} else {
			if (!node.getRight().isEmpty()) {
				BSTNode<T> otherNode = (BSTNode<T>) node.getRight();
				while (!otherNode.getLeft().isEmpty()) {
					otherNode = (BSTNode<T>) otherNode.getLeft();
				}
				return otherNode;

			} else {

				BSTNode<T> aux = node;
				while (!aux.getParent().isEmpty() && aux.getParent().getRight() == aux) {
					aux = (BSTNode<T>) aux.getParent();
				}
				if (aux.getParent().isEmpty()) {
					return null;
				} else {
					return (BSTNode<T>) aux.getParent();
				}
			}

		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return null;
		} else {
			if (!node.getLeft().isEmpty()) {
				BSTNode<T> otherNode = (BSTNode<T>) node.getLeft();
				while (!otherNode.getRight().isEmpty()) {
					otherNode = (BSTNode<T>) otherNode.getRight();
				}
				return otherNode;

			} else {

				BSTNode<T> aux = node;
				while (!aux.getParent().isEmpty() && aux.getParent().getLeft() == aux) {
					aux = (BSTNode<T>) aux.getParent();
				}
				if (aux.getParent().isEmpty()) {
					return null;
				} else {
					return (BSTNode<T>) aux.getParent();
				}
			}

		}
	}
	
	private void removeLeaf(BSTNode<T> node){
		
		if(node.getParent().getRight().getData() == node.getData()){
			node.getParent().setRight(new BSTNode<T>());
		}else{
			node.getParent().setLeft(new BSTNode<T>());
		}
		
	}
	
	private void removeGrauUm(BSTNode<T> node){
		
		BSTNode<T> aux = null;
		
		if(node.getLeft().isEmpty()){
			aux = (BSTNode<T>) node.getRight();
		}else{
			aux = (BSTNode<T>) node.getLeft();
		}
		
		if(node.getParent().getRight().getData() == node.getData()){
			aux.setParent(node.getParent());
			node.getParent().setRight(aux);
		}else{
			aux.setParent(node.getParent());
			node.getParent().setLeft(aux);
			
		}
		
	}
	
	private void removeGrauDois(BSTNode<T> node){
		
		BSTNode<T> aux = sucessor(node.getData());
		
		node.setData(aux.getData());
		if(aux.isLeaf()){
			removeLeaf(aux);
		}else{
			removeGrauUm(aux);
		}
		
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		
		if(node.isEmpty()) return;
		
		if(element.equals(this.root.getData())){
			if(this.size() == 1){
				this.root.setData(null);
			}else{
				BSTNode<T> aux = sucessor(this.root.getData());
				if(aux != null){
					this.root.setData(aux.getData());
					if(aux.isLeaf()){
						removeLeaf(aux);
					}else{
						removeGrauUm(aux);						
					}
				}else{
					this.root = (BSTNode<T>) this.root.getLeft();
					this.root.setParent(new BSTNode<T>());
				}
				
			}
		}else{
			if(node.isLeaf()){
				removeLeaf(node);
			}else if(isGrauDois(node)){
				removeGrauDois(node);
			}else{
				removeGrauUm(node);
			}
		}
	}
	
	private boolean isGrauDois(BSTNode<T> node){
		
		if(!node.getLeft().isEmpty() && !node.getRight().isEmpty()){
			return true;
		}
		return false;
		
	}

	@Override
	public T[] preOrder() {
		
		T[] array = Util.makeArrayOfComparable(this.size());
		return preOrder(this.root, array);
	}

	private T[] preOrder(BSTNode<T> node, T[] array) {
		if (!node.isEmpty()) {
			int i = 0;
			while (array[i] != null) {
				i++;
			}
			array[i] = node.getData();
			preOrder((BSTNode<T>) node.getLeft(), array);
			preOrder((BSTNode<T>) node.getRight(), array);
		}

		return array;
	}

	@Override
	public T[] order() {
		
		T[] array = Util.makeArrayOfComparable(this.size());
		return order(this.root, array);
	}

	private T[] order(BSTNode<T> node, T[] array) {
		if (!node.isEmpty()) {
			order((BSTNode<T>) node.getLeft(), array);
			int i = 0;
			while (array[i] != null) {
				i++;
			}
			array[i] = node.getData();
			order((BSTNode<T>) node.getRight(), array);
		}

		return array;
	}

	@Override
	public T[] postOrder() {
		
		T[] array = Util.makeArrayOfComparable(this.size());
		return postOrder(this.root, array);
	}

	private T[] postOrder(BSTNode<T> node, T[] array) {
		if (!node.isEmpty()) {

			postOrder((BSTNode<T>) node.getLeft(), array);
			postOrder((BSTNode<T>) node.getRight(), array);

			int i = 0;
			while (array[i] != null) {
				i++;
			}
			array[i] = node.getData();
		}

		return array;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}