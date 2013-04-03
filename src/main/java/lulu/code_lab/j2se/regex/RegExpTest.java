package lulu.code_lab.j2se.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegExpTest {

	private final String REGEX = "//d";

	@Test
	public void hello() {
		System.out.println("123".matches("\\d+"));
		System.out.println("1f32e".matches("\\d[a-z]\\d{2}[a-z]"));
		System.out.println("1f32e".matches("\\w*"));
		System.out.println("a".matches("[abcd]"));
		System.out.println("aaaa".matches("a*"));
		System.out.println("abcd".matches("a.*"));
		System.out.println("".matches("a*"));
		System.out.println("".matches("a+"));
		System.out.println("a".matches("a?"));
		System.out.println("aa".matches("a?"));
		System.out.println("".matches("a?"));
		System.out.println("13223".matches("\\d+"));
		System.out.println("0870-12345678-12".matches("0\\d{2,3}-\\d{8}-\\d{2,5}"));
		System.out.println("0870-12345678".matches("0\\d{2,3}-\\d{8}(-\\d{2,5})?"));
		System.out.println("36".matches("\\d|[12]\\d|3[0-5]"));
		//^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.
		System.out
				.println("255.55.55.0"
						.matches("(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]\\d|\\d)\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]\\d|\\d)\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]\\d|\\d)\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]\\d|\\d)"));
		System.out
				.println("219.55.55.0"
						.matches("(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|\\d\\d|\\d)\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|\\d\\d|\\d)\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|\\d\\d|\\d)\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|\\d\\d|\\d)"));

	}

	@Test
	public void boundary_matchers() {
		System.out.println("helloworld".matches("^h.*"));
		System.out.println("helloworld".matches("^h.*d$"));

	}

	@Test
	public void testPattern() {
		//����,�������Ч��
		Pattern p = Pattern.compile("^h.*d$");
		Matcher m = p.matcher("helloworld");
		System.out.println(m.matches());
		System.out.println();
	}

	/**
	 * ��ʾ������ʽ�����ʹ�ã��������Ŵ���߿�ʼ��ÿһ�������ž���һ�����飬��һ��������Ϊ1.
	 */
	@Test
	public void testGroup1() {
		//����,�������Ч��
		String str = "452601198510053630,452601198510053632,452601198510053631,";
		Pattern p = Pattern.compile("((\\d{6})(\\d{8}))\\d{4}");
		Matcher m = p.matcher(str);
		while (m.find()) {
			System.out.println(m.group());
			System.out.println("�����:" + m.group(1) + ":����:" + m.group(2));
		}
	}

	/**
	 * ��ȡtd�е�����.
	 */
	@Test
	public void testGroup2() {
		//����,�������Ч��
		String str = "<table>" + "<tr>" + "<td>1</td>" + "<td>papa</td>" + "<td>4</td>" + "</tr>" + "</table>";
		//̰��ģʽ
		Pattern p = Pattern.compile("<td>(.*)</td>");
		Matcher m = p.matcher(str);
		while (m.find()) {
			System.out.println(m.group());
			System.out.println("����:" + m.group(1));
		}
		//̰��ģʽ
		Pattern p1 = Pattern.compile("<td>(.*?)</td>");
		Matcher m1 = p1.matcher(str);
		while (m1.find()) {
			System.out.println(m1.group());
			System.out.println("����:" + m1.group(1));
		}
	}

	@Test
	public void test1() {
		System.out.println("13702004234".replaceAll("\\d{4}$", "****"));
		System.out.println("aaa".replace("aa", "b"));
	}
}
