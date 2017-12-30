package adt.btree;


import java.util.ArrayList;
import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		if (node.isEmpty())
			return -1;

		if (node.isLeaf()) {
			return 0;
		} else {
			return 1 + height(node.getChildren().get(0));
		}

	}
	
	@Override
	public int countKeys() {
		if (isEmpty()) return 0;
		else {
			return countKeys(this.root);
		}
	}

	private int countKeys(BNode<T> node) {
		int size = node.getElements().size();
		
		if (!node.getChildren().isEmpty()) {
			for (BNode<T> child : node.getChildren()) {
				size += countKeys(child);
			}			
		}
		
		return size;
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		ArrayList<BNode<T>> array = new ArrayList<>();
		if (!isEmpty()) {
			depthLeftOrder(this.root, array);
		}

		BNode<T>[] elements = new BNode[array.size()];

		for (int i = 0; i < array.size(); i++) {
			elements[i] = (BNode<T>) array.get(i);
		}

		return elements;
	}

	private void depthLeftOrder(BNode<T> node, ArrayList<BNode<T>> array) {
		if (node.getParent() == null) array.add(node);
		
		for (int i = 0; i < node.getChildren().size(); i++) {
			array.add(node.getChildren().get(i));
			depthLeftOrder(node.getChildren().get(i), array);
		}
	}

	@Override
	public int size() {
		if (!isEmpty()) {
			return size(root);
		} else {
			return 0;
		}
	}

	private int size(BNode<T> node) {
		int size = node.getElements().size();

		LinkedList<BNode<T>> children = node.getChildren();

		if (!children.isEmpty()) {

			for (BNode<T> child : children) {
				size += size(child);
			}
		}

		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		if (isEmpty())
			return new BNodePosition<>();
		else {
			return search(this.root, element);
		}
	}

	private BNodePosition<T> search(BNode<T> node, T element) {
		int i = 0;
		while (i < node.size() && element.compareTo(node.getElementAt(i)) > 0) {
			i++;
		}
		if (i < node.size() && element.equals(node.getElementAt(i))) {
			return new BNodePosition<T>(node, i);
		}
		if (node.isLeaf())
			return new BNodePosition<>(node, -1);

		return search(node.getChildren().get(i), element);
	}

	@Override
	public void insert(T element) {
		if (element == null)
			return;
		if (this.root.isEmpty()) {
			BNode<T> newNode = new BNode<>(this.order);
			newNode.addElement(element);
			this.root = newNode;
		} else {
			insert(this.root, element);
		}
	}

	private void insert(BNode<T> node, T element) {
		if (node.isLeaf()) {
			if (node.isFull()) {
				node.addElement(element);
				split(node);
			} else {
				node.addElement(element);
			}
		} else {
			int index = 0;
			while (index != node.size() && element.compareTo(node.getElementAt(index)) > 0) {
				index++;
			}

			if (index == node.size()) {
				insert(node.getChildren().get(node.size()), element);
			} else {
				insert(node.getChildren().get(index), element);
			}
		}
	}

	private void split(BNode<T> node) {

		int meio = (node.size()) / 2;

		T elementoMeio = node.getElementAt(meio);

		BNode<T> rightNode = createRightNode(node);
		node.removeElement(meio);

		for (int i = 0; i < rightNode.size(); i++) {
			node.removeElement(rightNode.getElementAt(i));
		}

		for (int i = node.size() + 1; i < node.getChildren().size(); i++) {
			rightNode.getChildren().add(node.getChildren().get(i));
			node.getChildren().get(i).setParent(rightNode);
		}

		for (int i = 0; i < node.getChildren().size()-1; i++) {
			node.getChildren().remove(rightNode.getChildren().get(i));
		}

		BNode<T> parent = node.getParent();

		if (parent == null) {
			BNode<T> novoRoot = new BNode<>(order);
			root = novoRoot;

			root.getChildren().add(node);

			parent = root;
		}

		node.setParent(parent);
		rightNode.setParent(parent);

		if (parent.isFull()) {
			parent.addElement(elementoMeio);

			int indexMiddle = parent.getElements().indexOf(elementoMeio);
			parent.getChildren().add(indexMiddle + 1, rightNode);

			split(parent);
		} else {
			parent.addElement(elementoMeio);

			int indexMiddle = parent.getElements().indexOf(elementoMeio);
			parent.getChildren().add(indexMiddle + 1, rightNode);
		}

	}

	private BNode<T> createRightNode(BNode<T> node) {

		BNode<T> rightNode = new BNode<>(order);

		int indexControl = 0;

		if (order % 2 == 0) {
			indexControl = 1;
		}

		for (int i = (node.size() / 2) + indexControl; i < node.size(); i++) {
			rightNode.addElement(node.getElementAt(i));
		}

		return rightNode;
	}

	private void promote(BNode<T> node) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
