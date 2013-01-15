package lulu.code_lab.j2se.lang;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;

public class SystemTest {

	@Test
	public void testGetProperty() {
		System.setProperty("userProperty1", "userProperty1");
		assertEquals("userProperty1", System.getProperty("userProperty1"));
		//set -DuserProperty2=userProperty2
		assertEquals("userProperty2", System.getProperty("userProperty2"));
	}

	@Test
	public void testGetProperties() {
		Properties pro = System.getProperties();
		for (Entry<Object, Object> entry : pro.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}

	@Test
	public void testGetenvMap() {
		Map<String, String> envMap = System.getenv();
		for (Map.Entry<String, String> entry : envMap.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}

}
