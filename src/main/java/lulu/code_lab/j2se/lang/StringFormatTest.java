package lulu.code_lab.j2se.lang;

import java.util.Date;

import org.junit.Test;

public class StringFormatTest {

	/**
	 * 1.对整数进行格式化：%[index$][标识][最小宽度]转换方式。
	我们可以看到，格式化字符串由4部分组成，其中%[index$]的含义我们上面已经讲过，[最小宽度]的含义也很好理解，就是最终该整数转化的字符串最少包含多少位数字。我们来看看剩下2个部分的含义吧：
	标  识：

	'-' 在最小宽度内左对齐，不可以与“用0填充”同时使用
	'#' 只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x
	'+' 结果总是包括一个符号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
	' ' 正值前加空格，负值前加负号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
	'0' 结果将用零来填充
	',' 只适用于10进制，每3位数字之间用“，”分隔
	'(' 若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（同‘+’具有同样的限制）

	转换方式：
	d-十进制 o-八进制 x或X-十六进制
	 */
	@Test
	public void formatNumber() {
		System.out.println(String.format("limit %d,%d", 10, 20));
		System.out.println(String.format("%1$,09d", -3123));
		System.out.println(String.format("%1$9d", -31));
		System.out.println(String.format("%1$-9d", -31));
		System.out.println(String.format("%1$(9d", -31));
		System.out.println(String.format("%1$#9x", 5689));
		//结果为：
		// limit 10,20
		//-0003,123  
		//		      -31  
		//-31        
		//		     (31)  
		//   0x1639  
	}

	/**
	 * 对浮点数进行格式化：%[index$][标识][最少宽度][.精度]转换方式
	 */
	@Test
	public void formatDecimal() {
		System.out.println(String.format("%1$,07.1f", 4123.234));
	}

	/**
	 * 格式化字符
	 */
	@Test
	public void formatStr() {
		System.out.println(String.format("char %c", 97));
		System.out.println(String.format("char %c", 'b'));
		System.out.println(String.format("str is %s", "asdf"));
	}

	@Test
	public void formatPerent() {
		System.out.println(String.format("%1$d%%", 12));
		System.out.println(String.format("%n sdfadsf"));
	}

	@Test
	public void formatDateTime() {
		System.out.println(String.format("date is : %1$tY-%1$tm-%1$td", System.currentTimeMillis()));
		System.out.println(String.format("date is : %1$tY-%1$tm-%1$td", new Date()));
		System.out.println(String.format("date is : %1$tY-%<tm-%<td", new Date()));
		System.out.println(String.format("time is : %1$tH:%<tM:%<tS", new Date()));
		System.out.println(String.format("time is : %1$tR", new Date()));
		System.out.println(String.format("time is : %1$tT", new Date()));
		System.out.println(String.format("time is : %1$tr", new Date()));
		System.out.println(String.format("time is : %1$tD", new Date()));
		System.out.println(String.format("time is : %1$tF", new Date()));
		System.out.println(String.format("time is : %tH", new Date()));
	}

}
