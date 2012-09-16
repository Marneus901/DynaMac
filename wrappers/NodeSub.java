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


public class NodeSub extends Node{
	public Object currentObject;
	public ClassHook currentHook;
	public NodeSub(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("NodeSub");
	}
	public int getIDSub(){
		FieldHook fh = currentHook.getFieldHook("getIDSub");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
	public NodeSub getNextSub(){
		FieldHook fh = currentHook.getFieldHook("getNextSub");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
	public NodeSub getPreviousSub(){
		FieldHook fh = currentHook.getFieldHook("getPreviousSub");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
}
