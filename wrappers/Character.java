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

import org.dynamac.bot.api.methods.Client;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class Character extends Animable{
	public Object currentObject;
	public ClassHook currentHook;
	public Character(Object o){
		super(o);
		currentObject=o;
		currentHook = Data.indentifiedClasses.get("Character");
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
	public int getLocationX(){
		try{
			return getMaxX()+Client.getRSData().getBaseInfo().getX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocationY(){
		try{
			return getMaxY()+Client.getRSData().getBaseInfo().getY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocalX(){
		try{
			return getMaxX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocalY(){
		try{
			return getMinY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getHeight(){
		FieldHook fh = currentHook.getFieldHook("getHeight");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;		
	}
	public int getMovementSpeed(){
		FieldHook fh = currentHook.getFieldHook("getMovementSpeed");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;		
	}
	public ModelLD[] getLDModels(){
		FieldHook fh = currentHook.getFieldHook("getModels");
		if(fh!=null){
			Object array = fh.getData(currentObject);
			ModelLD[] models = new ModelLD[Array.getLength(array)];
			for(int i=0;i<models.length;++i)
				models[i] = new ModelLD(Array.get(array, i));
			return models;
		}
		return new ModelLD[]{};
	}
}
