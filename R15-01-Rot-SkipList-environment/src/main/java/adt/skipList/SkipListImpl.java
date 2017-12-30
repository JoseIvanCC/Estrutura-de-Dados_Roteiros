package adt.skipList;

import java.util.ArrayList;
import java.util.LinkedList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;
	protected int level;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.level = 0;
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@Override
	public void insert(int key, T newValue, int height) {

		SkipListNode[] newNode = new SkipListNode[this.maxHeight];
		SkipListNode<T> node = this.root;

		if (height > this.level) { // aqui eu atualizo o level(height da
									// skip) quando o no eh inserido
			this.level = height;
		}

		for (int i = this.maxHeight - 1; i >= 0; i--) {
			while (node.getForward(i).getValue() != null && node.getForward(i).getKey() < key) {
				node = node.getForward(i);
			}
			newNode[i] = node;
		}
		node = node.getForward(0);
		if (node.getKey() == key) {
			node.setValue(newValue);
		} else {
			if (height >= this.maxHeight) {
				for (int i = this.maxHeight; i > height; i--) {
					newNode[i] = this.root;
				}
				maxHeight = height;
			}
			node = new SkipListNode<T>(key, height, newValue);
			for (int j = 0; j < height; j++) {
				if (newNode[j] != null) {
					node.forward[j] = newNode[j].getForward(j);
					newNode[j].forward[j] = node;
				}
			}
		}

	}

	@Override
	public void remove(int key) {
		SkipListNode<T> aux = this.root;
		SkipListNode[] update = new SkipListNode[maxHeight];

		for (int i = maxHeight - 1; i >= 0; i--) {
			while (aux.forward[i].key < key) {
				aux = aux.forward[i];
			}
			update[i] = aux;
		}
		aux = aux.forward[0];

		if (aux.key == key) {
			for (int i = 0; i < aux.height(); i++) {
				if (update[i].forward[i] != aux)
					return;
				else
					update[i].forward[i] = aux.forward[i];
			}
		}
	}

	@Override
	public int height() {
		return this.level;
	}
	// HEIGHT ITERATIVO
	// @Override
	// public int height() {
	//
	// SkipListNode<T> node = this.root;
	// int altura = 0;
	// while (!node.getForward(0).equals(NIL)) {
	// if (node.getForward(0).height() > altura) {
	// altura = node.getForward(0).height();
	// }
	// node = node.getForward(0);
	// }
	// return altura;
	//
	// }
	
	public SkipListNode<T>[] elementsByHeightDescrescente() {
		ArrayList<SkipListNode<T>> list = new ArrayList<>();
		
		addDescrescenteNode(list, root.getForward(0), maxHeight-1);
		
		return list.toArray(new SkipListNode[list.size()]);
	}

	private void addDescrescenteNode(ArrayList<SkipListNode<T>> list, SkipListNode<T> node, int height) {
		if (height < 0) return;
		
		while (node != NIL) {
			if (node.height() == height) {
				list.add(node);
			}
			node = node.getForward(0);
		}
		
		addNode(height-1, list, root.getForward(0));
	}

	@Override
	public boolean isLevelOk() {
		return isLevelOk(root, 0, this.size());
	}

	private boolean isLevelOk(SkipListNode<T> node, int height, int sizeAnterior) {
		if (height == maxHeight) return true;
		
		int sizeAtual = 0;
		while (node.getForward(height+1) != NIL) {
			node = node.getForward(height+1);
			sizeAtual++;
		}
		
		if (sizeAtual == 0) return true;
		
		if (sizeAtual != (sizeAnterior/2)) {
			return false;
		}
		
		return isLevelOk(root, height + 1, sizeAtual);
	}

	@Override
	public SkipListNode<T>[] elementsByHeight(int height) {
		ArrayList<SkipListNode<T>> list = new ArrayList<>();
		
		addNode(height, list, root.getForward(0));
		
		return list.toArray(new SkipListNode[list.size()]);
	}

	private void addNode(int height, ArrayList<SkipListNode<T>> list, SkipListNode<T> node) {
		
		if (node == NIL) return;
		
		if (node.height() == height) {
			list.add(node);
		}
		
		addNode(height, list, node.getForward(0));
	}

	// ELEMENTS BY LEVEL ITERATIVO
	// ArrayList<T> array = new ArrayList<>();
	// SkipListNode<T> node = this.root.getForward(height);
	//
	// while (node.getForward(height) != NIL) {
	// array.add((T) node.getValue());
	// node = node.getForward(height);
	// }
	// array.add((T) node.getValue());
	//
	// return (T[]) array.toArray(new Comparable[0]);
	@Override
	public SkipListNode<T> search(int key) {
		if (this.root.getForward(0) == NIL) {
			return null;
		}
		else {
			return search(root, maxHeight-1, key);
		}
	}

	private SkipListNode<T> search(SkipListNode<T> node, int height, int key) {
		
		if (height < 0) return null;
		
		if (node.getForward(height).getKey() > key) {
			return search(node, height - 1, key);
		}
		else if (node.getForward(height).getKey() < key) {
			return search(node.getForward(height), height, key);
		}
		
		return node.getForward(height);
	}
	// SERACH ITERATIVO
	// public SkipListNode<T> search(int key) {
	// SkipListNode<T> x = this.root;
	// for (int i = maxHeight - 1; i >= 0; i--) {
	// while (x.getForward(i).getKey() < key) {
	// x = x.getForward(i);
	// }
	// }
	// x = x.getForward(0);
	//
	// if (x.getKey() == key)
	// return x;
	// else
	// return null;
	// }

	@Override
	public int size() {
		int size = 0;
		SkipListNode<T> node = this.root.getForward(0);

		while (!node.equals(NIL)) {
			node = node.getForward(0);
			size++;
		}
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode[] array = new SkipListNode[this.size() + 2];

		if (this.size() == 0) {
			array[0] = root;
			array[1] = NIL;
		}

		SkipListNode<T> node = this.root.getForward(0);
		array[0] = root;

		int i = 1;
		while (!node.equals(NIL)) {
			array[i] = node;
			node = node.getForward(0);
			i++;
		}
		array[i] = NIL;

		return array;
	}

}
