/******************************************************
 * Created by Marneus901                                *
 * © 2012 MarneusScripts.com                            *
 * **************************************************** *
 * Access to this source is unauthorized without prior  *
 * authorization from its appropriate author(s).        *
 * You are not permitted to release, nor distribute this* 
 * work without appropriate author(s) authorization.    *
 ********************************************************/
package org.dynamac.bot.api.methods;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

import org.dynamac.enviroment.Data;
import org.dynamac.mousepathgen.PathGen;


public class Mouse {
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	private static Point lastPoint;
	public static int mouseSpeed=10;

	public static Point getLastMousePos() {
		Point lastPos = getMousePos();
		if(lastPos != null)
			return lastPos;
		if(lastPoint != null)
			return lastPoint;
		return new Point(0,0);            
	}
	public static Point getMousePos() {
		return Data.CLIENT_APPLET.getMousePosition();
	}
	public static void moveMouse(Point p) {
		moveMouse(p.x, p.y);
	}
	public static void moveMouse(int x, int y) {
		Point p = getLastMousePos();
		moveMouseFrom(p.x, p.y, x, y);
	}
	public static void moveMouseFrom(Point p1, Point p2) {
		moveMouseFrom(p1.x, p1.y, p2.x, p2.y);
	}
	public static void moveMouseFrom(int x1, int y1, int x2, int y2) {
		Component target = Data.CLIENT_APPLET.getComponent(0);
		PathGen pathGen = new PathGen();
		for (MouseEvent me : createMousePath(target, pathGen.makeMousePath(x1, y1, x2, y2))) {
			target.dispatchEvent(me);
			try{ 
				Thread.sleep(Math.max(0, mouseSpeed - 2 + new Random().nextInt(4)));
				}
			catch(Exception ex){
			};
		}
		lastPoint = new Point(x2, y2);
	}
	public static void dragMouse(Point p, int button){
		dragMouse(p.x, p.y, button);
	}
	public static void dragMouse(int x, int y, int button){
		Point p = getLastMousePos();
		dragMouseFrom(p.x, p.y, x, y, button);
	}
	public static void dragMouseFrom(Point p1, Point p2, int button){
		dragMouseFrom(p1.x, p1.y, p2.x, p2.y, button);
	}
	public static void dragMouseFrom(int x1, int y1, int x2, int y2, int button){
		Component mouseTarget = Data.CLIENT_APPLET.getComponent(0);
		Component mouseMotionTarget = mouseTarget;
		PathGen pathGen = new PathGen();
		MouseEvent[] me = createDragPath(mouseMotionTarget,mouseTarget, pathGen.makeMousePath(x1, y1, x2, y2), button);
		mouseTarget.dispatchEvent(me[0]);
		for (int i = 1; i < me.length - 1; ++i) {
			mouseMotionTarget.dispatchEvent(me[i]);
			try{ 
				Thread.sleep(Math.max(0, mouseSpeed - 2 + new Random().nextInt(4)));
				}
			catch(Exception ex){
			};
		}
		mouseTarget.dispatchEvent(me[me.length-1]);
		lastPoint = new Point(x2, y2);
	}
	public static void clickMouse(){
		clickMouse(1);
	}
	public static void clickMouse(int button){
		clickMouse(getLastMousePos(), button);
	}
	public static void clickMouse(Point p, int button){
		clickMouse(p.x, p.y, button, 1);
	}
	public static void clickMouse(int x, int y, int button){
		clickMouse(x, y, button, 1);
	}
	public static void clickMouse(Point p, int button, int clickCount){
		clickMouse(p.x, p.y, button, clickCount);
	}
	public static void clickMouse(int x, int y, int button, int clickCount){
		Component target = Data.CLIENT_APPLET.getComponent(0);
		createMouseClick(target, x, y, button, clickCount);
	}
	public static void exitMouse(int x,int y) {
		Component target = Data.CLIENT_APPLET.getComponent(0);
		MouseEvent me = new MouseEvent(target,MouseEvent.MOUSE_EXITED,System.currentTimeMillis(),0,x,y,0,false,MouseEvent.NOBUTTON);
		target.dispatchEvent(me);
	}
	public static void enterMouse(int x,int y) {
		Component target = Data.CLIENT_APPLET.getComponent(0);
		MouseEvent me = new MouseEvent(target,MouseEvent.MOUSE_ENTERED,System.currentTimeMillis(),0,x,y,0,false,MouseEvent.NOBUTTON);
		target.dispatchEvent(me);
	}
	private static long getRandom() {
		Random rand = new Random();
		return rand.nextInt(100) + 40;
	}
	private static MouseEvent[] createDragPath(Component mouseMotionTarget, Component mouseTarget, Point[] path, int button)  throws IllegalArgumentException{
		MouseEvent[] me = new MouseEvent[path.length + 2];
		int buttonModifiers = getButtonModifiers(button);
		long lagTime = System.currentTimeMillis();
		me[0] = new MouseEvent(mouseTarget, MouseEvent.MOUSE_PRESSED, lagTime, buttonModifiers, path[0].x, path[0].y, 1, false, button);
		lagTime += getRandom();
		for (int i = 1; i < me.length - 1; ++i) {
			me[i] = new MouseEvent(mouseMotionTarget, MouseEvent.MOUSE_DRAGGED, lagTime, button, path[i-1].x, path[i-1].y, 0, false, 0);
			lagTime += getRandom();
		}
		me[path.length + 1] = new MouseEvent(mouseTarget, MouseEvent.MOUSE_RELEASED, lagTime, buttonModifiers, path[path.length - 1].x, path[path.length - 1].y, 1, false, button);
		return me;
	}
	private static MouseEvent[] createMousePath(Component target, Point[] path) {
		MouseEvent[] me = new MouseEvent[path.length];
		long lagTime = System.currentTimeMillis();
		for (int i = 0; i < me.length; ++i) {
			me[i] = new MouseEvent(target, MouseEvent.MOUSE_MOVED, lagTime, 0, path[i].x, path[i].y, 0, false, 0);
			lagTime += getRandom();
		}

		return me;
	}
	private static void createMouseClick(Component target, int x, int y, int button, int clickCount) throws IllegalArgumentException {
		int buttonModifiers = getButtonModifiers(button);
		for (int i = 0; i < clickCount; i++) {
			target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
			try {
				Thread.sleep(new Random().nextInt(100)+50);
			} catch (InterruptedException e) {
			}
			target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
			target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
		}
	}
	public static void pressMouse(int x, int y, int button){
		int buttonModifiers = getButtonModifiers(button);
		Component target = Data.CLIENT_APPLET.getComponent(0);
		target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
	}
	public static void releaseMouse(int x, int y, int button){
		int buttonModifiers = getButtonModifiers(button);
		Component target = Data.CLIENT_APPLET.getComponent(0);
		target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
		target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
	}
	private static int getButtonModifiers(int button) throws IllegalArgumentException {
		switch (button) {
		case LEFT_BUTTON:
			return MouseEvent.BUTTON1_MASK;
		case MIDDLE_BUTTON:
			return MouseEvent.BUTTON2_MASK;
		case RIGHT_BUTTON:
			return MouseEvent.BUTTON3_MASK;
		default:
			throw new IllegalArgumentException("Not a valid button choice.");
		}
	}    
	public static void paint(Graphics g){
	}
	public static int getSpeed(){
		return mouseSpeed;
	}
	public static void setSpeed(int speed){
		mouseSpeed=speed;
	}
	public static void clickMouse(Point point) {
		clickMouse(point, 1);
	}
}
