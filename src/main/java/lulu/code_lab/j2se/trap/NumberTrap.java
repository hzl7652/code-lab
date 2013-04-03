package lulu.code_lab.j2se.trap;

import java.util.ArrayList;
import java.util.List;

/**
 * 变量名称永远不要用l
 * 数字结尾永远不要用l，表示long使用L
 */
public class NumberTrap {

	public static void main(String[] args) {
		System.out.println(12345 + 5432l);

		List l = new ArrayList<String>();
		l.add("Foo");
		System.out.println(l);

		byte x = 10;

		//x+= 1234;//越界
		//x = (byte) (x+1234);//编译错误，需要类型转换
		//System.out.println(x);

		Object taobao = "taobao";
		String ali = "ali";
		taobao = taobao + ali;
		//taobao += ali;//编译错误，需要类型转换
		System.out.println(taobao);

	}
}
