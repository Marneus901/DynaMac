package org.dynamac.bot.api.methods;

import java.awt.event.KeyEvent;

import org.dynamac.bot.api.wrappers.GameObject;
import org.dynamac.bot.api.wrappers.InterfaceChild;
import org.dynamac.bot.api.wrappers.Tile;

public class Camera {
	public static int getAngleTo(int degrees) {
		int ca = getYaw();
		if (ca < degrees) {
			ca += 360;
		}
		int da = ca - degrees;
		if (da > 180) {
			da -= 360;
		}
		return da;
	}
	public static int getAngleTo(org.dynamac.bot.api.wrappers.Character n) {
		return getAngleTo(n.getLocation());
	}
	public static int getAngleTo(GameObject o) {
		return getAngleTo(o.getLocation());
	}
	public static int getAngleTo(Tile t) {
		int a = (Calculations.angleToTile(t) - 90) % 360;
		return a < 0 ? a + 360 : a;
	}
	public static int getX(){
		return Client.getCameraX();
	}
	public static int getY(){
		return Client.getCameraY();
	}
	public static int getZ(){
		return Client.getCameraZ();
	}
	public static int getPitch(){
		//Min - 2, max 100 - in percent.
		return (int) ((Client.getCameraPitch()-1022)/20.48);
	}
	public static int getYaw(){
		//in degrees, 0 or 360 is north
		return (int) (Client.getCameraYaw()/45.51);
	}

	public static boolean isCameraYawNorth()
	{
		int yaw = Client.getCameraYaw();
		return yaw > 15900 || yaw < 31;
	}

	public static boolean isCameraYawWest()
	{
		int yaw = Client.getCameraYaw();
		return yaw > 3700 && yaw < 4400;
	}

	public static boolean isCameraYawEast()
	{
		int yaw = Client.getCameraYaw();
		return yaw > 11900 && yaw < 12500;
	}

	public static boolean isCameraYawSouth()
	{
		int yaw = Client.getCameraYaw();
		return yaw > 7700 && yaw < 8600;
	}

	public static boolean isUp(){
		return getPitch()>=95;
	}
	public static void setAngle(int degrees) {
		if (getAngleTo(degrees) > 5) {
			Keyboard.pressKey((char) KeyEvent.VK_LEFT);
			for(int i=0;i<20;++i){
				int curr = getAngleTo(degrees);
				if(curr<=5)
					break;
				else if(curr>5)
					i=0;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
			Keyboard.releaseKey((char) KeyEvent.VK_LEFT);
		} else if (getAngleTo(degrees) < -5) {
			Keyboard.pressKey((char) KeyEvent.VK_RIGHT);
			for(int i=0;i<20;++i){
				int curr = getAngleTo(degrees);
				if(curr>=-5)
					break;
				else if(curr<-5)
					i=0;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
			Keyboard.releaseKey((char) KeyEvent.VK_RIGHT);
		}
	}
	public static void setCompass(final char direction) {
		switch (direction) {
		case 'n':
			setAngle(359);
			break;
		case 'w':
			setAngle(89);
			break;
		case 's':
			setAngle(179);
			break;
		case 'e':
			setAngle(269);
			break;
		default:
			setAngle(359);
			break;
		}
	}
	public static void setNorth(){
		InterfaceChild compass = Interfaces.get(548, 156);
		if(compass!=null)
			compass.doAction("Face North");
	}
	public static void setPitch(boolean up){
		int curr = getPitch();
		Keyboard.pressKey(up?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
		for(int i=0;i<20;++i){
			if(getPitch()>curr){
				i=0;
				curr=getPitch();
			}
			if(up && isUp())
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		Keyboard.releaseKey(up?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
	}
	public static void setPitch(int alt){
		int curr = getPitch();
		Keyboard.pressKey(alt>curr?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
		for(int i=0;i<20;++i){
			if(getPitch()>curr){
				i=0;
				curr=getPitch();
			}
			if(getPitch()>=alt)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		Keyboard.releaseKey(alt>curr?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
	}
	public static void turnTo(org.dynamac.bot.api.wrappers.Character c) {
		int angle = getAngleTo(c);
		setAngle(angle);
	}
	public static void turnTo(GameObject o) {
		int angle = getAngleTo(o);
		setAngle(angle);
	}
	public static void turnTo(Tile tile) {
		int angle = getAngleTo(tile);
		setAngle(angle);
	}

}
