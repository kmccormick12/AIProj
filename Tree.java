package ai.partB;

import java.util.Iterator; 


public class Tree<T> implements Iterator<TreeNode<T>> {
	private TreeNode<T> aNode; 
	private Stage st;
	private TreeNode<T> next; 
	private Iterator<TreeNode<T>> childCurrentIter;
	private Iterator<TreeNode<T>> childSubNodeIter; 
	
	enum Stage {
		PARENT, CHILD_CURRENT, CHILD_SUB_NODE
	}
	
	
	
	public Tree(TreeNode<T> aNode){
		this.aNode = aNode; 
		this.st = Stage.PARENT; 
		this.childCurrentIter = aNode.children.iterator(); 
	}
	
	@Override
	public boolean hasNext(){
		if(this.st == Stage.PARENT){
			this.next = this.aNode; 
			this.st = Stage.CHILD_CURRENT; 
			return true;
		} 
		
		if(this.st == Stage.CHILD_CURRENT){
			if(childCurrentIter.hasNext()){
				TreeNode<T> childDirect = childCurrentIter.next();
				childSubNodeIter = childDirect.iterator(); 
				this.st = Stage.CHILD_SUB_NODE; 
				return hasNext(); 
			}else{
				this.st = null;
				return false; 
			}
		}
		
		if(this.st == Stage.CHILD_SUB_NODE){
			if(childSubNodeIter.hasNext()){
				this.next = childSubNodeIter.next();
				return true; 
			}else{
				this.next = null;
				this.st = Stage.CHILD_CURRENT;
				return hasNext(); 
			}
		}
		
		return false; 
	}
	
	@Override
	public TreeNode<T> next(){
		return this.next; 
	}
	
	@Override
	public void remove(){
		throw new UnsupportedOperationException(); 
	}
}
