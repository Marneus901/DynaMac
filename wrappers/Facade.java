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

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class Facade {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook skillArray;
	private static FieldHook settings;
	public Facade(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Facade");
			skillArray=currentHook.getFieldHook("getSkillArray");
			settings=currentHook.getFieldHook("getSettings");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		skillArray=null;
		settings=null;
	}
	public Settings getSettings(){
		if(settings==null)
			settings=currentHook.getFieldHook("getSettings");
		if(settings!=null){
			Object data = settings.get(currentObject);
			if(data!=null)
				return new Settings(data);
		}
		return null;
	}
	public SkillInfo[] getSkillArray(){
		if(skillArray==null)
			skillArray=currentHook.getFieldHook("getEntryBuffers");
		if(skillArray!=null){
			Object data = skillArray.get(currentObject);
			if(data!=null){
				SkillInfo[] skills = new SkillInfo[Array.getLength(data)];
				for(int i=0;i<skills.length;++i){
					Object indDat = Array.get(data, i);
					if(indDat!=null)
						skills[i]=new SkillInfo(indDat);
				}
				return skills;
			}
		}
		return new SkillInfo[]{};
	}
}
