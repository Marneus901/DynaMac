package org.dynamac.bot.api.wrappers;

import java.awt.Point;
import java.awt.Polygon;
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
	@SuppressWarnings("unused")
	private int minx, miny, maxx, maxy;
	private byte plane;
	private ModelLD model;
	private int id;
	private int orientation=0;
	@SuppressWarnings("unused")//Used to see what type of object GameObject is referencing - do not remove.
	private Object reference;
	/*
	private int x = 0;
	private int y = 0;
	private int height = 0;
	private int width = 0;



	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}*/

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

	public boolean containsPoint(Point p){
		Polygon[] ps = getWireframe();
		for(int i=0;i<ps.length;++i)
			if(ps[i].contains(p)){
				return true;
			}
		return false;
	}
	public boolean doAction(String action){
		if(!Menu.isOpen()){
			Point p = getRandomPoint();
			if(p.equals(new Point(-1, -1))){
				return false;
			}
			if(!containsPoint(p)){
				return false;
			}
			//Mouse.moveMouse(p);
			Mouse.move(p);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			if(Menu.getIndex(action)==0){
				//Mouse.clickMouse();
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
				//Mouse.clickMouse(3);
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
	}/*
	public Point getModelPoint() {

		//if(obj == null) return new Point(-1, -1);

		final ModelLD mod = this.getModel();

		final int[] xPoints = mod.getVerticiesX();
		final int[] yPoints =mod.getVerticiesY();
		final int[] zPoints =mod.getVerticiesZ();

		final int i = new Random(0).nextInt(mod.getTriangleZ().length);

		final int i1 = mod.getTriangleX()[i],
				i2 = mod.getTriangleY()[i],
				i3 = mod.getTriangleZ()[i];

		final int ax = getX(), ay = getY();

		final Point[] indicePoints = new Point[3];

		indicePoints[0] = Calculations.worldToScreen(xPoints[i1] + ax,
				yPoints[i1] + Calculations.tileHeight(ax, ay),
				zPoints[i1] + ay);

		indicePoints[1] = Calculations.worldToScreen(xPoints[i2] + ax,
				yPoints[i2] + Calculations.tileHeight(ax, ay),
				zPoints[i2] + ay);

		indicePoints[2] = Calculations.worldToScreen(xPoints[i3] + ax,
				yPoints[i3] + Calculations.tileHeight(ax, ay),
				zPoints[i3] + ay);

		final int xPoint = blend(min(indicePoints[0].x, indicePoints[1].x, indicePoints[2].x), 
				max(indicePoints[0].x, indicePoints[1].x, indicePoints[2].x), new Random((long) 0.0).nextInt((int) 1.0));

		final int[][] xIndexes = new int[2][2];

		for(int xIndex = 0, xIndexCount = 0; xIndex < 3 && xIndexCount < 2; xIndex++) {
			final int x1 = indicePoints[xIndex].x;
			final int x2 = indicePoints[xIndex == 2 ? 0 : xIndex + 1].x;

			if(Math.min(x1, x2) <= Math.max(x1, x2)) {
				xIndexes[xIndexCount++] = new int[] {xIndex, xIndex == 2 ? 0 : xIndex + 1};
			}
		}

		final int d1 = Math.min(indicePoints[xIndexes[0][0]].x, indicePoints[xIndexes[0][1]].x) +
				Math.abs(indicePoints[xIndexes[0][0]].x - indicePoints[xIndexes[0][1]].x);
		final int d2 = Math.min(indicePoints[xIndexes[1][0]].x, indicePoints[xIndexes[1][1]].x) +
				Math.abs(indicePoints[xIndexes[1][0]].x - indicePoints[xIndexes[1][1]].x);

		final double xRatio1 = d1 == 0 ? 0.0 : xPoint / d1;
		final double xRatio2 = d2 == 0 ? 0.0 : xPoint / d2;

		final int yLimit1 = (int)(Math.abs(indicePoints[xIndexes[0][0]].y - indicePoints[xIndexes[0][1]].y) * xRatio1);
		final int yLimit2 = (int)(Math.abs(indicePoints[xIndexes[1][0]].y - indicePoints[xIndexes[1][1]].y) * xRatio2);

		final int yPoint = min(indicePoints[0].y, indicePoints[1].y, 
				indicePoints[2].y) + new Random(yLimit1).nextInt(yLimit2);

		return new Point(xPoint, yPoint);

	}*/
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
				pts.add(new Point(point1X, point1Y));
				pts.add(new Point(point2X, point2Y));
				pts.add(new Point(point3X, point3Y));
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
			Point[] pts = getModelPoints();
			if(pts.length>0)
				return pts[new Random().nextInt(pts.length)];
		}
		catch(Exception e){			
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	public Polygon[] getWireframe(){
		ModelLD model = getModel();
		if(model==null)
			return new Polygon[]{};
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		int[][] screenPoints = projectVertices();
		short[] trix = model.getTriangleX();
		short[] triy = model.getTriangleY();
		short[] triz = model.getTriangleZ();
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));
		for (int i = 0; i < numTriangles; i++) {
			try{
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
			catch(Exception e){}
		}
		return polys.toArray(new Polygon[]{});
	}
	public boolean isOnScreen(){
		if(Bank.isOpen())
			return false;
		Point p = Calculations.locationToScreen(getLocationX(), getLocationY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}/*
	private int min(final int... values) {
		int min = values[0];
		for(final int value : values) {
			if(value < min) {
				min = value;
			}
		}

		return min;
	}

	private int max(final int... values) {
		int max = values[0];
		for(final int value : values) {
			if(value > max) {
				max = value;
			}
		}

		return max;
	}*/
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getModel();
		if(model==null){
			return new int[][]{{-1, -1, -1}};
		}
		try{
			double locX = (getLocalX()+0.5)*512;
			double locY = (getLocalY()+0.5)*512;
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
		catch(Exception e){}
		return new int[][]{{}};
	}
}
