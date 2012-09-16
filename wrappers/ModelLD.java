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

import org.dynamac.bot.api.methods.Calculations;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class ModelLD extends Model{
	public Object currentObject;
	public ClassHook currentHook;
	public ModelLD(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("ModelLD");
	}
	public int[] getVerticiesX(){
		FieldHook fh = currentHook.getFieldHook("getVerticiesX");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public int[] getVerticiesY(){
		FieldHook fh = currentHook.getFieldHook("getVerticiesY");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public int[] getVerticiesZ(){
		FieldHook fh = currentHook.getFieldHook("getVerticiesZ");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public short[] getTriangleX(){
		FieldHook fh = currentHook.getFieldHook("getTriangleX");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public short[] getTriangleY(){
		FieldHook fh = currentHook.getFieldHook("getTriangleY");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public short[] getTriangleZ(){
		FieldHook fh = currentHook.getFieldHook("getTriangleZ");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public short[] getTriangleColor(){
		FieldHook fh = currentHook.getFieldHook("getTriangleColor");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public int[][] projectVertices(int localX, int localY) {
		if(Calculations.matrixCache.length==0)
			return new int[][]{{}};
		float[] data = Calculations.matrixCache;
		double locX = (localX+0.5)*512;
		double locY = (localY+0.5)*512;
		int numVertices = Math.min(getVerticiesX().length, Math.min(getVerticiesY().length, getVerticiesZ().length));
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
			int vertexX = (int) (getVerticiesX()[index] + locX);
			int vertexY = getVerticiesY()[index] + height;
			int vertexZ = (int) (getVerticiesZ()[index] + locY);
			
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
