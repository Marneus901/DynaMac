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


public class Projectile extends Animable{
	public Object currentObject;
	public ClassHook currentHook;
	public Projectile(Object o){
		super(o);
		currentObject=o;
		currentHook = Data.indentifiedClasses.get("Projectile");
	}
	public Animator getAnimator(){
		FieldHook fh = currentHook.getFieldHook("getAnimator");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Animator(data);
		}
		return null;
	}
	public int getAnimationID(){
		Animator animator = getAnimator();
		if(animator!=null){
			Animation animation = animator.getAnimation();
			if(animation!=null)
				return animation.getID();
		}
		return -1;
	}
	public int getTarget(){
		FieldHook fh = currentHook.getFieldHook("getTarget");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
}
