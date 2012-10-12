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


public class ItemDefLoader {
	public Object currentObject;
	public ClassHook currentHook;
	public ItemDefLoader(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("ItemDefLoader");
	}
	public boolean isMembers(){
		FieldHook fh = currentHook.getFieldHook("isMembers");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public Cache getDefCache(){
		FieldHook fh = currentHook.getFieldHook("getDefCache");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
	public Cache getModelCache(){
		FieldHook fh = currentHook.getFieldHook("getModelCache");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
}
