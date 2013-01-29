package lulu.code_lab.j2se.aop;

import java.util.Iterator;
import java.util.List;

/**
 * 拦截器链,持有被调用方法的信息
 * 
 * 
 */
public class InterceptorChain {

	private final List<MethodInterceptor> interceptors;

	private final Iterator<MethodInterceptor> interceptorIt;

	public InterceptorChain(List<MethodInterceptor> interceptors) {
		this.interceptors = interceptors;
		this.interceptorIt = interceptors.iterator();
	}

	/**
	 * 继续执行下一个拦截器,如果已经没有剩下的拦截器,则执行原方法
	 * 
	 * @return 拦截器链本身
	 * @throws Throwable
	 *             下层拦截器或原方法抛出的一切异常
	 */

	public void doChain() throws Throwable {

		if (interceptorIt.hasNext()) {
			MethodInterceptor interceptor = interceptorIt.next();
			interceptor.filter(this);
		} else {
			invoke();
		}
	}

	/**
	 * 执行原方法
	 */
	public void invoke() {
		System.out.println("调用原对象方法");
	}

}
