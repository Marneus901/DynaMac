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

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class ProjectileNode extends Animable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook projectile;
	public ProjectileNode(Object o){
		super(o);
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ProjectileNode");
			projectile = currentHook.getFieldHook("getProjectile");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		projectile=null;
	}
	public Projectile getProjectile(){
		if(projectile==null)
			projectile = currentHook.getFieldHook("getProjectile");
		if(projectile!=null){
			Object data = projectile.get(currentObject);
			if(data!=null)
				return new Projectile(data);
		}
		return null;
	}
}
