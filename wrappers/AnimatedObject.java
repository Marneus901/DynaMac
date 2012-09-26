package org.dynamac.bot.api.wrappers;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.dynamac.bot.api.methods.Calculations;
import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.Menu;
import org.dynamac.bot.api.methods.Mouse;
import org.dynamac.bot.api.methods.Nodes;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;

public class AnimatedObject{
	public Object currentObject;
	public ClassHook currentHook;
	public AnimatedObject(Object data, short x, short y) {
		localX=x;
		localY=y;
		currentObject = data;
		currentHook = Data.indentifiedClasses.get("AnimatedObject");
	}
	private int localX;
	private int localY;
	public boolean clickModel(){
		if(isOnScreen() && getLDModel()!=null){
			int[][] pts = projectVertices();
			int randInd = new Random().nextInt(pts.length);
			Point p = new Point(pts[randInd][0], pts[randInd][1]);
			if(p.x>0 && p.x<515 && p.y>54 && p.y<388){
				Mouse.clickMouse(p, 1);
				return true;
			}
		}
		return false;
	}
	public boolean clickTile(){
		if(isOnScreen()){
			Polygon p = Calculations.getTilePolygon(getLocationX(), getLocationY());
			Rectangle r = p.getBounds();
			Point pt = new Point(new Random().nextInt(r.width)+r.x, new Random().nextInt(r.height)+r.y);
			if(pt.x>0 && pt.x<515 && pt.y>54 && pt.y<388){
				Mouse.clickMouse(pt, 1);
				return true;
			}
		}
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
	public boolean containsPoint(Point p){
		for(Polygon poly : getWireframe())
			if(poly.contains(p))
				return true;
		return false;
	}
	public Point getRandomPoint(){
		int[][] pts = projectVertices();
		if(pts.length>0){
			int i = new Random().nextInt(pts.length);
			return new Point(pts[i][0], pts[i][1]);
		}
		return new Point(-1, -1);
	}
	public int getID(){
		FieldHook fh = currentHook.getFieldHook("getID");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return -1;
	}
	public Interactable getInteractable(){
		FieldHook fh = currentHook.getFieldHook("getInteractable");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Interactable(data);
		}
		return null;
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
	public int getLocationX(){
		try{
			return localX+Client.getRSData().getBaseInfo().getX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocationY(){
		try{
			return localY+Client.getRSData().getBaseInfo().getY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocalX(){
		return localX;
	}
	public int getLocalY(){
		return localY;
	}
	public ObjectDefLoader getObjectDefLoader(){
		FieldHook fh = currentHook.getFieldHook("getObjectDefLoader");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new ObjectDefLoader(data);
		}
		return null;
	}
	public ModelLD getLDModel(){
		ObjectDefLoader loader = getObjectDefLoader();
		if(loader!=null){
			Cache modelCache = loader.getModelCache();
			if(modelCache!=null){
				Node model = Nodes.lookup(modelCache.getTable(), getModelHash());
				if(model!=null){
					Object def=null;
					if (model.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("SoftReference").getClassName())) {
						SoftReference sr = new SoftReference(model.currentObject);
						def = sr.getSoftReference().get();
					}
					else if (model.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("HardReference").getClassName())) {
						HardReference hr = new HardReference(model.currentObject);
						def = hr.getHardReference();
					}
					if(def!=null){
						return new ModelLD(def);
					}
				}
			}
		}
		return null;
	}	
	public long getModelHash(){
		long modelHash = (getID()<<10) + (10 << 3) + getOrientation();
		modelHash |= 0 << 29;
		ObjectDefLoader loader = getObjectDefLoader();
		if(loader!=null){
			Cache modelCache = loader.getModelCache();
			if(modelCache!=null){
				modelHash = (getID()<<10) + (4 << 3) + getOrientation();
				modelHash |= 0 << 29;
				Node model = Nodes.lookup(modelCache.getTable(), modelHash);
				if(model!=null)
					return modelHash;
				modelHash = (getID()<<10) + (10 << 3) + getOrientation();
				modelHash |= 0 << 29;
				return modelHash;
			}
		}
		return modelHash;
	}
	public Point[] getModelPoints(){
		ModelLD model = getLDModel();
		if(model==null)
			return new Point[]{};
		ArrayList<Point> pts = new ArrayList<Point>();
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
	public int getOrientation(){
		FieldHook fh = currentHook.getFieldHook("getOrientation");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return 0;
	}
	public boolean isOnScreen(){
		Point p = Calculations.locationToScreen(getLocationX(), getLocationY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	public Polygon[] getWireframe(){
		ModelLD model = getLDModel();
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
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getLDModel();
		if(model==null){
			return new int[][]{{-1, -1, -1}};
		}
		try{
			double locX = (localX+0.5)*512;
			double locY = (localY+0.5)*512;
			Interactable inter = getInteractable();
			if(inter!=null && inter.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("AnimatedAnimableObject").getClassName())){
				Animable anim = new Animable(inter.currentObject);
				locX = (anim.getMinX()+0.5)*512;
				locY = (anim.getMinY()+0.5)*512;
				if(anim.getMinX()!=anim.getMaxX()){
					locX+=(anim.getMaxX()+0.5)*512;
					locX/=2;
				}
				if(anim.getMinY()!=anim.getMaxY()){
					locY+=(anim.getMaxY()+0.5)*512;
					locY/=2;
				}
			}
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
