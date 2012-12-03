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

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class MenuGroupNode extends NodeSub{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook option;
	private static FieldHook items;
	private static FieldHook size;
	public MenuGroupNode(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("MenuGroupNode");
			option = currentHook.getFieldHook("getOption");
			items = currentHook.getFieldHook("getItems");
			size = currentHook.getFieldHook("getSize");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		option=null;
		items=null;
		size=null;
	}
	public String getOption(){
		if(option==null)
			option = currentHook.getFieldHook("getOption");
		if(option!=null){
			Object data = option.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public NodeSubQueue getItems(){
		if(items==null)
			items = currentHook.getFieldHook("getItems");
		if(items!=null){
			Object data = items.get(currentObject);
			if(data!=null)
				return new NodeSubQueue(data);
		}
		return null;
	}
	public int getSize(){
		if(size==null)
			size = currentHook.getFieldHook("getSize");
		if(size!=null){
			Object data = size.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
}
