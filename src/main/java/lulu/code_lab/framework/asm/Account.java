package lulu.code_lab.framework.asm;

public class Account {

	private int foo;

	public int operation(int i) {
		//SecurityChecker.checkSecurity(i);
		System.out.println("operation...");
		return 0;
	}

	public int getFoo() {
		return foo;
	}

	public void setFoo(int foo) {
		this.foo = foo;
	}

	static class SecurityChecker {
		public static void checkSecurity(int i) {
			System.out.println("SecurityChecker.checkSecurity ...");
		}
	}

}