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


public class ObjectDefLoader {
	public Object currentObject;
	public ClassHook currentHook;
	public ObjectDefLoader(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("ObjectDefLoader");
	}
	public Cache getCache(){
		FieldHook fh = currentHook.getFieldHook("getCache");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
	public ObjectComposite getComposite(){
		FieldHook fh = currentHook.getFieldHook("getComposite");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new ObjectComposite(data);
		}
		return null;
	}
	public String[] getGroundActions(){
		FieldHook fh = currentHook.getFieldHook("getGroundActions");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return new String[]{};
	}
}
