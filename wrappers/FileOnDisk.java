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

import java.io.File;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class FileOnDisk {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook file;
	public FileOnDisk(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("FileOnDisk");
			file = currentHook.getFieldHook("getFile");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		file=null;
	}
	public File getFile(){
		if(file==null)
			file = currentHook.getFieldHook("getFile");
		if(file!=null){
			Object data = file.get(currentObject);
			if(data!=null)
				return (File)data;
		}
		return null;		
	}
}
