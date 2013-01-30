/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's bytecode analyzer gui for method analysis. *
********************************************************/
package org.dynamac.analyzer.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import org.dynamac.analyzer.node.TreeNode;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Modifier;
import org.objectweb.asm.util.Printer;

public class MethodAnalyzer extends javax.swing.JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public String[] getInvokedFromList(){
		ArrayList<String> list = new ArrayList<String>();
		for(TreeNode cn : AnalyzerMain.root.childrenArray){
			TreeNode methodsNode = cn.getChildByText("Methods");
			for(TreeNode mn : methodsNode.childrenArray){
				int i=0;
				for(AbstractInsnNode instr : mn.currentMethod.instructions.toArray()){
					i++;
					if(instr.getType()==AbstractInsnNode.METHOD_INSN){
						MethodInsnNode methodIns = (MethodInsnNode)instr;
						String s = methodIns.owner+"."+methodIns.name+""+methodIns.desc;
						TreeNode parentClass = (TreeNode) currentNode.getParent().getParent();
						if(s.equals(parentClass.currentClass.name+"."+currentNode.currentMethod.name+""+currentNode.currentMethod.desc)){
							list.add(cn.currentClass.name+"."+mn.currentMethod.name+mn.currentMethod.desc+":"+i);
							invokedFromList.add(mn);
						}
					}
				}
			}
		}
		return list.toArray(new String[]{});
	}
	public String[] getInvokeList(){
		ArrayList<String> list = new ArrayList<String>();
		int i=0;
		for(AbstractInsnNode instr : currentNode.currentMethod.instructions.toArray()){
			i++;
			if(instr.getType()==AbstractInsnNode.METHOD_INSN){
				MethodInsnNode methodIns = (MethodInsnNode)instr;
				TreeNode clazzNode = AnalyzerMain.root.getChildByText(methodIns.owner);
				if(clazzNode==null)
					continue;
				TreeNode methodsNode = clazzNode.getChildByText("Methods");
				for(TreeNode curr : methodsNode.childrenArray){
					String sub = curr.currentString.substring(curr.currentString.indexOf(" ")+1, curr.currentString.length());
					if(sub.equals(methodIns.name+" "+methodIns.desc)){
						System.out.println("Attempting to get TreeNode : "+sub);
						if(!list.contains(clazzNode.currentString+"."+sub+":"+i)){
							list.add(clazzNode.currentString+"."+sub+":"+i);
							invokeList.add(curr);
							break;
						}
					}
				}
			}
		}
		return list.toArray(new String[]{});
	}
	public String[] getFieldList(){
		ArrayList<String> list = new ArrayList<String>();
		int i=0;
		for(AbstractInsnNode instr : currentNode.currentMethod.instructions.toArray()){
			i++;
			if(instr.getType()==AbstractInsnNode.FIELD_INSN){
				FieldInsnNode fieldIns = (FieldInsnNode)instr;
				String s = fieldIns.desc+" "+fieldIns.owner+"."+fieldIns.name;
				TreeNode classNode = AnalyzerMain.root.getChildByText(fieldIns.owner);
				if(classNode!=null){
					TreeNode fieldsNode = classNode.getChildByText("Fields");
					for(TreeNode curr : fieldsNode.childrenArray){
						if(curr.currentField.name.equals(fieldIns.name)){
							list.add(s+":"+i);
							fieldList.add(curr);
							break;
						}
					}
				}
			}
		}
		return list.toArray(new String[]{});
	}
	public String[] getInstructions(){
		ArrayList<String> list = new ArrayList<String>();
		int i=0;
		for(AbstractInsnNode instr : currentNode.currentMethod.instructions.toArray()){
			i++;
			final int opcode = instr.getOpcode();
			final String mnemonic = opcode==-1?"":Printer.OPCODES[instr.getOpcode()];
			String curr = (i+" : "+mnemonic+" ");
			boolean ignore = false;
			switch (instr.getType()) {
			case AbstractInsnNode.LABEL: 
				ignore=true;
				break;
			case AbstractInsnNode.FRAME:
				ignore=true;
				break;
			case AbstractInsnNode.LINE:
				ignore=true;
			case AbstractInsnNode.INSN:
				break;
			case AbstractInsnNode.INT_INSN:
				if (instr.getOpcode()==Opcodes.NEWARRAY) {
					curr = curr.concat(Printer.TYPES[((IntInsnNode)instr).operand]);
				} else {
					curr = curr.concat(""+((IntInsnNode)instr).operand);
				}
				break;
			case AbstractInsnNode.JUMP_INSN:
			{
				final LabelNode targetInstruction = ((JumpInsnNode)instr).label;
				final int targetId = currentNode.currentMethod.instructions.indexOf(targetInstruction);
				curr = curr.concat(""+targetId);
				break;
			}
			case AbstractInsnNode.LDC_INSN:
				curr = curr.concat(""+((LdcInsnNode)instr).cst);
				break;
			case AbstractInsnNode.IINC_INSN:
				curr = curr.concat(""+((IincInsnNode)instr).var);
				curr = curr.concat(" ");
				curr = curr.concat(""+((IincInsnNode)instr).incr);
				break;
			case AbstractInsnNode.TYPE_INSN:
				curr = curr.concat(((TypeInsnNode)instr).desc);
				break;
			case AbstractInsnNode.VAR_INSN:
				curr = curr.concat(""+((VarInsnNode)instr).var);
				break;
			case AbstractInsnNode.FIELD_INSN:
				curr = curr.concat(((FieldInsnNode)instr).owner);
				curr = curr.concat(".");
				curr = curr.concat(((FieldInsnNode)instr).name);
				curr = curr.concat(" ");
				curr = curr.concat(((FieldInsnNode)instr).desc);
				break;
			case AbstractInsnNode.METHOD_INSN:
				curr = curr.concat(((MethodInsnNode)instr).owner);
				curr = curr.concat(".");
				curr = curr.concat(((MethodInsnNode)instr).name);
				curr = curr.concat(" ");
				curr = curr.concat(((MethodInsnNode)instr).desc);
				break;
			case AbstractInsnNode.MULTIANEWARRAY_INSN:
				curr = curr.concat(((MultiANewArrayInsnNode)instr).desc);
				curr = curr.concat(" ");
				curr = curr.concat(""+((MultiANewArrayInsnNode)instr).dims);
				break;
			case AbstractInsnNode.LOOKUPSWITCH_INSN:
			{
				final List<?> keys = ((LookupSwitchInsnNode)instr).keys;
				final List<?> labels = ((LookupSwitchInsnNode)instr).labels;
				for (int t=0; t<keys.size(); t++) {
					final int key = (Integer)keys.get(t);
					final LabelNode targetInstruction = (LabelNode)labels.get(t);
					final int targetId = currentNode.currentMethod.instructions.indexOf(targetInstruction);
					curr = curr.concat(key+": "+targetId+", ");
				}
				final LabelNode defaultTargetInstruction = ((LookupSwitchInsnNode)instr).dflt;
				final int defaultTargetId = currentNode.currentMethod.instructions.indexOf(defaultTargetInstruction);
				curr = curr.concat("default: "+defaultTargetId);
				break;
			}
			case AbstractInsnNode.TABLESWITCH_INSN:
			{
				final int minKey = ((TableSwitchInsnNode)instr).min;
				final List<?> labels = ((TableSwitchInsnNode)instr).labels;
				for (int t=0; t<labels.size(); t++) {
					final int key = minKey+t;
					final LabelNode targetInstruction = (LabelNode)labels.get(t);
					final int targetId = currentNode.currentMethod.instructions.indexOf(targetInstruction);
					curr = curr.concat(key+": "+targetId+", ");
				}
				final LabelNode defaultTargetInstruction = ((TableSwitchInsnNode)instr).dflt;
				final int defaultTargetId = currentNode.currentMethod.instructions.indexOf(defaultTargetInstruction);
				curr = curr.concat("default: "+defaultTargetId);
				break;
			}
			}	
			if(!ignore)
				list.add(curr);
		}
		return list.toArray(new String[]{});
	}
	public MethodAnalyzer(TreeNode n) {
		currentNode=n;
		new Thread(this).start();
	}
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private void initComponents() {

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		accessFlagText = new javax.swing.JLabel();
		descriptionText = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane4 = new javax.swing.JScrollPane();
		fieldJList = new javax.swing.JList();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		invokeJList = new javax.swing.JList();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		invokedFromJList = new javax.swing.JList();
		jPanel5 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		instructionJList = new javax.swing.JList();
        invokeList = new ArrayList<TreeNode>();
        invokedFromList = new ArrayList<TreeNode>();
        fieldList = new ArrayList<TreeNode>();
        
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		TreeNode parentClass = (TreeNode) currentNode.getParent().getParent();
		setTitle(Modifier.convertAccessMaskToModifier(currentNode.currentMethod.access)+" "+parentClass.currentClass.name+"."+currentNode.currentMethod.name+" "+currentNode.currentMethod.desc);
		
		accessFlagText.setText("Access Flag : "+currentNode.currentMethod.access);

		descriptionText.setText("Description : "+currentNode.currentMethod.desc);

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(accessFlagText)
								.addComponent(descriptionText))
								.addContainerGap(520, Short.MAX_VALUE))
		);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(accessFlagText)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(descriptionText)
						.addContainerGap(323, Short.MAX_VALUE))
		);

		jTabbedPane1.addTab("Properties", jPanel1);

		fieldJList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = getFieldList();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		fieldJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				fieldListValueChanged(evt);
			}
		});
		jScrollPane4.setViewportView(fieldJList);

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addContainerGap())
		);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addContainerGap())
		);

		jTabbedPane1.addTab("Fields Referenced", jPanel2);

		invokeJList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = getInvokeList();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		invokeJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				invokeListValueChanged(evt);
			}
		});
		jScrollPane3.setViewportView(invokeJList);

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addContainerGap())
		);
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addContainerGap())
		);

		jTabbedPane1.addTab("Methods Invoked", jPanel3);

		invokedFromJList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = getInvokedFromList();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		invokedFromJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				invokedFromListValueChanged(evt);
			}
		});
		jScrollPane2.setViewportView(invokedFromJList);

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(
				jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addContainerGap())
		);
		jPanel4Layout.setVerticalGroup(
				jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addContainerGap())
		);

		jTabbedPane1.addTab("Invoked From", jPanel4);

		instructionJList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = getInstructions();
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		jScrollPane1.setViewportView(instructionJList);

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(
				jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addContainerGap())
		);
		jPanel5Layout.setVerticalGroup(
				jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addContainerGap())
		);

		jTabbedPane1.addTab("Bytecode Instructions", jPanel5);

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
	private void invokedFromListValueChanged(javax.swing.event.ListSelectionEvent evt) {
    	if(!evt.getValueIsAdjusting()){
    		new MethodAnalyzer(invokedFromList.get(invokedFromJList.getSelectedIndex()));
    	}
	}
	private void invokeListValueChanged(javax.swing.event.ListSelectionEvent evt) {
    	if(!evt.getValueIsAdjusting()){
    		new MethodAnalyzer(invokeList.get(invokeJList.getSelectedIndex()));
    	}
	}
	private void fieldListValueChanged(javax.swing.event.ListSelectionEvent evt) {
    	if(!evt.getValueIsAdjusting()){
    		new FieldAnalyzer(fieldList.get(fieldJList.getSelectedIndex()));
    	}
	}
	private javax.swing.JLabel accessFlagText;
	private javax.swing.JLabel descriptionText;
	private javax.swing.JList<?> fieldJList;
	ArrayList<TreeNode> fieldList;
	private javax.swing.JList<?> instructionJList;
	private javax.swing.JList<?> invokeJList;
	ArrayList<TreeNode> invokeList;
	private javax.swing.JList<?> invokedFromJList;
	ArrayList<TreeNode> invokedFromList;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTabbedPane jTabbedPane1;
	TreeNode currentNode;
	public void run() {
		initComponents();
	}
}