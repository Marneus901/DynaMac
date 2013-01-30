/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer menu listener.           *
********************************************************/
package org.dynamac.analyzer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.dynamac.analyzer.node.TreeNode;

class PopupActionListener implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		System.out.println("Selected: " + actionEvent.getActionCommand()+":"+node.toString());
		if(node.isClass())
			new ClassAnalyzer(node);
		if(node.isMethod())
			new MethodAnalyzer(node);
		if(node.isField())
			new FieldAnalyzer(node);
	}
	public PopupActionListener(TreeNode n){
		node=n;
	}
	TreeNode node;
}