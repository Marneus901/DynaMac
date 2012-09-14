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


public class Animable extends Interactable{
	public Object currentObject;
	public ClassHook currentHook;
	public Animable(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Animable");
	}
	public short getMinX(){
		FieldHook fh = currentHook.getFieldHook("getMinX");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
	public short getMaxX(){
		FieldHook fh = currentHook.getFieldHook("getMaxX");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
	public short getMinY(){
		FieldHook fh = currentHook.getFieldHook("getMinY");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
	public short getMaxY(){
		FieldHook fh = currentHook.getFieldHook("getMaxY");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Short)data;
		}
		return -1;
	}
}
