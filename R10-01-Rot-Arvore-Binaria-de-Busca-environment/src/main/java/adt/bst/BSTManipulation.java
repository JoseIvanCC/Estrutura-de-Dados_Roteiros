package adt.bst;

public interface BSTManipulation <T extends Comparable<T>>{
	
	/**
	 * It says if a BST tree1 is equals to another BST tree2. Both trees must have
	 * exactly the same nodes and the same formats. This method must 
	 * be implemented using recursion. 
	 */
	public boolean equals(BST<T> tree1, BST<T> tree2);
	
	/**
	 * It says if a BST tree1 is similar to another BST tree2. Two BSTs are similar if they 
	 * have the same formats. The content of each node is irrelevant. This method must 
	 * be implemented using recursion.   
	 */
	public boolean isSimilar(BST<T> tree1, BST<T> tree2);
	
	/**
	 * It says if a BST tree1 contains another BST subtree. This method must 
	 * be implemented using recursion.  
	 */
	public boolean contains(BST<T> tree, BST<T> subtree);
	
	/**
	 * It returns the comon ancestor between two nodes of a BST. A common ancestor is the first common 
	 * node above node1 and node2. 
	 */
	public BSTNode<T> commonAncestor(BSTNode<T> node1, BSTNode<T> node2);
}
