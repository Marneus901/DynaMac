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

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Animable;
import org.dynamac.bot.api.wrappers.AnimableNode;
import org.dynamac.bot.api.wrappers.AnimableObject;
import org.dynamac.bot.api.wrappers.BoundaryObject;
import org.dynamac.bot.api.wrappers.FloorObject;
import org.dynamac.bot.api.wrappers.Ground;
import org.dynamac.bot.api.wrappers.WallObject;


public class Objects {
	public static FloorObject[] getAllFloorObjects(){
		try{
			ArrayList<FloorObject> allObjects = new ArrayList<FloorObject>();
			Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
			Ground[][] a1 = array[Client.getPlane()];
			int x=0;
			for(Ground[] a2 : a1){
				if(a2==null)
					continue;
				int y=0;
				for(Ground gd : a2){
					if(gd==null)
						continue;
					if(gd.getFloorDecoration()!=null){
						FloorObject fo = new FloorObject(gd.getFloorDecoration().currentObject, x, y);
						if(fo!=null)
							allObjects.add(fo);
					}
					y++;
				}
				x++;
			}
			return allObjects.toArray(new FloorObject[]{});
		}
		catch(Exception e){

		}
		return new FloorObject[]{};
	}
	public static WallObject[] getAllWallObjects(){
		try{
			ArrayList<WallObject> allObjects = new ArrayList<WallObject>();
			Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
			Ground[][] a1 = array[Client.getPlane()];
			int x=0;
			for(Ground[] a2 : a1){
				if(a2==null)
					continue;
				int y=0;
				for(Ground gd : a2){
					if(gd==null)
						continue;
					if(gd.getWallDecoration1()!=null){
						WallObject fo = new WallObject(gd.getWallDecoration1().currentObject, x, y);
						if(fo!=null)
							allObjects.add(fo);
					}
					if(gd.getWallDecoration2()!=null){
						WallObject fo = new WallObject(gd.getWallDecoration2().currentObject, x, y);
						if(fo!=null)
							allObjects.add(fo);
					}
					y++;
				}
				x++;
			}
			return allObjects.toArray(new WallObject[]{});
		}
		catch(Exception e){

		}
		return new WallObject[]{};
	}
	public static BoundaryObject[] getAllBoundaryObjects(){
		try{
			ArrayList<BoundaryObject> allObjects = new ArrayList<BoundaryObject>();
			Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
			Ground[][] a1 = array[Client.getPlane()];
			int x=0;
			for(Ground[] a2 : a1){
				if(a2==null)
					continue;
				int y=0;
				for(Ground gd : a2){
					if(gd==null)
						continue;
					if(gd.getBoundary1()!=null){
						BoundaryObject fo = new BoundaryObject(gd.getBoundary1().currentObject, x, y);
						if(fo!=null)
							allObjects.add(fo);
					}
					if(gd.getBoundary2()!=null){
						BoundaryObject fo = new BoundaryObject(gd.getBoundary2().currentObject, x, y);
						if(fo!=null)
							allObjects.add(fo);
					}
					y++;
				}
				x++;
			}
			return allObjects.toArray(new BoundaryObject[]{});
		}
		catch(Exception e){

		}
		return new BoundaryObject[]{};
	}
	public static AnimableObject[] getAllAnimableObjects(){
		try{
			ArrayList<AnimableObject> allObjects = new ArrayList<AnimableObject>();
			Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
			Ground[][] a1 = array[Client.getPlane()];
			for(Ground[] a2 : a1){
				if(a2==null)
					continue;
				for(Ground gd : a2){
					if(gd==null)
						continue;
					AnimableNode list = gd.getAnimableList();
					if(list!=null){
						Animable data = list.getAnimable();
						if(data!=null){
							AnimableObject fo = new AnimableObject(data.currentObject);
							if(fo!=null)
								allObjects.add(fo);
						}
					}
				}
			}
			return allObjects.toArray(new AnimableObject[]{});
		}
		catch(Exception e){
		}
		return new AnimableObject[]{};
	}
}
