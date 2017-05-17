package ai.KendallBenjiBot;

/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project Part B
 * Tutor: Matt De Bono 
 */	

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List; 

public class TreeNode<T> implements Iterable<TreeNode<T>> {
	public T data; 
	public TreeNode<T> parent; 
	public List<TreeNode<T>> children; 
	public int eval; 
	//private List<TreeNode<T>> elementIndex; 
	
	public TreeNode(T data, int eval){
		this.eval = eval; 
		this.data = data; 
		this.children = new LinkedList<TreeNode<T>>(); 
		//this.elementIndex = new LinkedList<TreeNode<T>>();
		//this.elementIndex.add(this); 
	}
	
	public boolean isRoot(){
		return (parent == null);
	}
	
	public TreeNode<T> addChild(TreeNode<T> child){
		//TreeNode<T> childNode = new TreeNode<T>(child); 
		child.parent = this; 
		this.children.add(child); 
		return child; 
	}
	
	//this method returns the level of the current node. 
	public int getLevel(){
		if(this.isRoot()){
			return 0;
		}else{
			return parent.getLevel() + 1; 
		}
	}
	
	/*@Override
	public String toString(){
		return data != null ? data.toString() : "[data null]";
	}*/
	
	@Override
	public Iterator<TreeNode<T>> iterator(){
		Tree<T> iter = new Tree<T>(this);
		return iter; 
	}
	
	
	
	
}
