/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's node for bytecode tracing.                 *
********************************************************/
package org.dynamac.analyzer.node;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class TreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;
	private boolean areChildrenDefined = false;
	private int numChildren;
	public ArrayList<TreeNode> childrenArray = new ArrayList<TreeNode>();
	public String currentString;//Current name of class/field/method
	
	public ClassNode currentClass;
	public FieldNode currentField;
	public MethodNode currentMethod;
	public String refactoredName;
	
	public TreeNode(String s) {
		super(s);
		numChildren = 0;
		currentString=s;
		refactoredName="";
	}
	public TreeNode(String s, ClassNode c) {
		super(s);
		numChildren = 0;
		currentString=s;
		currentClass=c;
		refactoredName="";
	}
	public TreeNode(String s, FieldNode f) {
		super(s);
		numChildren = 0;
		currentString=s;
		currentField=f;
		refactoredName="";
	}
	public TreeNode(String s, MethodNode m) {
		super(s);
		numChildren = 0;
		currentString=s;
		currentMethod=m;
		refactoredName="";
	}
	@Override
	public boolean isLeaf() {
		if(currentField!=null || currentMethod!=null)
				return true;
		return(false);
	}
	public boolean isClass(){
		return currentClass!=null;
	}
	public boolean isMethod(){
		return currentMethod!=null;
	}
	public boolean isField(){
		return currentField!=null;
	}
	public int getNodeCount(){
		if(childrenArray.size()>0){
			int count=1;
			for(TreeNode n : childrenArray)
				count+=n.getNodeCount();
			return count;
		}
		return 1;
	}
	@Override
	public int getChildCount() {
		if (!areChildrenDefined)
			return 0;
		return numChildren;
	}
	public TreeNode getChildByText(String s){
		for(TreeNode n : childrenArray)
			if(n.toString().equals(s))
				return n;
		return null;
	}
	public void removeChildren(){
		childrenArray=new ArrayList<TreeNode>();
		numChildren=0;
		removeAllChildren();
		areChildrenDefined=false;
	}
	public void addChild(TreeNode d){
		areChildrenDefined=true;
		add(d);
		childrenArray.add(d);
		numChildren++;	
	}
	public String toString() {
		return currentString+((refactoredName!=null && !refactoredName.equals(""))?" ["+refactoredName+"]":"");
	}
}