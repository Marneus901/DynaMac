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
import java.awt.Rectangle;
import java.util.Random;

import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.Menu;
import org.dynamac.bot.api.methods.Mouse;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class NPC extends Character{
	public Object currentObject;
	private ClassHook currentHook;
	public NPC(Object o){
		super(o);
		currentObject=o;
		currentHook = Data.indentifiedClasses.get("NPC");
	}

	public boolean containsPoint(Point test) {
		return getLocation().getPolygon().contains(test);
	}

	/**
	 * Temporary Fix, when we start injecting, this will be resolved
	 * @author Greg
	 * @return
	 */

	public boolean doAction(String action){
		if(!Menu.isOpen()){
			Point p = getRandomPoint();
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
		FieldHook fh = currentHook.getFieldHook("getLevel");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
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
		FieldHook fh = currentHook.getFieldHook("getNPCDef");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new NPCDef(data);
		}
		return null;
	}
	public String getNPCName(){
		FieldHook fh = currentHook.getFieldHook("getNPCName");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}

	public Point getRandomPoint() {
		return getRandomPoint(this.getLocation().getPolygon().getBounds());
	}

	public Point getRandomPoint(Rectangle p) {
		boolean found = false;
		Point Return = null;
		for (int i = 0; i < 30; i++) {
			Rectangle r = p.getBounds();
			Return = new Point(r.x + new Random(r.width).nextInt(), r.y + new Random(r.height).nextInt());
			if (r.contains(Return)) {
				System.out.println(Return);
				found = true;
				break;
			}
		}
		if (!found) {
			int xOff = new Random().nextInt(16);
			int yOff = new Random().nextInt(16);
			if (new Random().nextInt(3) == 1)
				xOff *= -1;
			if (new Random().nextInt(3) == 1)
				yOff *= -1;
			System.out.println(xOff + "," + yOff);
			Return = new Point(((int) p.getBounds().getCenterX()) + xOff,
					((int) p.getBounds().getCenterY()) + yOff);
		}
		if (Return == null)
			return null;
		return new Point(Return.x, Return.y);
	}
}
