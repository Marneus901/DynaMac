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


public class ModelSoftware extends Model{
	public Object currentObject;
	public ClassHook currentHook;
	public ModelSoftware(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("ModelSoftware");
	}
}
