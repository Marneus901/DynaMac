/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer class hierarchy view.    *
********************************************************/
package org.dynamac.analyzer.gui;

import java.awt.event.MouseEvent;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.dynamac.analyzer.node.ClassTree;
import org.dynamac.analyzer.node.TreeNode;
import org.dynamac.enviroment.Data;
import org.dynamac.reflection.ClassHook;
import org.objectweb.asm.tree.ClassNode;

public class ClassHierarchy extends javax.swing.JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public boolean canBeAdded(ClassNode cg){
		if(Data.REFLECTION_CLIENT_HOOKS.containsKey(cg.superName))
			return false;
		for(ClassTree ci : hierarchyRoot.children){
			if(ci.isInFamily(cg))
				return false;
		}
		return true;
	}
	public ClassTree getHierarchy(){
		hierarchyRoot = new ClassTree();
		for(ClassHook ch : Data.REFLECTION_CLIENT_HOOKS.values()){
			ClassNode cg = ch.getBytecodeClass();
			if(canBeAdded(cg))
				hierarchyRoot.addChild(new ClassTree(cg));
		}
		return hierarchyRoot;
	}
	public ClassHierarchy() {
        new Thread(this).start();
    }
    private void initComponents() {
    	setTitle("Class Hierarchy Tree : "+AnalyzerMain.root.currentString);
    	hierarchyRoot = new ClassTree();
        jScrollPane1 = new javax.swing.JScrollPane();
		classHierarchyTree = new javax.swing.JTree(getHierarchy());
        classHierarchyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		classHierarchyTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	classHierarchyTreeMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(classHierarchyTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setVisible(true);
    }
    private void classHierarchyTreeMousePressed(java.awt.event.MouseEvent evt) {
		if(evt.getButton()==MouseEvent.BUTTON3){
			TreePath path = classHierarchyTree.getPathForLocation(evt.getX(), evt.getY());
			if(path!=null){
				System.out.println(path.toString());
				ClassTree node = (ClassTree)path.getLastPathComponent();
				TreeNode classNode = AnalyzerMain.root.getChildByText(node.thisClass.name);
				if(classNode!=null){
					menu = new SubMenu(classNode);
					menu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		}
	}
    private javax.swing.JTree classHierarchyTree;
    private javax.swing.JScrollPane jScrollPane1;
	public ClassTree hierarchyRoot;
    SubMenu menu;
	public void run() {
		initComponents();
	}
}