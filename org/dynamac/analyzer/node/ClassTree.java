/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's class hierarchy node object.               *
********************************************************/
package org.dynamac.analyzer.node;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.dynamac.analyzer.gui.AnalyzerMain;
import org.dynamac.enviroment.Data;
import org.dynamac.reflection.ClassHook;
import org.objectweb.asm.tree.ClassNode;

public class ClassTree extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 1L;
	public ArrayList<ClassTree> children = new ArrayList<ClassTree>();
	public ClassNode thisClass;
	private boolean areChildrenDefined = false;
	private int numChildren;
	public ClassTree(ClassNode cg){
		super(cg.name);
		thisClass = cg;
		for(ClassHook ch : Data.REFLECTION_CLIENT_HOOKS.values()){
			ClassNode c = ch.getBytecodeClass();
			if(c.superName.equals(thisClass.name))
				addChild(new ClassTree(c));
		}
		areChildrenDefined=(children.size()>0);
		numChildren=children.size();
	}
	public ClassTree(){
		super(AnalyzerMain.root.currentString);
		thisClass=null;
		areChildrenDefined=false;
		numChildren=0;
	}
	@Override
	public boolean isLeaf() {
		return !areChildrenDefined;
	}
	@Override
	public int getChildCount() {
		if (!areChildrenDefined)
			return 0;
		return numChildren;
	}
	public void addChild(ClassTree c){
		areChildrenDefined=true;
		add(c);
		children.add(c);
		numChildren++;	
	}
	public boolean isInFamily(ClassNode cg){
		if(thisClass==null)
			for(ClassTree ct : children)
				if(ct.isInFamily(cg))
					return true;
		if(thisClass.name.equals(cg.name))
			return true;
		for(ClassTree t : children)
			if(t.isInFamily(cg))
				return true;
		return false;
	}
	public String toString(){
		if(thisClass==null)
			return AnalyzerMain.root.currentString;
		return thisClass.name;
	}
}