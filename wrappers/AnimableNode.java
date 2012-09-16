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


public class AnimableNode {
	public Object currentObject;
	public ClassHook currentHook;
	public AnimableNode(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("AnimableNode");
	}
	public AnimableNode getNext(){
		FieldHook fh = currentHook.getFieldHook("getNext");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new AnimableNode(data);
		}
		return null;
	}
	public Animable getAnimable(){
		FieldHook fh = currentHook.getFieldHook("getAnimable");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Animable(data);
		}
		return null;
	}
}
