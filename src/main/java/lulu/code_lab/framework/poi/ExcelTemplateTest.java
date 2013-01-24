package lulu.code_lab.framework.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ExcelTemplateTest {

	@Test
	public void test01() {
		ExcelTemplate et = ExcelTemplate.newInstance();
		et.readTemplateByClasspath("template.xls");
		System.out.println(et);
	}

	@Test
	public void test02() {
		ExcelTemplate et = ExcelTemplate.newInstance();
		et.readTemplateByClasspath("template.xls");
		et.createCell("papa");
		et.createCell("4");
		et.createCell("男");
		et.createNewRow();
		et.createCell("dudu");
		et.createCell("5");
		et.createCell("男");
		et.createNewRow();
		et.createCell("kitty");
		et.createCell("1");
		et.createCell("女");

		Map<String, String> titleMap = new HashMap<String, String>();
		titleMap.put("title", "宠物列表");
		titleMap.put("date", "2012-11-10");
		et.repalceTitle(titleMap);
		//et.insertSerialNumber();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("C:/export.xls"));
			et.writeTo(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
