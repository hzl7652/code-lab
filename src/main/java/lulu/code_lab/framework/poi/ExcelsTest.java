package lulu.code_lab.framework.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ExcelsTest {

	private static List<Pet> pets = new ArrayList<Pet>();
	private static Map<String, String> titleMap = new HashMap<String, String>();
	static {
		pets = new ArrayList<Pet>();
		pets.add(new Pet("papa", 4, "男"));
		pets.add(new Pet("papa1", 3, "男"));
		pets.add(new Pet("papa2", 1, "男"));

		titleMap.put("title", "宠物列表");
		titleMap.put("date", "2012-11-10");

	}

	@Test
	public void testExportExcel() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("C:/export-excel/export.xls"));
			Excels.exportObj2ExcelTemplate("template.xls", Pet.class, pets, titleMap, false, true, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExportExcelNoTitle() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("C:/export-excel/export-no-title.xls"));
			Excels.exportObj2ExcelTemplate("template-no-title.xls", Pet.class, pets, titleMap, true, true, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExportExcelNoTemplate() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("C:/export-excel/export-no-template.xls"));
			Excels.exportObj2Excel(Pet.class, pets, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadExcel2Objs() throws URISyntaxException {

		URL fileUrl = ExcelsTest.class.getClassLoader().getResource("pets.xls");
		File file = new File(fileUrl.toURI());
		System.out.println(Excels.readExcel2Objs(file, Pet.class, 1, 2));
	}

}
