package org.dynamac.bot.api.wrappers;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.dynamac.bot.api.methods.Bank;
import org.dynamac.bot.api.methods.Calculations;
import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.Menu;
import org.dynamac.bot.api.methods.Mouse;
import org.dynamac.bot.api.methods.Nodes;
import org.dynamac.enviroment.Data;

public class GameObject {
	private int minx, miny, maxx, maxy;
	private byte plane;
	private ModelLD model;
	private int id;
	private int orientation=0;
	@SuppressWarnings("unused")//Used to see what type of object GameObject is referencing - do not remove.
	private Object reference;
	public GameObject(AnimableObject ao){
		minx=ao.getMinX();
		miny=ao.getMinY();
		maxx=ao.getMaxX();
		maxy=ao.getMaxY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
	public GameObject(AnimatedObject ao){
		Interactable inter = ao.getInteractable();
		if(inter!=null){
		Animable anim = new Animable(inter.currentObject);
		minx=anim.getMinX();
		miny=anim.getMinY();
		maxx=anim.getMaxX();
		maxy=anim.getMaxY();
		plane=anim.getPlane();
		}
		else{
			minx=ao.getLocalX();
			maxx=ao.getLocalX();
			miny=ao.getLocalY();
			maxy=ao.getLocalY();
		}
		model=ao.getLDModel();
		id=ao.getID();
		orientation=ao.getOrientation();
		reference=ao;
	}
	public GameObject(BoundaryObject ao){
		minx=(int) ao.getLocalX();
		miny=(int) ao.getLocalY();
		maxx=(int) ao.getLocalX();
		maxy=(int) ao.getLocalY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
	public GameObject(FloorObject ao){
		minx=(int) ao.getLocalX();
		miny=(int) ao.getLocalY();
		maxx=(int) ao.getLocalX();
		maxy=(int) ao.getLocalY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
	public GameObject(WallObject ao){
		minx=(int) ao.getLocalX();
		miny=(int) ao.getLocalY();
		maxx=(int) ao.getLocalX();
		maxy=(int) ao.getLocalY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
/*	public boolean click(){
		if(!containsPoint(Mouse.getLastMousePos())){
			Point p = getRandomPoint();
			if(p.equals(new Point(-1, -1)))
				return false;
			Mouse.moveMouse(p);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		if(Menu.contains(getName(), false)){
			int index=Menu.getFirstIndexByOption(getName());
			if(index==0){
				Mouse.clickMouse();
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
			Mouse.clickMouse(Mouse.getLastMousePos(), 3);
			for(int i=0;i<10;++i){
				if(Menu.isOpen())
					break;
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			}
			if(Menu.isOpen())
				return Menu.clickIndex(index);
			return false;
		}
		else{
			if(containsPoint(Mouse.getLastMousePos())){
				Mouse.clickMouse();
				return true;
			}
			return false;
		}
	}*/
	public boolean containsPoint(Point p){
		for(Polygon poly : getWireframe())
			if(poly.contains(p))
				return true;
		return false;
	}
	public boolean doAction(String action){
		if(!Menu.isOpen()){
			Point p = getRandomPoint();
			if(p.equals(new Point(-1, -1))){
				return false;
			}
			if(!containsPoint(p))
				return false;
			Mouse.moveMouse(p);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			if(Menu.getIndex(action)==0){
				Mouse.clickMouse();
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
				Mouse.clickMouse(Mouse.getLastMousePos(), 3);
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
	public int getID(){
		return id;
	}
	public int getLocalX(){
		return minx;
	}
	public int getLocalY(){
		return miny;
	}
	public Tile getLocation(){
		return new Tile(getLocationX(), getLocationY(), 0);
	}
	
	public Rectangle realBounds(Polygon[] polys) {
		int maxheight = -9999999;
		int minheight = 9999999;
		int maxwidth = -9999999;
		int minwidth = 9999999;
		int maxX = -9999999;
		int minX = 9999999;
		int maxY = -9999999;
		int minY = 9999999;

		for(Polygon p : polys) {
			if(p.getBounds().height > maxheight) {
				maxheight = p.getBounds().height;
			} else if(p.getBounds().height < minheight) {
				minheight = p.getBounds().height;
			}
			if(p.getBounds().width > maxwidth) {
				maxwidth = p.getBounds().width;
			} else if(p.getBounds().width < minwidth) {
				minwidth = p.getBounds().width;
			}
			if(p.getBounds().x > maxX) {
				maxX = p.getBounds().x;
			} else if(p.getBounds().x < minX) {
				minX = p.getBounds().x;
			}
			if(p.getBounds().y > maxY) {
				maxY = p.getBounds().y;
			} else if(p.getBounds().y < minY) {
				minY = p.getBounds().y;
			}
		}
		return new Rectangle(((maxX + minX)/2), ((maxY + minY)/2), ((maxwidth + minwidth)/2), ((maxheight + minheight)/2));
	}
	
	public Point getCenterOfModel() {
		Rectangle thetangle = new Rectangle(realBounds(getWireframe()).x, realBounds(getWireframe()).y, realBounds(getWireframe()).height, realBounds(getWireframe()).width);
		return new Point((int)thetangle.getCenterX(), (int)thetangle.getCenterY());
	}
	
	public int getLocationX(){
		try{
			return minx+Client.getBaseX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocationY(){
		try{
			return miny+Client.getBaseY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public ModelLD getModel(){
		return model;
	}
	public String getName(){
		ObjectDef def = getObjectDef();
		if(def!=null)
			return def.getName();
		return "null";
	}
	public ObjectDef getObjectDef(){
		try{
			Node ref = Nodes.lookup(Client.getRSData().getObjectDefLoaders().getDefCache().getTable(), (long)getID());
			if(ref==null)
				return null;
			if (ref.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("SoftReference").getClassName())) {
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ObjectDef(def);
			}
			else if (ref.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("HardReference").getClassName())) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ObjectDef(def);
			}
		}
		catch(Exception e){
		}
		return null;
	}
	public int getOrientation(){
		return orientation;
	}
	public Point[] getModelPoints(){
		if(model==null)
			return new Point[]{};
		ArrayList<Point> pts = new ArrayList<Point>();
		try{
			int[][] screenPoints = projectVertices();
			short[] trix = model.getTriangleX();
			short[] triy = model.getTriangleY();
			short[] triz = model.getTriangleZ();
			int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));;
			for (int i = 0; i < numTriangles; i++) {
				int index1 = trix[i];
				int index2 = triy[i];
				int index3 = triz[i];

				int point1X = screenPoints[index1][0];
				int point1Y = screenPoints[index1][1];
				int point2X = screenPoints[index2][0];
				int point2Y = screenPoints[index2][1];
				int point3X = screenPoints[index3][0];
				int point3Y = screenPoints[index3][1];
				if(point1X==-1 || point1Y==-1 ||
						point2X==-1 || point2Y==-1 ||
						point3X==-1 || point3Y==-1)
					continue;

				int avx = (point1X+point2X+point3X)/3;
				int avy = (point1Y+point2Y+point3Y)/3;
				pts.add(new Point(avx, avy));
			}
			return pts.toArray(new Point[]{});
		}
		catch(Exception e){

		}
		return new Point[]{};
	}
	public int getPlane(){
		return plane;
	}
	public Point getRandomPoint(){
		try{
			int[][] pts = projectVertices();
			if(pts.length>0){
				int i = new Random().nextInt(pts.length);
				return new Point(pts[i][0], pts[i][1]);
			}
		}
		catch(Exception e){			
		}
		return new Point(-1, -1);
	}
	public Polygon[] getWireframe(){
		if(model==null)
			return new Polygon[]{};
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		int[][] screenPoints = projectVertices();
		short[] trix = model.getTriangleX();
		short[] triy = model.getTriangleY();
		short[] triz = model.getTriangleZ();
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));;
		for (int i = 0; i < numTriangles; i++) {
			int index1 = trix[i];
			int index2 = triy[i];
			int index3 = triz[i];

			int point1X = screenPoints[index1][0];
			int point1Y = screenPoints[index1][1];
			int point2X = screenPoints[index2][0];
			int point2Y = screenPoints[index2][1];
			int point3X = screenPoints[index3][0];
			int point3Y = screenPoints[index3][1];
			if(point1X==-1 || point1Y==-1 ||
					point2X==-1 || point2Y==-1 ||
					point3X==-1 || point3Y==-1)
				continue;

			Polygon p = new Polygon();
			p.addPoint(point1X, point1Y);
			p.addPoint(point2X, point2Y);
			p.addPoint(point3X, point3Y);

			polys.add(p);
		}
		return polys.toArray(new Polygon[]{});
	}
	public boolean isOnScreen(){
		if(Bank.isOpen())
			return false;
		Point p = Calculations.locationToScreen(getLocationX(), getLocationY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		if(model==null)
			return new int[][]{{-1, -1, -1}};
		double locX = (minx+0.5)*512;
		double locY = (miny+0.5)*512;
		locX+=(maxx+0.5)*512;
		locX/=2;
		locY+=(maxy+0.5)*512;
		locY/=2;
		int numVertices = Math.min(model.getVerticiesX().length, Math.min(model.getVerticiesY().length, model.getVerticiesZ().length));
		int[][] screen = new int[numVertices][3];

		float xOff = data[12];
		float yOff = data[13];
		float zOff = data[15];
		float xX = data[0];
		float xY = data[4];
		float xZ = data[8];
		float yX = data[1];
		float yY = data[5];
		float yZ = data[9];
		float zX = data[3];
		float zY = data[7];
		float zZ = data[11];

		int height = Calculations.tileHeight((int)locX, (int)locY);
		for (int index = 0; index < numVertices; index++) {
			int vertexX = (int) (model.getVerticiesX()[index] + locX);
			int vertexY = model.getVerticiesY()[index] + height;
			int vertexZ = (int) (model.getVerticiesZ()[index] + locY);

			float _z = (zOff + (zX * vertexX + zY * vertexY + zZ * vertexZ));
			float _x = (xOff + (xX * vertexX + xY * vertexY + xZ * vertexZ));
			float _y = (yOff + (yX * vertexX + yY * vertexY + yZ * vertexZ));

			float fx = ((float)256.0 + ((float)256.0 * _x) / _z);
			float fy = ((float)166.0 + ((float)167.0 * _y) / _z);
			if(fx<520 && fx>0 && fy<390 && fy>50){
				screen[index][0] = (int)fx;
				screen[index][1] = (int)fy;
				screen[index][2] = 1;
			}
			else{
				screen[index][0] = -1;
				screen[index][1] = -1;
				screen[index][2] = 0;
			}
		}
		return screen;
	}
}
