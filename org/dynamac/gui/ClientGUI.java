/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's 'main screen'; for starting scripts and    *
* logging/debugging.                                   *
********************************************************/
package org.dynamac.gui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.dynamac.analyzer.gui.AnalyzerMain;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.Logger;
import org.dynamac.script.ScriptDef;
import org.dynamac.settings.Settings;
import org.dynamac.loader.ClassLoader;

public class ClientGUI extends javax.swing.JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public ClientGUI() {
		new Thread(this).start();
    }
	public void updateLog(){
		String log = "";
		for(String s : Logger.getLogs()){
			log = log.concat(s+"\n");
		}
		if(jTextArea1!=null){
			jTextArea1.setText(log);
			jTextArea1.setCaretPosition(log.length());
		}
	}
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		startScript = new MenuItem("Start Script");
		startScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	startScriptActionPerformed(evt);
            }
        });
		fileMenu.add(startScript);
		openASMAnalyzer = new MenuItem("Open Analyzer");
		openASMAnalyzer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	openASMAnalyzerActionPerformed(evt);
            }
        });
		fileMenu.add(openASMAnalyzer);
		menuBar.add(fileMenu);
		setMenuBar(menuBar);
		
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setVisible(true);
    }

	private void openASMAnalyzerActionPerformed(java.awt.event.ActionEvent evt) {

		//TESTING PURPOSES - SHOULD NOT BE INVOKED FROM SCRIPT --//
		new AnalyzerMain(Data.clientFile.getName()).setVisible(true);
	}
	@SuppressWarnings("deprecation")
	private void startScriptActionPerformed(java.awt.event.ActionEvent evt) {
    	if(Data.CURRENT_SCRIPT==null){
			JFileChooser c = new JFileChooser();    
			c.setCurrentDirectory(new java.io.File(Settings.getLastScriptDirectory()));
			c.setDialogTitle("Select script...");
			c.setFileSelectionMode(JFileChooser.FILES_ONLY);
			c.setFileFilter(new FileFilter() {
		        public boolean accept(File f) {
		            return f.getName().toLowerCase().endsWith(".class") ||
		            f.getName().toLowerCase().endsWith(".jar")
		                || f.isDirectory();
		          }
	
		          public String getDescription() {
		            return "Dynamac Scripts";
		          }
		        });
			c.setAcceptAllFileFilterUsed(false);
			if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				Settings.writeToRegistry("LastScriptLocation", c.getSelectedFile().toString());
				try {
					ClassLoader loader = new ClassLoader(c.getSelectedFile());
					if(loader!=null){
						Class<?> scriptClass = loader.loadClass(c.getSelectedFile().getName().substring(0, c.getSelectedFile().getName().indexOf(".")));
						Object scriptObject = scriptClass.newInstance();
						if(scriptObject instanceof ScriptDef){
							Data.CURRENT_SCRIPT = (ScriptDef)scriptObject;
							Data.CURRENT_SCRIPT.start();
							startScript.setLabel("Stop Script");
						}
						else{
							System.out.println("Selected file is not a valid script!");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    	}
    	else{
			startScript.setLabel("Start Script");
    		Data.CURRENT_SCRIPT.stop();
    		Data.CURRENT_SCRIPT=null;
    		Runtime.getRuntime().gc();
    	}
    }
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
	private MenuBar menuBar;
	private Menu fileMenu;
	public static MenuItem startScript;//grr so bad
	public MenuItem openASMAnalyzer;
	@Override
	public void run() {
        initComponents();
	}
}