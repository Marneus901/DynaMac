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


public class MenuGroupNode extends NodeSub{
	public Object currentObject;
	public ClassHook currentHook;
	public MenuGroupNode(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("MenuGroupNode");
	}
	public String getOption(){
		FieldHook fh = currentHook.getFieldHook("");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public NodeSubQueue getItems(){
		FieldHook fh = currentHook.getFieldHook("getItems");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new NodeSubQueue(data);
		}
		return null;
	}
	public int getSize(){
		FieldHook fh = currentHook.getFieldHook("getSize");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
}
