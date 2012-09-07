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
import java.util.Collections;

import org.dynamac.bot.api.wrappers.Animable;
import org.dynamac.bot.api.wrappers.AnimableNode;
import org.dynamac.bot.api.wrappers.AnimableObject;
import org.dynamac.bot.api.wrappers.AnimatedAnimableObject;
import org.dynamac.bot.api.wrappers.AnimatedBoundaryObject;
import org.dynamac.bot.api.wrappers.AnimatedFloorObject;
import org.dynamac.bot.api.wrappers.AnimatedObject;
import org.dynamac.bot.api.wrappers.AnimatedWallObject;
import org.dynamac.bot.api.wrappers.Boundary;
import org.dynamac.bot.api.wrappers.BoundaryObject;
import org.dynamac.bot.api.wrappers.FloorDecoration;
import org.dynamac.bot.api.wrappers.FloorObject;
import org.dynamac.bot.api.wrappers.Ground;
import org.dynamac.bot.api.wrappers.WallDecoration;
import org.dynamac.bot.api.wrappers.WallObject;
import org.dynamac.enviroment.Data;


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
	public static AnimatedObject[] getAllAnimatedObjects(){
		ArrayList<AnimatedObject> animatedObjects = new ArrayList<AnimatedObject>();
		Ground[][][] tiles = Client.getRSData().getGroundInfo().getGroundArray();
		for(int x=0;x<tiles[Client.getPlane()].length;++x){
			for(int y=0;y<tiles[Client.getPlane()][x].length;++y){
				Ground g1 = tiles[Client.getPlane()][x][y];
				if(g1==null)
					continue;
				if(g1.getBoundary1()!=null){
					Boundary object = g1.getBoundary1();
					if(!object.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("BoundaryObject").getClassName())){
						animatedObjects.add(new AnimatedBoundaryObject(object.currentObject, x, y).getAnimatedObject());
					}
				}
				else if(g1.getBoundary2()!=null){
					Boundary object = g1.getBoundary2();
					if(!object.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("BoundaryObject").getClassName())){
						animatedObjects.add(new AnimatedBoundaryObject(object.currentObject, x, y).getAnimatedObject());
					}
				}
				else if(g1.getWallDecoration1()!=null){
					WallDecoration object = g1.getWallDecoration1();
					if(!object.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("WallObject").getClassName())){
						animatedObjects.add(new AnimatedWallObject(object.currentObject, x, y).getAnimatedObject());
					}
				}
				else if(g1.getWallDecoration2()!=null){
					WallDecoration object = g1.getWallDecoration2();
					if(!object.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("WallObject").getClassName())){
						animatedObjects.add(new AnimatedWallObject(object.currentObject, x, y).getAnimatedObject());
					}
				}
				else if(g1.getFloorDecoration()!=null){
					FloorDecoration object = g1.getFloorDecoration();
					if(!object.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("FloorObject").getClassName())){
						animatedObjects.add(new AnimatedFloorObject(object.currentObject, x, y).getAnimatedObject());
					}
				}
				else if(g1.getAnimableList()!=null){
					AnimableNode node = g1.getAnimableList();
					Animable object = node.getAnimable();
					if(!object.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("AnimableObject").getClassName())){
						animatedObjects.add(new AnimatedAnimableObject(object.currentObject).getAnimatedObject());
					}
				}
			}
		}
		animatedObjects.removeAll(Collections.singleton(null));
		return animatedObjects.toArray(new AnimatedObject[]{});
	}
	public static AnimableObject[] getAnimatedObjectsByID(int inputID) {
		ArrayList<AnimatedObject> allAnimableObjects = new ArrayList<AnimatedObject>();
		for (AnimatedObject o : Objects.getAllAnimatedObjects()) { 
			if(o.getID() == inputID) allAnimableObjects.add(o); 
		}
		return allAnimableObjects.toArray(new AnimableObject[]{});
	}
	/**
	 * Credits Natfoth
	 * @param inputID
	 * @return
	 */
	public static AnimableObject[] getAnimableObjectsByID(int inputID) {
		ArrayList<AnimableObject> allAnimableObjects = new ArrayList<AnimableObject>();
		for (AnimableObject o : Objects.getAllAnimableObjects()) { 
			if(o.getID() == inputID) allAnimableObjects.add(o); 
		}
		return allAnimableObjects.toArray(new AnimableObject[]{});
	}
	/**
	 * Credits Natfoth
	 * @param inputID
	 * @return
	 */
	public static BoundaryObject[] getBoundaryObjectsByID(int inputID) {
		ArrayList<BoundaryObject> allBoundaryObjects = new ArrayList<BoundaryObject>();
		for (BoundaryObject o : Objects.getAllBoundaryObjects()) { 
			if(o.getID() == inputID) allBoundaryObjects.add(o); 
		}
		return allBoundaryObjects.toArray(new BoundaryObject[]{});
	}
	/**
	 * Credits Natfoth
	 * @param inputID
	 * @return
	 */
	public static FloorObject[] getFloorObjectsByID(int inputID) {
		ArrayList<FloorObject> allFloorObjects = new ArrayList<FloorObject>();
		for (FloorObject o : Objects.getAllFloorObjects()) { 
			if(o.getID() == inputID) allFloorObjects.add(o); 
		}
		return allFloorObjects.toArray(new FloorObject[]{});
	}
	/**
	 * Credits Natfoth
	 * @param inputID
	 * @return
	 */
	public static WallObject[] getWallObjectsByID(int inputID) {
		ArrayList<WallObject> allWallObjects = new ArrayList<WallObject>();
		for (WallObject o : Objects.getAllWallObjects()) { 
			if(o.getID() == inputID) allWallObjects.add(o); 
		}
		return allWallObjects.toArray(new WallObject[]{});
	}
}
