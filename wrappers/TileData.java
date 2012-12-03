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

public class TileData {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook heights;
	public TileData(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("TileData");
			heights = currentHook.getFieldHook("getHeights");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		heights=null;
	}
	public int[][] getHeights(){
		if(heights==null)
			heights = currentHook.getFieldHook("getHeights");
		if(heights!=null){
			Object data = heights.get(currentObject);
			if(data!=null)
				return (int[][])data;
		}
		return new int[][]{{}};		
	}
}
