/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer right click menu.        *
********************************************************/
package org.dynamac.analyzer.gui;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.dynamac.analyzer.node.TreeNode;

class SubMenu extends JPopupMenu {  
	private static final long serialVersionUID = 1L;

	public SubMenu(TreeNode n){
		if(n.isClass()){
			JMenuItem item = new JMenuItem("Open Class Analysis Window");
			item.addActionListener(new PopupActionListener(n));
			add(item);
		}
		else if(n.isMethod()){
			JMenuItem item = new JMenuItem("Open Method Analysis Window");
			item.addActionListener(new PopupActionListener(n));
			add(item);
		}
		else if(n.isField()){
			JMenuItem item = new JMenuItem("Open Field Analysis Window");
			item.addActionListener(new PopupActionListener(n));
			add(item);
		} 
	}
}