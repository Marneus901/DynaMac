/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's main client methods.                       *
********************************************************/
package org.dynamac.methods;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.Logger;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class Client {
	private static final Logger log = new Logger(Client.class.getSimpleName());//Logger as well
	/**
	 * Returns the currently set LoaderDef ClassLoader.
	 * *Can be null.
	 * @return classLoader
	 */
	public static ClassLoader getClientLoader(){
		return Data.CLIENT_CLASSLOADER;
	}
	/**
	 * Sets the ClassLoader object to load a LoaderDef script.
	 * See {@link org.dynamac.loader.ClassLoader} or set a custom ClassLoader.
	 * @param loader
	 */
	public static void setClientloader(ClassLoader loader){
		Data.CLIENT_CLASSLOADER = loader;
	}
	/**
	 * Loads the clients bytecode into memory, and initializes
	 * reflection hooks (before loading reflection).
	 * @param client
	 */
	public static void loadClient(File client){
		Data.clientFile = client;
		if(client.getName().endsWith(".jar")){
			try{
				JarFile jar = new JarFile(client);
				Enumeration<?> en = jar.entries();
				while (en.hasMoreElements()) {
					JarEntry entry = (JarEntry) en.nextElement();
					if(entry.getName().endsWith(".class")){
						InputStream in = jar.getInputStream(entry);
						byte[] allByteData = new byte[0];
						byte[] buffer = new byte[1024];
						int read;
						while ((read = in.read(buffer)) != -1){
							byte[] tempBuff = new byte[read+allByteData.length];
							for(int i=0;i<allByteData.length;++i)
								tempBuff[i]=allByteData[i];
							for(int i=0;i<read;++i)
								tempBuff[i+allByteData.length]=buffer[i];
							allByteData=tempBuff;
						}
						ClassReader cr = new ClassReader(allByteData);
						ClassNode cn = new ClassNode();
						cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
						ClassHook hook = new ClassHook(cn.name, cn);
						for(FieldNode fn : cn.fields){
							hook.addFieldHook(new FieldHook(fn.name, fn));
						}
						Data.REFLECTION_CLIENT_HOOKS.put(cn.name, hook);
					}
				}
				log.info("Loaded "+Data.REFLECTION_CLIENT_HOOKS.size()+" classes!");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else
			log.error("File is not a loadable jar file.");
	}
	/**
	 * Creates an instance of the main boot class of the loaded client.
	 * The constructor must take no parameters, see overloaded method.
	 * Must have the jar loaded in order to start it.
	 * @param mainClass
	 * @param mainMethod
	 * @return The boot class instance object
	 */
	public static Object initializeClient(String mainClass, String mainMethod){//You specify what the main class is
		if(Data.CLIENT_CLASSLOADER!=null){
			try {
				Class<?> bootClass = Data.CLIENT_CLASSLOADER.loadClass(mainClass);
				if(bootClass!=null){
					Data.CLIENT_INSTANCE = bootClass.newInstance();
					log.info("Initialized main class : "+mainClass);
					for(Method m : Data.CLIENT_INSTANCE.getClass().getDeclaredMethods()){
						if(m.getName().equals(mainMethod)){
							m.invoke(Data.CLIENT_INSTANCE, new Object[]{});
							break;
						}
					}
					loadReflectionHooks();
					return Data.CLIENT_INSTANCE;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * "Turns-on" or "activates" the reflection hooks, *should* be done after the client
	 * has been initialized for proper class loading.
	 */
	public static void loadReflectionHooks(){
		for(ClassHook ch : Data.REFLECTION_CLIENT_HOOKS.values()){
			ch.loadRuntime();
		}
		log.info("Loaded reflection hooks...");
	}
}
