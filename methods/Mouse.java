package org.dynamac.bot.api.methods;

import java.awt.Color;
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
	private static Point lastPoint = new Point(-1, -1);
	private static boolean isPressed = false;
	public static int mouseSpeed=10;
	private static PathGen pathGen = new PathGen();
	private static MouseEvent[] createPath(Component target, Point[] path) {
		MouseEvent[] me = new MouseEvent[path.length];
		long lagTime = System.currentTimeMillis();
		for (int i = 0; i < me.length; ++i) {
			me[i] = new MouseEvent(target, MouseEvent.MOUSE_MOVED, lagTime, 0, path[i].x, path[i].y, 0, false, 0);
			lagTime += getRandom();
		}
		return me;
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
	private static long getRandom() {
		Random rand = new Random();
		return rand.nextInt(100) + 40;
	}
	private static void sleep(long milli){
		try{ 
			Thread.sleep(milli);
			}
		catch(Exception ex){
		};
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
	public static void click(){
		press(LEFT_BUTTON);
		sleep(new Random().nextInt(100)+50);
		release(LEFT_BUTTON);
	}
	public static void drag(int x, int y){
		Component mouseTarget = Data.CLIENT_APPLET.getComponent(0);
		Component mouseMotionTarget = mouseTarget;
		PathGen pathGen = new PathGen();
		Point last = getLocation();
		MouseEvent[] me = createDragPath(mouseMotionTarget,mouseTarget, pathGen.makeMousePath(last.x, last.y, x, y), LEFT_BUTTON);
		isPressed=true;
		for (int i = 0; i < me.length; ++i) {
			mouseMotionTarget.dispatchEvent(me[i]);
			lastPoint=me[i].getPoint();
			sleep(Math.max(0, mouseSpeed - 2 + new Random().nextInt(4)));
		}
		isPressed=false;
	}
	public static void exit(int x,int y) {
		Component target = getTarget();
		MouseEvent me = new MouseEvent(target,MouseEvent.MOUSE_EXITED,System.currentTimeMillis(),0,x,y,0,false,MouseEvent.NOBUTTON);
		target.dispatchEvent(me);
	}
	public static void enter(int x,int y) {
		Component target = getTarget();
		MouseEvent me = new MouseEvent(target,MouseEvent.MOUSE_ENTERED,System.currentTimeMillis(),0,x,y,0,false,MouseEvent.NOBUTTON);
		target.dispatchEvent(me);
	}
	public static Point getLocation(){
		Point lastPos = getRealLocation();
		if(!lastPos.equals(new Point(-1, -1)))
			return lastPos;
		return lastPoint;
	}
	public static Point getRealLocation(){
		Point p = Data.CLIENT_APPLET.getMousePosition();
		if(p!=null)
			return p;
		return new Point(-1, -1);
	}
	public static int getSpeed(){
		return mouseSpeed;
	}
	public static Component getTarget(){
		return Data.CLIENT_APPLET.getComponent(0);
	}
	public static boolean isPressed(){
		return isPressed;
	}
	public static void move(Point p1) {
		move(p1.x, p1.y);
	}
	public static void move(int x, int y){
		Component target = getTarget();
		Point last = getLocation();
		for (MouseEvent me : createPath(target, pathGen.makeMousePath(last.x, last.y, x, y))) {
			target.dispatchEvent(me);
			lastPoint = me.getPoint();
			sleep(Math.max(0, mouseSpeed - 2 + new Random().nextInt(4)));
		}
	}
	public static void paint(Graphics g){
		if(Data.currentScript==null)
			return;
		if(isPressed)
			g.setColor(new Color(255, 127, 0, 155));
		else
			g.setColor(new Color(0, 255, 0, 155));
		Point last = getLocation();
		g.fillOval(last.x-10, last.y-3, 20, 6);
		g.fillOval(last.x-3, last.y-10, 6, 20);
	}
	public static void press(int button){
		Point last = getLocation();
		press(last.x, last.y, button);
	}
	public static void press(int x, int y, int button){
		int buttonModifiers = getButtonModifiers(button);
		Component target = getTarget();
		target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
		isPressed=true;
	}
	public static void release(int button){
		Point last = getLocation();
		release(last.x, last.y, button);
	}
	public static void release(int x, int y, int button){
		int buttonModifiers = getButtonModifiers(button);
		Component target = getTarget();
		target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
		isPressed=false;
		target.dispatchEvent(new MouseEvent(target, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), buttonModifiers, x, y, 1, false, button));
	}
	public static void rightClick(){
		press(RIGHT_BUTTON);
		sleep(new Random().nextInt(100)+50);
		release(RIGHT_BUTTON);
	}
	public static void setSpeed(int speed){
		mouseSpeed=speed;
	}
}
