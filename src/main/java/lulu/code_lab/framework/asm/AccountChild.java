package lulu.code_lab.framework.asm;

import java.util.List;

import lulu.code_lab.j2se.aop.InterceptorChain;
import lulu.code_lab.j2se.aop.MethodInterceptor;

import org.nutz.lang.Lang;

public class AccountChild extends Account {

	private static List<MethodInterceptor> interceptors;

	static {
		MethodInterceptor m1 = new MethodInterceptor() {

			@Override
			public void filter(InterceptorChain chain) throws Throwable {
				System.out.println("m1 before");
				chain.doChain();
				System.out.println("m1 after");
			}
		};
		interceptors = Lang.list(m1);
	}

	@Override
	public int operation(int i) {

		try {
			new InterceptorChain(interceptors).doChain();
			return 0;
		} catch (Throwable e) {
			throw Lang.wrapThrow(e);
		}

	}

}
