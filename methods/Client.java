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

import java.awt.Rectangle;
import java.lang.reflect.Array;

import org.dynamac.bot.api.wrappers.BaseInfo;
import org.dynamac.bot.api.wrappers.DetailInfoNode;
import org.dynamac.bot.api.wrappers.HashTable;
import org.dynamac.bot.api.wrappers.Info;
import org.dynamac.bot.api.wrappers.Interface;
import org.dynamac.bot.api.wrappers.ItemDefLoader;
import org.dynamac.bot.api.wrappers.MenuGroupNode;
import org.dynamac.bot.api.wrappers.NPCNode;
import org.dynamac.bot.api.wrappers.NodeList;
import org.dynamac.bot.api.wrappers.NodeSubQueue;
import org.dynamac.bot.api.wrappers.ObjectDefLoader;
import org.dynamac.bot.api.wrappers.Player;
import org.dynamac.bot.api.wrappers.RenderLD;
import org.dynamac.bot.api.wrappers.Settings;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.FieldHook;
import org.dynamac.rsapplet.canvas.Canvas;


public class Client {
	public static int getBaseX(){
		Info rsdata = getRSData();
		if(rsdata!=null){
			BaseInfo baseinfo = rsdata.getBaseInfo();
			if(baseinfo!=null){
				return baseinfo.getX();
			}
		}
		return -1;
	}
	public static int getBaseY(){
		Info rsdata = getRSData();
		if(rsdata!=null){
			BaseInfo baseinfo = rsdata.getBaseInfo();
			if(baseinfo!=null){
				return baseinfo.getY();
			}
		}
		return -1;
	}
	public static int getCameraPitch(){
		FieldHook fh = Data.runtimeStaticFields.get("getCameraPitch");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getCameraX(){
		FieldHook fh = Data.runtimeStaticFields.get("getCameraX");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getCameraY(){
		FieldHook fh = Data.runtimeStaticFields.get("getCameraY");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getCameraYaw(){
		FieldHook fh = Data.runtimeStaticFields.get("getCameraYaw");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getCameraZ(){
		FieldHook fh = Data.runtimeStaticFields.get("getCameraZ");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static Canvas getCanvas(){
		FieldHook fh = Data.runtimeStaticFields.get("getCanvas");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Canvas)data;
		}
		return null;
	}
	public static Object getClient(){
		FieldHook fh = Data.runtimeStaticFields.get("getClient");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data;
		}
		return null;
	}
	public static NodeSubQueue getCollapsedMenuItems(){
		FieldHook fh = Data.runtimeStaticFields.get("getCollapsedMenuItems");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new NodeSubQueue(data);
		}
		return null;
	}
	public static String getCurrentAction(){
		FieldHook fh = Data.runtimeStaticFields.get("getCurrentAction");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public static MenuGroupNode getCurrentMenuGroupNode(){
		FieldHook fh = Data.runtimeStaticFields.get("getCurrentMenuGroupNode");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new MenuGroupNode(data);
		}
		return null;
	}
	public static int getDestinationX(){
		FieldHook fh = Data.runtimeStaticFields.get("getDestinationX");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null){
				Info rsdata = getRSData();
				if(rsdata!=null){
					BaseInfo baseinfo = rsdata.getBaseInfo();
					if(baseinfo!=null){
						int localDest = (((Integer)data) * fh.getMultiplier());
						if(localDest!=-1)
							return localDest + baseinfo.getX();
					}
				}
			}
		}
		return -1;
	}
	public static int getDestinationY(){
		FieldHook fh = Data.runtimeStaticFields.get("getDestinationY");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null){
				Info rsdata = getRSData();
				if(rsdata!=null){
					BaseInfo baseinfo = rsdata.getBaseInfo();
					if(baseinfo!=null){
						int localDest = (((Integer)data) * fh.getMultiplier());
						localDest/=4;
						if(localDest!=-1)
							return localDest + baseinfo.getY();
					}
				}
			}
		}
		return -1;
	}
	public static DetailInfoNode getDetailInfoNode(){
		FieldHook fh = Data.runtimeStaticFields.get("getDetailInfoNode");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new DetailInfoNode(data);
		}
		return null;
	}
	public static Rectangle[] getInterfaceBoundsArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getInterfaceBoundsArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Rectangle[])data;
		}
		return new Rectangle[]{};
	}
	public static Interface[] getInterfaceCache(){
		FieldHook fh = Data.runtimeStaticFields.get("getInterfaceCache");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null){
				Interface[] array = new Interface[Array.getLength(data)];
				for(int i=0;i<array.length;++i)
					array[i]=new Interface(i, Array.get(data, i));
				return array;
			}
		}
		return new Interface[]{};
	}
	public static HashTable getInterfaceNodeCache(){
		FieldHook fh = Data.runtimeStaticFields.get("getInterfaceNodeCache");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
	public static int getInterfaceIndex(){
		FieldHook fh = Data.runtimeStaticFields.get("getInterfaceIndex");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static ItemDefLoader getItemDefLoader(){
		FieldHook fh = Data.runtimeStaticFields.get("getItemDefLoader");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new ItemDefLoader(data);
		}
		return null;
	}
	public static HashTable getItemHashTable(){
		FieldHook fh = Data.runtimeStaticFields.get("getItemHashTable");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
	public static boolean isItemSelected(){
		FieldHook fh = Data.runtimeStaticFields.get("isItemSelected");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public static boolean isLoggedIn(){
		try{
			return Client.getRSData().getGroundInfo()!=null;
		}
		catch(Exception e){
			return false;
		}
	}
	public static Object getKeyboard(){
		FieldHook fh = Data.runtimeStaticFields.get("getKeyboard");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data;
		}
		return null;
	}
	public static int[] getLevelArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getLevelArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static int[] getLevelExpArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getLevelExpArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static int getLoginIndex(){
		FieldHook fh = Data.runtimeStaticFields.get("getLoginIndex");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getLoopCycle(){
		FieldHook fh = Data.runtimeStaticFields.get("getLoopCycle");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int[] getMaxLevelArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getMaxLevelArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static boolean isMenuCollapsed(){
		FieldHook fh = Data.runtimeStaticFields.get("isMenuCollapsed");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public static boolean isMenuOpen(){
		FieldHook fh = Data.runtimeStaticFields.get("isMenuOpen");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public static NodeList getMenuItems(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuItems");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new NodeList(data);
		}
		return null;
	}
	public static int getMenuHeight(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuHeight");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMenuOptionsCount(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuOptionsCount");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMenuOptionsCountCollapsed(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuOptionsCountCollapsed");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMenuWidth(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuWidth");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMenuX(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuX");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMenuY(){
		FieldHook fh = Data.runtimeStaticFields.get("getMenuY");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static float getMinimapAngle(){
		FieldHook fh = Data.runtimeStaticFields.get("getMinimapAngle");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Float)data;
		}
		return -1;
	}
	public static int getMinimapOffset(){
		FieldHook fh = Data.runtimeStaticFields.get("getMinimapOffset");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMinimapScale(){
		FieldHook fh = Data.runtimeStaticFields.get("getMinimapScale");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getMinimapSetting(){
		FieldHook fh = Data.runtimeStaticFields.get("getMinimapSetting");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static Object getMouse(){
		FieldHook fh = Data.runtimeStaticFields.get("getMouse");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data;
		}
		return null;
	}
	public static Player getMyPlayer(){
		FieldHook fh = Data.runtimeStaticFields.get("getMyPlayer");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new Player(data);
		}
		return null;
	}
	public static int getNPCCount(){
		FieldHook fh = Data.runtimeStaticFields.get("getNPCCount");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int[] getNPCIndexArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getNPCIndexArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static NPCNode[] getNPCNodeArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getNPCNodeArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null){
				NPCNode[] array = new NPCNode[Array.getLength(data)];
				for(int i=0;i<array.length;++i)
					array[i]=new NPCNode(Array.get(data, i));
				return array;
			}
		}
		return new NPCNode[]{};
	}
	public static HashTable getNPCNodeCache(){
		FieldHook fh = Data.runtimeStaticFields.get("getNPCNodeCache");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
	public static ObjectDefLoader getObjectDefLoader(){
		FieldHook fh = Data.runtimeStaticFields.get("getObjectDefLoader");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new ObjectDefLoader(data);
		}
		return null;
	}
	public static String getPassword(){
		FieldHook fh = Data.runtimeStaticFields.get("getPassword");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public static int getPlane(){
		FieldHook fh = Data.runtimeStaticFields.get("getPlane");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static Player[] getPlayerArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getPlayerArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null){
				Player[] array = new Player[Array.getLength(data)];
				for(int i=0;i<array.length;++i)
					array[i]=new Player(Array.get(data, i));
				return array;
			}
		}
		return new Player[]{};
	}
	public static int getPlayerCount(){
		FieldHook fh = Data.runtimeStaticFields.get("getPlayerCount");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int[] getPlayerIndexArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getPlayerIndexArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static RenderLD getRenderLD(){
		FieldHook fh = Data.runtimeStaticFields.get("getRender");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new RenderLD(data);
		}
		return null;
	}
	public static Info getRSData(){
		FieldHook fh = Data.runtimeStaticFields.get("getRSData");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new Info(data);
		}
		return null;
	}
	public static String getSelectedItemName(){
		FieldHook fh = Data.runtimeStaticFields.get("getSelectedItemName");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public static Settings getSettings(){
		FieldHook fh = Data.runtimeStaticFields.get("getSettings");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return new Settings(data);
		}
		return null;
	}
	public static boolean isSpellSelected(){
		FieldHook fh = Data.runtimeStaticFields.get("isSpellSelected");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public static int[] getSkillExperienceMaxes(){
		FieldHook fh = Data.runtimeStaticFields.get("getSkillExperienceMaxes");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static int[] getSkillLevels(){
		FieldHook fh = Data.runtimeStaticFields.get("getSkillLevels");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static int[] getSkillLevelMaxesArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getSkillLevelMaxesArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public static int getSubMenuHeight(){
		FieldHook fh = Data.runtimeStaticFields.get("getSubMenuHeight");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getSubMenuWidth(){
		FieldHook fh = Data.runtimeStaticFields.get("getSubMenuWidth");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getSubMenuX(){
		FieldHook fh = Data.runtimeStaticFields.get("getSubMenuX");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static int getSubMenuY(){
		FieldHook fh = Data.runtimeStaticFields.get("getSubMenuY");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;
	}
	public static float[] getTileData(){
		FieldHook fh = Data.runtimeStaticFields.get("getTileData");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (float[])data;
		}
		return new float[]{};
	}
	public static boolean[] getValidInterfaceArray(){
		FieldHook fh = Data.runtimeStaticFields.get("getValidInterfaceArray");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return (boolean[])data;
		}
		return new boolean[]{};
	}
	public static String getUsername(){
		FieldHook fh = Data.runtimeStaticFields.get("getUsername");
		if(fh!=null){
			Object data = fh.getData(Data.CLIENT);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
