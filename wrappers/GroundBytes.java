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


public class GroundBytes {
	public Object currentObject;
	public ClassHook currentHook;
	public GroundBytes(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("GroundBytes");
	}
	public byte[][][] getBytes(){
		FieldHook fh = currentHook.getFieldHook("getBytes");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (byte[][][])data;
		}
		return new byte[][][]{};		
	}
}
