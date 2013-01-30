/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer gui for field analysis.  *
********************************************************/
package org.dynamac.analyzer.gui;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import org.dynamac.analyzer.node.TreeNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.util.Modifier;

public class FieldAnalyzer extends javax.swing.JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public String[] getAccessedFrom(){
		ArrayList<String> list = new ArrayList<String>();
		for(TreeNode cn : AnalyzerMain.root.childrenArray){
			TreeNode methodsNode = cn.getChildByText("Methods");
			for(TreeNode mn : methodsNode.childrenArray){
				int i=0;
				for(AbstractInsnNode instr : mn.currentMethod.instructions.toArray()){
					i++;
					if(instr.getType()==AbstractInsnNode.FIELD_INSN){
						FieldInsnNode fieldIns = (FieldInsnNode)instr;
						TreeNode parentClass = (TreeNode) currentNode.getParent().getParent();
						String s = fieldIns.owner+"."+fieldIns.name;
						if(s.equals(parentClass.currentClass.name+"."+currentNode.currentField.name)){
							list.add(cn.currentClass.name+"."+mn.currentMethod.name+mn.currentMethod.desc+":"+i);
							accessedFromList.add(mn);
						}
					}
				}
			}
		}
		return list.toArray(new String[]{});
	}
    public FieldAnalyzer(TreeNode node) {
        currentNode = node;
		new Thread(this).start();
    }
    @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accessedFromJList = new javax.swing.JList();
        accessedFromList = new ArrayList<TreeNode>();
        
		TreeNode parentClass = (TreeNode) currentNode.getParent().getParent();
		setTitle(Modifier.convertAccessMaskToModifier(currentNode.currentField.access)+" "+currentNode.currentField.desc+" "+parentClass.currentClass.name+"."+currentNode.currentField.name);
		
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jLabel1.setText("Access Flag : "+currentNode.currentField.access);

        jLabel2.setText("Signiture : "+currentNode.currentField.desc);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(368, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Properties", jPanel1);

        accessedFromJList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getAccessedFrom();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        accessedFromJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                accessedFromJListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(accessedFromJList);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Accessed From", jPanel2);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        setVisible(true);
    }
    private void accessedFromJListValueChanged(javax.swing.event.ListSelectionEvent evt) {
    	if(!evt.getValueIsAdjusting()){
    		new MethodAnalyzer(accessedFromList.get(accessedFromJList.getSelectedIndex()));
    	}
    }
    private javax.swing.JList<?> accessedFromJList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    ArrayList<TreeNode> accessedFromList;
    TreeNode currentNode;
	public void run() {
		initComponents();
	}
}