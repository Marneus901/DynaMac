/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer main window.             *
********************************************************/
package org.dynamac.analyzer.gui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.dynamac.analyzer.node.TreeNode;
import org.dynamac.enviroment.Data;
import org.dynamac.reflection.ClassHook;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Modifier;

public class AnalyzerMain extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	public static TreeNode root = null;
	private String fileName = "";
    public AnalyzerMain(String file) {
    	fileName = file;
    	createNodes();
        initComponents();
    }
    public void createNodes(){
    	String[] keys = Data.REFLECTION_CLIENT_HOOKS.keySet().toArray(new String[]{});
    	root = new TreeNode(fileName);
    	Arrays.sort(keys);
    	for(String s : keys){
    		ClassHook ch = Data.REFLECTION_CLIENT_HOOKS.get(s);
    		ClassNode cn = ch.getBytecodeClass();
    		if(cn!=null){
    			TreeNode clazz = new TreeNode(cn.name, cn);
    			TreeNode clazz1 = new TreeNode("Fields");
    			for(FieldNode fn : cn.fields){
    				clazz1.addChild(new TreeNode(Modifier.convertAccessMaskToModifier(fn.access)+" "+fn.desc+" "+fn.name, fn));
    			}
    			clazz.addChild(clazz1);
    			TreeNode clazz2 = new TreeNode("Methods");
    			for(MethodNode mn : cn.methods){
    				clazz2.addChild(new TreeNode(Modifier.convertAccessMaskToModifier(mn.access)+" "+mn.name+" "+mn.desc, mn));
    			}
    			clazz.addChild(clazz2);
        		root.addChild(clazz);
    		}
    	}
    }
	public ArrayList<String> sortStrings(ArrayList<String> s){
		int a,b;
		String temp;
		for(a=0;a<s.size()-1;++a){
			for(b=0;b<s.size()-1;++b){
				if(s.get(b).compareTo(s.get(b+1))>0){
					temp=s.get(b);
					s.set(b, s.get(b+1));
					s.set(b+1, temp);
				}
			}
		}
		return s;
	}
    private void initComponents() {
		setTitle("Dynamac ASM Analyzer : Main Window ("+root.toString()+")");
        jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree(root);
		jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        openClassName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        openFieldName = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        openMethodName = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				jTree1MousePressed(evt);
			}
		});
		
        jScrollPane1.setViewportView(jTree1);

        jLabel1.setText("Open Class By Name");

        jButton1.setText("Open Analyzer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Open Field By Name (class.field; e.g. \"ab.c\")");

        jButton2.setText("Open Analyzer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Open Method By Name (e.g. \"abc.def (IIZ)V\" )");

        jButton3.setText("Open Analyzer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Search Window");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("View Hierarchy");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("Expanding the tree will display fields and methods of");

        jLabel5.setText("the expanded class.");

        jLabel6.setText("Right click any class, field, or method to open up the");

        jLabel7.setText("analysis window for the referenced object.");

        jLabel8.setText("Clicking on an item in any of the lists in the analysis");

        jLabel9.setText("windows will open a new analysis window for that");

        jLabel10.setText("object; this allows the tracing to happen effeciently.");

        jLabel11.setText("©2013 Dynamac.org - Marneus901");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(openMethodName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(openClassName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(openFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2))
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel11)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openMethodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String name = openClassName.getText();
        TreeNode node = root.getChildByText(name);
        if(node!=null)
        	new ClassAnalyzer(node);
        else
        	JOptionPane.showMessageDialog(null, "No such class found in client!");
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    	try{
	        String text = openFieldName.getText();
	        String className = text.substring(0, text.lastIndexOf("."));
	        TreeNode classNode = root.getChildByText(className);
	        if(classNode!=null){
	        	String fieldName = text.substring(text.lastIndexOf(".")+1, text.length());
	        	TreeNode fieldsNode = classNode.getChildByText("Fields");
	        	for(TreeNode curr : fieldsNode.childrenArray){
	        		if(curr.currentField.name.equals(fieldName)){
		        		new FieldAnalyzer(curr);
		        		return;
	        		}
	        	}
	        	JOptionPane.showMessageDialog(null, "No such field found in client!");
	        }
	        else
	        	JOptionPane.showMessageDialog(null, "No such class found in client!");
    	}
    	catch(Exception e){
    		JOptionPane.showMessageDialog(null, "Invalid input!");
    	}
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    	String s = openMethodName.getText();
    	String className = s.substring(0, s.indexOf("."));
    	String methodName = s.substring(s.indexOf(".")+1, s.indexOf(" "));
    	String methodDesc = s.substring(s.indexOf(" ")+1, s.length());
    	TreeNode classNode = AnalyzerMain.root.getChildByText(className);
    	if(classNode!=null){
    		TreeNode methodsNode = classNode.getChildByText("Methods");
    		if(methodsNode!=null){//Should not happen, every class TreeNode has Fields and Methods for children
    			for(TreeNode method : methodsNode.childrenArray){
    				if(method.currentMethod.name.equals(methodName) && method.currentMethod.desc.equals(methodDesc)){
    					new MethodAnalyzer(method);
    					return;
    				}
    			}
    	    	JOptionPane.showMessageDialog(null, "Method not found!");
    		}
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "Class not found!");
    	}
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO search window
    	JOptionPane.showMessageDialog(null, "Not yet implemented!");
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
    	new ClassHierarchy();
    }
	private void jTree1MousePressed(java.awt.event.MouseEvent evt) {
		if(evt.getButton()==MouseEvent.BUTTON3){
			TreePath path = jTree1.getPathForLocation(evt.getX(), evt.getY());
			if(path!=null){
				System.out.println(path.toString());
				TreeNode node = (TreeNode)path.getLastPathComponent();
				menu = new SubMenu(node);
				menu.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		}
	}
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTextField openClassName;
    private javax.swing.JTextField openFieldName;
    private javax.swing.JTextField openMethodName;
	private SubMenu menu;
}
