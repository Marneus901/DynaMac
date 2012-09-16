package org.dynamac.bot.api.wrappers;

import org.dynamac.bot.api.methods.Calculations;
import org.dynamac.bot.api.methods.Client;
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
	public int getUnknownInt(int index){
		FieldHook fh = currentHook.getFieldHook("getUnknownInt"+index);
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return -1;
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
	public ObjectDef getObjectDef(){
		try{
			Node ref = Nodes.lookup(Client.getRSData().getObjectDefLoaders().getCache().getTable(), (long)getID());
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
		ObjectDefLoader odl = getObjectDefLoader();
		if(odl!=null){
			ObjectComposite oc = odl.getComposite();
			if(oc!=null){
				ModelLD model = oc.getModel();
				if(model!=null){
					return model;
				}
			}
		}
		return null;
	}	
	public ModelLD getLDModel2(){
		FieldHook fh = currentHook.getFieldHook("getModel");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new ModelLD(data);
		}
		return null;
	}	
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getLDModel();
		if(model==null)
			return new int[][]{{-1, -1, -1}};
		double locX = (localX+0.5)*512;
		double locY = (localY+0.5)*512;
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
