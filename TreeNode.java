package ai.KendallBenjiBot;

/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project Part B
 * Tutor: Matt De Bono 
 */	

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List; 

/* A basic TreeNode. It can hold any data type as it was built with generics, however, we use it with data = a BoardObj.
   We also store the evaluation of this board state at each TreeNode. Each node has a parent and a list of children. 
 */
public class TreeNode<T> implements Iterable<TreeNode<T>> {
	public T data; 
	public TreeNode<T> parent; 
	public List<TreeNode<T>> children; 
	public int eval; 
	
	//Consrtucts a TreeNode
	public TreeNode(T data, int eval)
	{
		this.eval = eval; 
		this.data = data; 
		this.children = new LinkedList<TreeNode<T>>(); 
	}
	
	//checks if the node is a root
	public boolean isRoot()
	{
		return (parent == null);
	}
	
	//adds a child to a given node
	public TreeNode<T> addChild(TreeNode<T> child)
	{
		child.parent = this; 
		this.children.add(child); 
		return child; 
	}
	
	//this method returns the level of the current node. 
	public int getLevel()
	{
		if(this.isRoot())
		{
			return 0;
		}
		else
		{
			return parent.getLevel() + 1; 
		}
	}
	
	//returns a Tree iterator to iterate over nodes
	@Override
	public Iterator<TreeNode<T>> iterator(){
		Tree<T> iter = new Tree<T>(this);
		return iter; 
	}
	
	
	
	
}
