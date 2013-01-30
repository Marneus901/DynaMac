/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer gui for class analysis.  *
********************************************************/
package org.dynamac.analyzer.gui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.tree.TreePath;

import org.dynamac.analyzer.node.ClassTree;
import org.dynamac.analyzer.node.TreeNode;
import org.dynamac.enviroment.Data;
import org.dynamac.reflection.ClassHook;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.util.Modifier;

public class ClassAnalyzer extends javax.swing.JFrame implements Runnable{
	public boolean canBeAdded(ClassNode cg){
		if(Data.REFLECTION_CLIENT_HOOKS.containsKey(cg.superName))
			return false;
		for(ClassTree ci : hierarchyRoot.children){
			if(ci.isInFamily(cg))
				return false;
		}
		return true;
	}
	public ClassTree getHierarchy(){
		hierarchyRoot = new ClassTree();
		for(ClassHook ch : Data.REFLECTION_CLIENT_HOOKS.values()){
			ClassNode cg = ch.getBytecodeClass();
			if(canBeAdded(cg))
				hierarchyRoot.addChild(new ClassTree(cg));
		}
		for(ClassTree curr : hierarchyRoot.children){
			if(curr.isInFamily(currentNode.currentClass))
				return curr;
		}
		return null;
	}
    public String[] findTypeField(){
    	ArrayList<String> ret = new ArrayList<String>();
    	for(TreeNode clazz : AnalyzerMain.root.childrenArray){
    		TreeNode fieldList = clazz.getChildByText("Fields");
    		for(TreeNode field : fieldList.childrenArray){
    			FieldNode fn = field.currentField;
    			if(fn.desc.contains("L"+currentNode.currentClass.name+";")){
    				ret.add(clazz.currentString+"."+fn.name);
    				instancesList.add(field);
    			}
    		}
    	}
		return ret.toArray(new String[]{});
	}
    public String[] getFields(){
    	ArrayList<String> ret = new ArrayList<String>();
    	TreeNode fieldList = currentNode.getChildByText("Fields");
		for(TreeNode field : fieldList.childrenArray){
			ret.add(field.currentString);
			fieldsList.add(field);
		}
		return ret.toArray(new String[]{});
    }
    public boolean isRSClassType(FieldNode f){
    	return false;
    }
    public String[] getMethods(){
    	ArrayList<String> ret = new ArrayList<String>();
    	TreeNode methodList = currentNode.getChildByText("Methods");
		for(TreeNode method : methodList.childrenArray){
			ret.add(method.currentString);
			methodsList.add(method);
		}
		return ret.toArray(new String[]{});
    }
	private static final long serialVersionUID = 1L;
	public ClassAnalyzer(TreeNode n) {
		currentNode=n;
		new Thread(this).start();
    }
    @SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	private void initComponents() {
    	setTitle("Class Analysis : "+currentNode.toString());
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        superclassName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        accessFlag = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        instanceList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        fieldList = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        methodList = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        hierarchyFamily = new javax.swing.JTree(getHierarchy());
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        refactorName = new javax.swing.JTextField();
        instancesList = new ArrayList<TreeNode>();
        fieldsList = new ArrayList<TreeNode>();
        methodsList = new ArrayList<TreeNode>();
        
        hierarchyFamily.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	hierarchyFamilyMousePressed(evt);
            }
        });
        jButton1.setText("Refactor Name");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.setText("Generate Signiture");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jLabel1.setText("Superclass : ");

        superclassName.setText(currentNode.currentClass.superName);
        
        jLabel2.setText("Access Flag : ");

        accessFlag.setText(currentNode.currentClass.access+" ["+Modifier.convertAccessMaskToModifier(currentNode.currentClass.access)+"]");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(superclassName))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accessFlag))
                            .addComponent(refactorName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jButton2))
                .addContainerGap(316, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(superclassName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(accessFlag))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refactorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Properties", jPanel4);

        instanceList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = findTypeField();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        instanceList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                instanceListValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(instanceList);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Instance List", jPanel5);

        fieldList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getFields();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        fieldList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                fieldListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(fieldList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("Field List", jPanel1);

        methodList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getMethods();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        methodList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                methodListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(methodList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Method List", jPanel2);

        jScrollPane5.setViewportView(hierarchyFamily);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hierarchy Family", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setVisible(true);
    }

    private void instanceListValueChanged(javax.swing.event.ListSelectionEvent evt) {      
    	if(!evt.getValueIsAdjusting()){
    		new FieldAnalyzer(instancesList.get(instanceList.getSelectedIndex())).setVisible(true);
    	}
    }                                         

    private void fieldListValueChanged(javax.swing.event.ListSelectionEvent evt) {    
    	if(!evt.getValueIsAdjusting()){
    		new FieldAnalyzer(fieldsList.get(fieldList.getSelectedIndex()));
    	}
    }                                                                       

    private void methodListValueChanged(javax.swing.event.ListSelectionEvent evt) {   
    	if(!evt.getValueIsAdjusting()){
    		new MethodAnalyzer(methodsList.get(methodList.getSelectedIndex()));
    	}
    }                           
    private void hierarchyFamilyMousePressed(java.awt.event.MouseEvent evt) {
		if(evt.getButton()==MouseEvent.BUTTON3){
			TreePath path = hierarchyFamily.getPathForLocation(evt.getX(), evt.getY());
			if(path!=null){
				System.out.println(path.toString());
				ClassTree node = (ClassTree)path.getLastPathComponent();
				TreeNode classNode = AnalyzerMain.root.getChildByText(node.thisClass.name);
				if(classNode!=null){
					menu = new SubMenu(classNode);
					menu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		}
	}            

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        currentNode.refactoredName=refactorName.getText();
        setTitle("Class Analysis : "+currentNode.toString());
        //Enviroment.mainWindow.treeModel.reload(currentNode);
        //Enviroment.mainWindow.mainTree.setModel(Enviroment.mainWindow.treeModel);
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
       // System.out.println("Superclass : "+currentNode.currentClass.getSuperclassName());
       // System.out.println("Modifier : "+currentNode.currentClass.getModifiers());
        //getUniqueFields();
        //getMethodSignitures();
    	System.out.println("Signiture algorithm not developed!");
    }
    private javax.swing.JLabel accessFlag;
    private javax.swing.JList<?> fieldList;
    private javax.swing.JTree hierarchyFamily;
    private javax.swing.JList<?> instanceList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<?> methodList;
    private javax.swing.JLabel superclassName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField refactorName;
    TreeNode currentNode;
    ArrayList<TreeNode> instancesList;
    ArrayList<TreeNode> fieldsList;
    ArrayList<TreeNode> methodsList;
    TreeNode[] hierarchyClassList;
	public ClassTree hierarchyRoot;
	private SubMenu menu;
	public void run() {
		initComponents();
	}
}