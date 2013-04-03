package lulu.code_lab.j2se.trap;

public class Null {

	public static void greet() {
		System.out.println("Hello World");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Null x = null;
		x.greet();//正常输出
		(x).greet();//正常输出
		((Null) null).greet();//正常输出
	}

}
