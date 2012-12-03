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

import java.awt.Point;

import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.Menu;
import org.dynamac.bot.api.methods.Mouse;
import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class NPC extends Character{
	public Object currentObject;
	private static ClassHook currentHook;
	private static FieldHook level;
	private static FieldHook def;
	private static FieldHook name;
	public NPC(Object o){
		super(o);
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NPC");
			level = currentHook.getFieldHook("getLevel");
			def = currentHook.getFieldHook("getNPCDef");
			name = currentHook.getFieldHook("getNPCName");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		level=null;
		def=null;
		name=null;
	}
	public boolean containsPoint(Point test) {
		return getLocation().getPolygon().contains(test);
	}
	public boolean doAction(String action){
		if(!Menu.isOpen()){
			Point p = getLocation().getRandomPoint();
			if(p.equals(new Point(-1, -1))){
				return false;
			}
			if(!containsPoint(p))
				return false;
			Mouse.move(p);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			if(Menu.getIndex(action)==0){
				Mouse.click();
				for(int i=0;i<20;++i){
					if(Client.getMouseCrosshairState()==2)
						return true;
					if(Client.getMouseCrosshairState()==1)
						return false;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				return false;
			}
			if(Menu.getIndex(action)>0){
				Mouse.rightClick();
				for(int i=0;i<10;++i){
					if(Menu.isOpen())
						break;
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
		}
		return Menu.click(action);
	}
	public int getLevel(){
		if(level==null)
			level = currentHook.getFieldHook("getLevel");
		if(level!=null){
			Object data = level.get(currentObject);
			if(data!=null)
				return (Integer)data * level.getIntMultiplier();
		}
		return -1;
	}
	public int getID(){
		NPCDef def = getNPCDef();
		if(def!=null)
			return def.getID();
		return -1;
	}
	public NPCDef getNPCDef(){
		if(def==null)
			def = currentHook.getFieldHook("getNPCDef");
		if(def!=null){
			Object data = def.get(currentObject);
			if(data!=null)
				return new NPCDef(data);
		}
		return null;
	}
	public String getNPCName(){
		if(name==null)
			name = currentHook.getFieldHook("getNPCName");
		if(name!=null){
			Object data = name.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
