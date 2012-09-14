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


public class Ground {
	public Object currentObject;
	public ClassHook currentHook;
	public Ground(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Ground");
	}
	public FloorDecoration getFloorDecoration(){
		FieldHook fh = currentHook.getFieldHook("getFloorDecoration");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new FloorDecoration(data);
		}
		return null;		
	}
	public Boundary getBoundary1(){
		FieldHook fh = currentHook.getFieldHook("getBoundary1");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Boundary(data);
		}
		return null;		
	}
	public Boundary getBoundary2(){
		FieldHook fh = currentHook.getFieldHook("getBoundary2");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Boundary(data);
		}
		return null;		
	}
	public WallDecoration getWallDecoration1(){
		FieldHook fh = currentHook.getFieldHook("getWallDecoration1");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new WallDecoration(data);
		}
		return null;		
	}
	public WallDecoration getWallDecoration2(){
		FieldHook fh = currentHook.getFieldHook("getWallDecoration2");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new WallDecoration(data);
		}
		return null;		
	}
	public AnimableNode getAnimableList(){
		FieldHook fh = currentHook.getFieldHook("getAnimableList");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new AnimableNode(data);
		}
		return null;		
	}
}
