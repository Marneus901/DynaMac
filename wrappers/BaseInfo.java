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
import org.dynamac.enviroment.hook.ClassHook.FieldHook;


public class BaseInfo {
	public Object currentObject;
	public ClassHook currentHook;
	public BaseInfo(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("BaseInfo");
	}
	public int getX(){
		FieldHook fh = currentHook.getFieldHook("getX");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public int getY(){
		FieldHook fh = currentHook.getFieldHook("getY");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
}
