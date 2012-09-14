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

import java.lang.reflect.Array;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class Interface {
	public int index;
	public Object currentObject;
	public ClassHook currentHook;
	public Interface(int i, Object o){
		index=i;
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Interface");
	}
	public InterfaceChild[] getChildren(){
		FieldHook fh = currentHook.getFieldHook("getChildren");
		if(fh!=null){
			Object array = fh.getData(currentObject);
			if(array!=null){
				InterfaceChild[] children = new InterfaceChild[Array.getLength(array)];
				for(int i=0;i<children.length;++i)
					children[i] = new InterfaceChild(Array.get(array, i), this, i);
				return children;
			}
		}
		return new InterfaceChild[]{};
	}	
}
