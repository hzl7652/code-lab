package lulu.code_lab.framework.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class WriteSimpleClass implements Opcodes {

	/**
	 * 演示生成如下的java class文件
	 * 	  class source	
			package pkg;
			public interface Comparable extends Serializable{
			int LESS=-1;
			int EQUAL=0;
			int GREATER=1;
			int compareTo(Object o);
			}
	*/
	@Test
	public void writeClass1() {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		cw.visit(V1_6, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "pkg/Comparable", null, "java/lang/Object",
				new String[] { "java/io/Serializable" });

		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, -1).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, 0).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, 1).visitEnd();

		cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();

		byte[] b = cw.toByteArray();
		MyClassLoader mcl = new MyClassLoader();
		Class c = mcl.defineClass("pkg.Comparable", b);
		System.out.printf("class name %s \n", c.getName());

		for (Field f : c.getFields()) {
			System.out.printf("field  %s  ", f.getName());
		}
	}

	/**
	 * 演示使用asm动态生成子类，添加方法
	 */
	@Test
	public void testGenSubClass() throws Exception {
		ClassReader cr = new ClassReader("lulu.code_lab.framework.asm.Account");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		GenSubClassVisitor gscv = new GenSubClassVisitor(cw);
		cr.accept(gscv, ClassReader.SKIP_DEBUG);

		//通过asm增加一个方法
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "asmAddMethod", "()V", null, null);
		mv.visitCode();
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn("this is method add by ASM");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(2, 1);
		mv.visitEnd();

		byte[] data = cw.toByteArray();
		MyClassLoader mcl = new MyClassLoader();
		Class c = mcl.defineClass("lulu.code_lab.framework.asm.Account$EnhancedByASM", data);

		GenASMsource.printClassASMsource(data);

		System.out.printf("class name %s \n", c.getName());

		Object account = c.newInstance();

		Method m = c.getMethod("asmAddMethod");

		m.invoke(account);

	}

	class GenSubClassVisitor extends ClassVisitor {

		String enhancedSuperName;

		public GenSubClassVisitor(ClassVisitor cv) {
			super(Opcodes.ASM4, cv);
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

			String enhancedName = name + "$EnhancedByASM"; // 改变类命名
			enhancedSuperName = name; // 改变父类，这里是”Account”

			super.visit(version, access, enhancedName, signature, enhancedSuperName, interfaces);
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String desc,
				final String signature, final String[] exceptions) {
			MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
			MethodVisitor wrappedMv = mv;
			if (mv != null) {
				if (name.equals("<init>")) {
					wrappedMv = new ChangeToChildConstructorMethodAdapter(mv, enhancedSuperName);
				}
			}
			return wrappedMv;
		}

		class ChangeToChildConstructorMethodAdapter extends MethodVisitor {
			private final String superClassName;

			public ChangeToChildConstructorMethodAdapter(MethodVisitor mv, String superClassName) {
				super(Opcodes.ASM4, mv);
				this.superClassName = superClassName;
			}

			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc) {
				// 调用父类的构造函数时
				if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
					owner = superClassName;
				}
				super.visitMethodInsn(opcode, owner, name, desc);// 改写父类为 superClassName 
			}
		}

	}

	class MyClassLoader extends ClassLoader {
		public Class defineClass(String name, byte[] b) {
			return defineClass(name, b, 0, b.length);
		}
	}
}
