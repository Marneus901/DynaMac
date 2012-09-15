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

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.dynamac.bot.api.wrappers.TileData;
import org.dynamac.enviroment.Data;


public class Calculations {	
	public static float[] matrixCache = new float[]{};
	public double distance(int x1, int x2, int y1, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	public static double distanceTo(int x, int y) {
		int x2 = Client.getMyPlayer().getLocationX();
		int y2 = Client.getMyPlayer().getLocationY();
		return Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
	}
	public Polygon getTilePolygon(int x, int y){
		Polygon p = new Polygon();
		Point center = Calculations.locationToScreen(x, y);
		Point n = Calculations.locationToScreen(x, y+1);
		Point s = Calculations.locationToScreen(x, y-1);
		Point e = Calculations.locationToScreen(x+1, y);
		Point w = Calculations.locationToScreen(x-1, y);
		p.addPoint((center.x+n.x+w.x)/3, (center.y+n.y+w.y)/3);
		p.addPoint((center.x+s.x+w.x)/3, (center.y+s.y+w.y)/3);
		p.addPoint((center.x+s.x+e.x)/3, (center.y+s.y+e.y)/3);
		p.addPoint((center.x+n.x+e.x)/3, (center.y+n.y+e.y)/3);
		return p;
	}
	public static Point groundToScreen(int x, int y) {
		try{
			int z = tileHeight(x, y);
			return worldToScreen(x, z, y);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	public static Point groundToScreen(int x, int y, int height) {
		try{
			int z = tileHeight(x, y)-height;
			return worldToScreen(x, z, y);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	public boolean isOnScreen(int x, int y){
		return new Rectangle(0, 0, Data.CLIENT_APPLET.getWidth(), Data.CLIENT_APPLET.getHeight()).contains(new Point(x, y));
	}
	public static Point locationToScreen(int x, int y, int height){
		x = x-Client.getRSData().getBaseInfo().getX();
		y = y-Client.getRSData().getBaseInfo().getY();
		return groundToScreen((int) ((x + 0.5) * 512), (int) ((y + 0.5) * 512), height);
	}
	public static Point locationToScreen(int x, int y){
		x = x-Client.getRSData().getBaseInfo().getX();
		y = y-Client.getRSData().getBaseInfo().getY();
		return groundToScreen((int) ((x + 0.5) * 512), (int) ((y + 0.5) * 512));
	}
	public static int tileHeight(int x, int y) {
		try{
			int p = Client.getPlane();
			int x1 = x >> 9;
			int y1 = y >> 9;
			byte[][][] settings = Client.getRSData().getGroundBytes().getBytes();
			if (settings != null && x1 >= 0 && x1 < 104 && y1 >= 0 && y1 < 104) {
				if (p <= 3 && (settings[1][x1][y1] & 2) != 0) {
					++p;
				}
				TileData[] planes = Client.getRSData().getGroundInfo().getTileData();
				if (planes != null && p < planes.length && planes[p] != null) {
					int[][] heights = planes[p].getHeights();
					if (heights != null) {
						int x2 = x & 0x200 - 1;
						int y2 = y & 0x200 - 1;
						int start_h = heights[x1][y1] * (0x200 - x2) + heights[x1 + 1][y1] * x2 >> 9;
						int end_h = heights[x1][1 + y1] * (0x200 - x2) + heights[x1 + 1][y1 + 1] * x2 >> 9;
			return start_h * (512 - y2) + end_h * y2 >> 9;
					}
				}
			}
		}
		catch(Exception e){
		}
		return 0;
	}		
	public static Point worldToMap(int x, int y){	
		x-= Client.getRSData().getBaseInfo().getX();	
		y-= Client.getRSData().getBaseInfo().getY();	
		int regionTileX = Client.getMyPlayer().getLocationX() - Client.getRSData().getBaseInfo().getX();
		int regionTileY = Client.getMyPlayer().getLocationY() - Client.getRSData().getBaseInfo().getY();

		final int cX = (int) (x * 4 + 2) - (regionTileX << 9) / 0x80;		
		final int cY = (int) (y * 4 + 2) - (regionTileY << 9) / 0x80;		
		final int actDistSq = cX * cX + cY * cY;	
		final int mmDist = Math.max(154 / 2, 154 / 2) + 10;	
		if (mmDist * mmDist >= actDistSq) {		
			int angle = 0x3fff & (int)Client.getMinimapAngle();		
			final boolean mmsettingis4 = Client.getMinimapSetting()==4;		
			if (!mmsettingis4){			
				angle = 0x3fff & Client.getMinimapOffset() + (int) Client.getMinimapAngle();	
			}	
			int sin = SIN_TABLE[angle];	
			int cos = COS_TABLE[angle];		
			if (!mmsettingis4) {		
				final int fact = 0x100 + Client.getMinimapScale();		
				sin = 0x100 * sin / fact;			
				cos = 0x100 * cos / fact;		
			}	
			return new Point((cos * cX + sin * cY >> 0xf) + 550 + 154 / 2, -(cos * cY - sin * cX >> 0xf) + 58 + 154 / 2);	
		}	
		return new Point(-1, -1);
	}
	public static Point worldToScreen(final int x, final int y, final int z) {
		if(matrixCache.length==0)
			return new Point(-1, -1);
		float[] data = matrixCache;
		final float _z = z * data[11] + (data[15] + x * data[3] + y * data[7]);
		final float _x = data[8] * z + (data[4] * y + (data[12] + data[0] * x));
		final float _y = data[9] * z + (data[5] * y + (data[1] * x + data[13]));	
		return new Point(			
			Math.round((int)(260.0 + (256.0 * _x) / _z))-4,	
			Math.round((int)(171.0 + (167.0 * _y) / _z))		
		);		
	}								
	public static final int[] SIN_TABLE = new int[0x4000];	
	public static final int[] COS_TABLE = new int[0x4000];	
	static {
		final double d = 0.00038349519697141029D;	
		for (int i = 0; i < 0x4000; i++) {		
			Calculations.SIN_TABLE[i] = (int) (32768D * Math.sin(i * d));	
			Calculations.COS_TABLE[i] = (int) (32768D * Math.cos(i * d));	
		}	
	}
}
