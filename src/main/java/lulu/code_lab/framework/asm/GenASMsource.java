package lulu.code_lab.framework.asm;

import java.io.IOException;
import java.io.PrintWriter;

import org.nutz.lang.Lang;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * 从class文件生成asm源码
 */
public class GenASMsource {

	public static void main(String[] args) throws Exception {
		//		Class c = Account.class;
		//		printClassASMsource(c);

		printClassASMsource(AccountChild.class);
	}

	public static void printClassASMsource(byte[] b) {
		ClassReader cr;
		cr = new ClassReader(b);
		cr.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), ClassReader.SKIP_DEBUG);

	}

	@SuppressWarnings("rawtypes")
	public static void printClassASMsource(Class xClass) {
		ClassReader cr;
		try {
			cr = new ClassReader(xClass.getName());
			cr.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), ClassReader.SKIP_DEBUG);
		} catch (IOException e) {
			throw Lang.wrapThrow(e);
		}

	}
}
