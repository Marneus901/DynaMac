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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import org.dynamac.enviroment.Data;
import org.dynamac.mousepathgen.PathGen;


public class Mouse {
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;
	private Point lastPoint;

	public Point getLastMousePos() {
		Point lastPos = getMousePos();
		if(lastPos != null)
			return lastPos;
		if(lastPoint != null)
			return lastPoint;
		return new Point(0,0);            
	}
	public Point getMousePos() {
		return Data.CLIENT_APPLET.getMousePosition();
	}
	public void moveMouse(Point p) {
		moveMouse(p.x, p.y);
	}
	public void moveMouse(int x, int y) {
		Point p = getLastMousePos();
		moveMouseFrom(p.x, p.y, x, y);
	}
	public void moveMouseFrom(Point p1, Point p2) {
		moveMouseFrom(p1.x, p1.y, p2.x, p2.y);
	}
	public void moveMouseFrom(int x1, int y1, int x2, int y2) {
		Component target = Data.CLIENT_APPLET.getComponent(0);
		PathGen pathGen = new PathGen();
		for (MouseEvent me : createMousePath(target, pathGen.makeMousePath(x1, y1, x2, y2))) {
			target.dispatchEvent(me);
			stars.add(new Star(System.currentTimeMillis(), me.getPoint()));
			try{ Thread.sleep(2 + (int)(Math.random() * 2));}catch(Exception ex){};
		}
		lastPoint = new Point(x2, y2);
	}
	public void dragMouse(Point p, int button){
		dragMouse(p.x, p.y, button);
	}
	public void dragMouse(int x, int y, int button){
		Point p = getLastMousePos();
		dragMouseFrom(p.x, p.y, x, y, button);
	}
	public void dragMouseFrom(Point p1, Point p2, int button){
		dragMouseFrom(p1.x, p1.y, p2.x, p2.y, button);
	}
	public void dragMouseFrom(int x1, int y1, int x2, int y2, int button){
		Component mouseTarget = Data.CLIENT_APPLET.getComponent(0);
		Component mouseMotionTarget = mouseTarget;
		PathGen pathGen = new PathGen();
		MouseEvent[] me = createDragPath(mouseMotionTarget,mouseTarget, pathGen.makeMousePath(x1, y1, x2, y2), button);
		mouseTarget.dispatchEvent(me[0]);
		for (int i = 1; i < me.length - 1; ++i) {
			mouseMotionTarget.dispatchEvent(me[i]);
			try{ Thread.sleep(2 + (int)(Math.random() * 2));}catch(Exception ex){}; //the lagtime isnt human enough
		}
		mouseTarget.dispatchEvent(me[me.length-1]);
		lastPoint = new Point(x2, y2);
		stars.add(new Star(System.currentTimeMillis(), lastPoint));
	}
	public void clickMouse(){
		clickMouse(1);
	}
	public void clickMouse(int button){
		clickMouse(getMousePos(), button);
	}
	public void clickMouse(Point p, int button){
		clickMouse(p.x, p.y, button, 1);
	}
	public void clickMouse(int x, int y, int button){
		clickMouse(x, y, button, 1);
	}
	public void clickMouse(Point p, int button, int clickCount){
		clickMouse(p.x, p.y, button, clickCount);
	}
	public void clickMouse(int x, int y, int button, int clickCount){
		Component target = Data.CLIENT_APPLET.getComponent(0);
		for (MouseEvent me : createMouseClick(target, x, y, button, clickCount))
			target.dispatchEvent(me);
	}
	public void exitMouse(int x,int y) {
		Component target = Data.CLIENT_APPLET.getComponent(0);
		MouseEvent me = new MouseEvent(target,MouseEvent.MOUSE_EXITED,System.currentTimeMillis(),0,x,y,0,false,MouseEvent.NOBUTTON);
		target.dispatchEvent(me);
	}
	public void enterMouse(int x,int y) {
		Component target = Data.CLIENT_APPLET.getComponent(0);
		MouseEvent me = new MouseEvent(target,MouseEvent.MOUSE_ENTERED,System.currentTimeMillis(),0,x,y,0,false,MouseEvent.NOBUTTON);
		target.dispatchEvent(me);
	}
	private static long getRandom() {
		Random rand = new Random();
		//lag var 47-219, usu 78-94; currently tends low
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
	private static MouseEvent[] createMouseClick(Component target, int x, int y, int button, int clickCount) throws IllegalArgumentException {
		int buttonModifiers = getButtonModifiers(button);
		MouseEvent[] me = new MouseEvent[clickCount * 3];
		long lagTime = System.currentTimeMillis();
		int count = 1;

		for (int i = 0; i < me.length; i += 3) {
			me[i] = new MouseEvent(target, MouseEvent.MOUSE_PRESSED, lagTime, buttonModifiers, x, y, count, false, button);
			lagTime += getRandom();
			me[i + 1] = new MouseEvent(target, MouseEvent.MOUSE_RELEASED, lagTime, buttonModifiers, x, y, count, false, button);
			me[i + 2] = new MouseEvent(target, MouseEvent.MOUSE_CLICKED, lagTime, buttonModifiers, x, y, count, false, button);
			lagTime += getRandom();
			++count;
		}

		return me;
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
		for (int i = 0; i < stars.size(); i++) {
			if (System.currentTimeMillis() - stars.get(i).when <= LENGTH) {
				stars.get(i).c = new Color(Color.HSBtoRGB(i / (float) stars.size(), 1, 1));
				stars.get(i).draw(g.create(), System.currentTimeMillis());
			} 
			else 
				stars.remove(i);
		}
	}
	private static final long LENGTH = 400;
	private static final double DISTANCE = 10;
	public final static ArrayList<Star> stars = new ArrayList<Star>();
	public class Ray {
		public double rot;
		public Ray(double rot) {
			this.rot = rot;
		}
		public void draw(Graphics g1, double j, Color c) {
			Graphics2D g = (Graphics2D) g1;
			g.rotate(rot);
			g.setColor(c);
			g.drawLine(0, 0, (int) (j), (int) (j));
		}
	}
	public class Star extends ArrayList<Ray> {
		private static final long serialVersionUID = -1137739803879792377L;
		long when;
		Point where;
		Color c;
		public Star(long when, Point where) {
			for (int i = 0; i < 100; i++)
				add(new Ray(Math.random() * 2 * Math.PI));
			this.where = where;
			this.when = when;
		}
		public void draw(Graphics g, long time) {
			g.translate(where.x, where.y);
			for (Ray r : this)
				r.draw(g, dist(time), c);
		}
		public double dist(long time) {
			return DISTANCE - (double) (time - when) / (LENGTH / DISTANCE);
		}
	}
}
