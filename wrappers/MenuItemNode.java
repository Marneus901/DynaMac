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

public class MenuItemNode extends NodeSub{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook action;
	private static FieldHook option;
	public MenuItemNode(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("MenuItemNode");
			action = currentHook.getFieldHook("getAction");
			option = currentHook.getFieldHook("getOption");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		action=null;
		option=null;
	}
	public String getAction(){
		if(action==null)
			action = currentHook.getFieldHook("getAction");
		if(action!=null){
			Object data = action.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
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
}
