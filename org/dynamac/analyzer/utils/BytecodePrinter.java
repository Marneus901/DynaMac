/*******************************************************
* I forget who made this........                       *
********************************************************/
package org.dynamac.analyzer.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.dynamac.enviroment.Data;
import org.dynamac.reflection.ClassHook;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class BytecodePrinter {
	/**
	 * Gets us the bytecode method body of a given method.
	 * @param className The class name to search for.
	 * @param methodName The method name.
	 * @param methodDescriptor The method's descriptor. 
	 *                         Can be null if one wishes to just get the first 
	 *                         method with the given name.
	 * @throws IOException
	 */
	public static String[] getMethod(String className, String methodName, String methodDescriptor) throws IOException {
		ClassHook ch = Data.REFLECTION_CLIENT_HOOKS.get(className);
		ClassNode cn = ch.getBytecodeClass();
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		byte[] data = cw.toByteArray();
		ClassReader classReader = new ClassReader(data);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, new SourceCodeTextifier(), printWriter);
		MethodSelectorVisitor methodSelectorVisitor = new MethodSelectorVisitor(traceClassVisitor, methodName, methodDescriptor);
		classReader.accept(methodSelectorVisitor, ClassReader.SKIP_DEBUG);

		return toList(stringWriter.toString());
	}

	/**
	 * Gets us the bytecode method body of a given method.
	 * @param className The class name to search for.
	 * @param methodName The method name.
	 * @throws IOException
	 */
	public static String[] getMethod(String className, String methodName) throws IOException {
		return getMethod(className, methodName, null);
	}

	private static String[] toList(String str) {
		//won't work correctly for all OSs
		String[] operations = str.split("[" + "\n" + "]");

		for (int i = 0; i < operations.length; ++i) {
			operations[i] = operations[i].trim();
		}

		return operations;
	}

	private static class MethodSelectorVisitor extends ClassVisitor {
		private final String methodName;
		private final String methodDescriptor;

		public MethodSelectorVisitor(ClassVisitor cv, String methodName, String methodDescriptor) {
			super(Opcodes.ASM4, cv);
			this.methodName = methodName;
			this.methodDescriptor = methodDescriptor;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc,
				String signature, String[] exceptions) {

			if (methodName.equals(name)) {
				if (methodDescriptor == null)
					return new MaxVisitFilterMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions));

				if (methodDescriptor.equals(desc))
					return new MaxVisitFilterMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions));
			}

			return null;
		}
	}

	private static class MaxVisitFilterMethodVisitor extends MethodVisitor {
		public MaxVisitFilterMethodVisitor(MethodVisitor mv) {
			super(Opcodes.ASM4, mv);
		}

		@Override
		public void visitMaxs(int maxStack, int maxLocals) {
		}
	}


	private static class SourceCodeTextifier extends Printer {
		public SourceCodeTextifier() {
			this(Opcodes.ASM4);
		}

		protected SourceCodeTextifier(final int api) {
			super(api);
		}

		@Override
		public void visit(
				final int version,
				final int access,
				final String name,
				final String signature,
				final String superName,
				final String[] interfaces)
		{
		}

		@Override
		public Textifier visitMethod(
				final int access,
				final String name,
				final String desc,
				final String signature,
				final String[] exceptions)
		{
			Textifier t = new Textifier();
			text.add(t.getText());
			return t;
		}

		@Override
		public Textifier visitAnnotation(
				final String name,
				final String desc)
		{
			return new Textifier();
		}

		@Override
		public Textifier visitArray(
				final String name)
		{
			return new Textifier();
		}

		@Override
		public Textifier visitFieldAnnotation(
				final String desc,
				final boolean visible)
		{
			return new Textifier();
		}

		@Override
		public Textifier visitAnnotationDefault() {
			return new Textifier();
		}

		@Override
		public Textifier visitMethodAnnotation(
				final String desc,
				final boolean visible)
		{
			return new Textifier();
		}

		@Override
		public Textifier visitParameterAnnotation(
				final int parameter,
				final String desc,
				final boolean visible)
		{
			return new Textifier();
		}

		@Override
		public void visitSource(String file, String debug) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitOuterClass(String owner, String name, String desc) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Printer visitClassAnnotation(String desc, boolean visible) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void visitClassAttribute(Attribute attr) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitInnerClass(String name, String outerName,
				String innerName, int access) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Printer visitField(int access, String name, String desc,
				String signature, Object value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void visitClassEnd() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(String name, Object value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitEnum(String name, String desc, String value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitAnnotationEnd() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitFieldAttribute(Attribute attr) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitFieldEnd() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitMethodAttribute(Attribute attr) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitCode() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitFrame(int type, int nLocal, Object[] local,
				int nStack, Object[] stack) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitInsn(int opcode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitIntInsn(int opcode, int operand) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitVarInsn(int opcode, int var) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitTypeInsn(int opcode, String type) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name,
				String desc) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,
				String desc) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitInvokeDynamicInsn(String name, String desc,
				Handle bsm, Object... bsmArgs) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitJumpInsn(int opcode, Label label) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitLabel(Label label) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitLdcInsn(Object cst) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitIincInsn(int var, int increment) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitTableSwitchInsn(int min, int max, Label dflt,
				Label... labels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitMultiANewArrayInsn(String desc, int dims) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitTryCatchBlock(Label start, Label end, Label handler,
				String type) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitLocalVariable(String name, String desc,
				String signature, Label start, Label end, int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitLineNumber(int line, Label start) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitMaxs(int maxStack, int maxLocals) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitMethodEnd() {
			// TODO Auto-generated method stub
			
		}

	}
}
