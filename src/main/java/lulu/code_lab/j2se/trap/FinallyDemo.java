package lulu.code_lab.j2se.trap;

/**
 * 原则：finally里面不允许有return/break/continue/throw等改变正常退出的逻辑
 */
public class FinallyDemo {

	static int value = 0;

	static int inc() {
		return value++;
	}

	static int dec() {
		return value--;
	}

	static int getResult() {
		try {
			return inc();
		} finally {
			return dec();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getResult());
		System.out.println(value);

	}

}
