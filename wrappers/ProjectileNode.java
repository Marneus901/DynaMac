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


public class ProjectileNode extends Animable{
	public Object currentObject;
	public ClassHook currentHook;
	public ProjectileNode(Object o){
		super(o);
		currentObject=o;
		currentHook = Data.indentifiedClasses.get("ProjectileNode");
	}
	public Projectile getProjectile(){
		FieldHook fh = currentHook.getFieldHook("getProjectile");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Projectile(data);
		}
		return null;
	}
}
