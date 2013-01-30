/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's injection methods.                         *
********************************************************/
package org.dynamac.methods;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.dynamac.enviroment.Data;
import org.dynamac.injection.AbstractTransform;
import org.dynamac.reflection.ClassHook;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class Injector {
	private static ArrayList<AbstractTransform> injectionTransforms = new ArrayList<AbstractTransform>();
	/**
	 * Add an injection transform for the client.
	 * Transform must extend {@link org.dynamac.injection.AbstractTransform}
	 * @param transform
	 */
	public static void addTransform(AbstractTransform transform){
		injectionTransforms.add(transform);
	}
	/**
	 * Runs through the list of transforms and applies changes to the client.
	 */
	public static void runInjection(){
		for(ClassHook ch : Data.REFLECTION_CLIENT_HOOKS.values()){
			for(AbstractTransform transform : injectionTransforms){
				transform.injectClass(ch);
			}
		}
		try{
			HashMap<JarEntry, byte[]> clientData = new HashMap<JarEntry, byte[]>();
			JarFile theJar = new JarFile(Data.clientFile);
			Enumeration<?> en = theJar.entries();
			while (en.hasMoreElements()) {
				JarEntry entry = (JarEntry) en.nextElement();
				if(entry.getName().startsWith("META"))
					continue;
				byte[] buffer = new byte[1024];
				int read;
				InputStream is = theJar.getInputStream(entry);
				byte[] allByteData = new byte[0];
				while ((read = is.read(buffer)) != -1){
					byte[] tempBuff = new byte[read+allByteData.length];
					for(int i=0;i<allByteData.length;++i)
						tempBuff[i]=allByteData[i];
					for(int i=0;i<read;++i)
						tempBuff[i+allByteData.length]=buffer[i];
					allByteData=tempBuff;
				}
				clientData.put(entry, allByteData);
				is.close();
			}

			FileOutputStream stream = new FileOutputStream(Data.clientFile);
			JarOutputStream out = new JarOutputStream(stream);
			for(JarEntry entry : clientData.keySet()){
				byte[] data = clientData.get(entry);
				if(entry.getName().endsWith(".class")){
					ClassHook hook = Data.REFLECTION_CLIENT_HOOKS.get(entry.getName().substring(0, entry.getName().indexOf(".class")));
					if(hook!=null){
						ClassNode cn = hook.getBytecodeClass();
						ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
						cn.accept(cw);
						data = cw.toByteArray();
					}
				}
				JarEntry newEntry = new JarEntry(entry.getName());
				out.putNextEntry(newEntry);
				out.write(data);
			}
			out.close();
			stream.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
