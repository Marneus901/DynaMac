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


public class Viewport {
	public Object currentObject;
	public ClassHook currentHook;
	public Viewport(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Viewport");
	}
	public float[] getFloats(){
		FieldHook fh = currentHook.getFieldHook("getFloats");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (float[])data;
		}
		return new float[]{};		
	}
}
