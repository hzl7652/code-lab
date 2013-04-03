package lulu.code_lab.j2se.trap;

import java.util.Arrays;

/**
 * 不要指望==
 * 不要指望常量池，尽量不用intern()
 * 不要往常量池放过多东西，会导致持久代OOM
 */
public class StringTrap {

	public static void main(String[] args) {
		System.out.println("A" + "B"); //AB
		System.out.println('A' + 'b');//163 96+97

		StringBuilder sb1 = new StringBuilder().append('A').append('B'); //AB
		StringBuilder sb2 = new StringBuilder('A').append('B');//B 没char类型的构造参数 char自动转成int型，调用StringBuilder(int capacity) 
		StringBuilder sb3 = new StringBuilder("A").append("B");

		System.out.println(sb1);
		System.out.println(sb2 + " " + sb2.capacity());
		System.out.println(sb3);

		String s = "who";
		//String s = "w";
		//String ho = "ho";
		//s += ho;
		System.out.println("who" == s);
		System.out.println("who" == "who");
		System.out.println("who" == new String("who"));
		System.out.println("who" == new String("who").intern());

		System.out.print("i love ");
		http: //www.taobao.com
		System.out.println("taobao!");

		System.out.println(Arrays.toString("张3".getBytes()));
	}
}
