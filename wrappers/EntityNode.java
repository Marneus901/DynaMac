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


public class EntityNode {
	public Object currentObject;
	public ClassHook currentHook;
	public EntityNode(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("EntityNode");
	}
	public EntityNode getNext(){
		FieldHook fh = currentHook.getFieldHook("getNext");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new EntityNode(data);
		}
		return null;
	}
	public EntityNode getPrevious(){
		FieldHook fh = currentHook.getFieldHook("getPrevious");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new EntityNode(data);
		}
		return null;
	}
}
