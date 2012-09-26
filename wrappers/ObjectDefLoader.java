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

import org.dynamac.bot.api.methods.Nodes;
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
	public ObjectDef getObjectDef(int id){
		Cache cache = getDefCache();
		if(cache!=null){
			Node node = Nodes.lookup(cache.getTable(), id);
			if(node!=null){
				ObjectDef def = new ObjectDef(node.currentObject);
				return def;
			}
		}
		return null;
	}
}
