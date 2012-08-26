package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.MenuItemNode;
import org.dynamac.bot.api.wrappers.Node;
import org.dynamac.bot.api.wrappers.NodeList;

public class Menu {
	public static String[] getMenuItems(){
		NodeList items = Client.getMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getAction()+" "+item.getOption());
			}
		}
		return options.toArray(new String[]{});
	}
	public static String[] getMenuActions(){
		NodeList items = Client.getMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getAction());
			}
		}
		return options.toArray(new String[]{});
	}
	public static String[] getMenuOptions(){
		NodeList items = Client.getMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getOption());
			}
		}
		return options.toArray(new String[]{});
	}
}
