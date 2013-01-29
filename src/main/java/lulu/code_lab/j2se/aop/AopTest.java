package lulu.code_lab.j2se.aop;

import org.junit.Test;
import org.nutz.lang.Lang;

public class AopTest {

	@Test
	public void test1() throws Throwable {
		MethodInterceptor m1 = new MethodInterceptor() {

			@Override
			public void filter(InterceptorChain chain) throws Throwable {
				System.out.println("m1 before");
				chain.doChain();
				System.out.println("m1 after");
			}
		};
		MethodInterceptor m2 = new MethodInterceptor() {

			@Override
			public void filter(InterceptorChain chain) throws Throwable {
				System.out.println("m2 before");
				chain.doChain();
				System.out.println("m2 after");
			}
		};
		MethodInterceptor m3 = new MethodInterceptor() {

			@Override
			public void filter(InterceptorChain chain) throws Throwable {
				System.out.println("m3 before");
				return;
			}
		};

		InterceptorChain chain = new InterceptorChain(Lang.list(m1, m2, m3));
		chain.doChain();
	}
}
