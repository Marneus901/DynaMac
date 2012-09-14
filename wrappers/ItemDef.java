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


public class ItemDef {
	public Object currentObject;
	public ClassHook currentHook;
	public ItemDef(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("ItemDef");
	}
	public ItemDefLoader getItemDefLoader(){
		FieldHook fh = currentHook.getFieldHook("getItemDefLoader");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new ItemDefLoader(data);
		}
		return null;
	}
	public int getID(){
		FieldHook fh = currentHook.getFieldHook("getID");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
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
	public String[] getActions(){
		FieldHook fh = currentHook.getFieldHook("getActions");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return new String[]{};
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
	public String getName(){
		FieldHook fh = currentHook.getFieldHook("getName");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
