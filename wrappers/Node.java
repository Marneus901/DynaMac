/******************************************************
* Created by Marneus901                                *
* © 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.wrappers;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class Node {
	public Object currentObject;
	public ClassHook currentHook;
	public Node(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Node");
	}
	public long getID(){
		FieldHook fh = currentHook.getFieldHook("getID");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Long)data) * fh.getLongMultiplier();
		}
		return -1;
	}
	public Node getNext(){
		FieldHook fh = currentHook.getFieldHook("getNext");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null){
				if(data.getClass().getName().equals(Data.indentifiedClasses.get("MenuItemNode").getClassName()))
					return new MenuItemNode(data);
				return new Node(data);
			}
		}
		return null;
	}
	public Node getPrevious(){
		FieldHook fh = currentHook.getFieldHook("getPrevious");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null){
				if(data.getClass().getName().equals(Data.indentifiedClasses.get("MenuItemNode").getClassName()))
					return new MenuItemNode(data);
				return new Node(data);
			}
		}
		return null;
	}
}
