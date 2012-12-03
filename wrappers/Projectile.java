/******************************************************
* Created by Marneus901                                *
* � 2012 MarneusScripts.com                            *
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

public class Projectile extends Animable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook animator;
	private static FieldHook target;
	public Projectile(Object o){
		super(o);
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Projectile");
			animator = currentHook.getFieldHook("getAnimator");
			target = currentHook.getFieldHook("getTarget");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animator=null;
		target=null;
	}
	public Animator getAnimator(){
		if(animator==null)
			animator = currentHook.getFieldHook("getAnimator");
		if(animator!=null){
			Object data = animator.get(currentObject);
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
		if(target==null)
			target = currentHook.getFieldHook("getTarget");
		if(target!=null){
			Object data = target.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
}
