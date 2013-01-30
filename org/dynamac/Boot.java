/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* The application entry point.                         *
********************************************************/
package org.dynamac;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.Logger;
import org.dynamac.gui.ClientGUI;
import org.dynamac.settings.Settings;
import org.dynamac.script.LoaderDef;
import org.dynamac.loader.ClassLoader;

public class Boot{
	private static final Logger log = new Logger(Boot.class.getSimpleName());
	public static void main(String[] args) {
		//TODO Cleanup this Boot class; it b ugly
		Data.clientGUI = new ClientGUI();
		log.log("Starting up Dynamac!");
		JFileChooser c = new JFileChooser();    
		c.setCurrentDirectory(new java.io.File(Settings.getLastLoaderDirectory()));c.setDialogTitle("Select loader script...");
		c.setFileSelectionMode(JFileChooser.FILES_ONLY);
		c.setFileFilter(new FileFilter() {
	        public boolean accept(File f) {
	            return f.getName().toLowerCase().endsWith(".class") ||
	            f.getName().toLowerCase().endsWith(".jar")
	                || f.isDirectory();
	          }

	          public String getDescription() {
	            return "Dynamac Loader Scripts";
	          }
	        });
		c.setAcceptAllFileFilterUsed(false);
		if (c.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			Settings.writeToRegistry("LastLoaderLocation", c.getSelectedFile().toString());
			ClassLoader loaderScriptLoader = new ClassLoader(c.getSelectedFile());
			if(loaderScriptLoader!=null){
				try{
					Class<?> scriptClass = loaderScriptLoader.loadClass(c.getSelectedFile().getName().substring(0, c.getSelectedFile().getName().indexOf(".")));
					Object scriptObject = scriptClass.newInstance();
					if(scriptObject instanceof LoaderDef){
						LoaderDef loaderScript = (LoaderDef)scriptObject;
						loaderScript.start();
						log.log("Started loader script!");
					}
					else{
						log.log("Selected file is not a valid loader script!");
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		else{
			System.exit(0);
		}
	}
}
