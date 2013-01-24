package lulu.code_lab.framework.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class PoiTest {

	@Test
	public void testReadCell(){
		try {
			//3.8可以从WorkbookFactory创建
			Workbook wb = new HSSFWorkbook(PoiTest.class.getClassLoader().getResourceAsStream("jhhyqk.xls"));
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);
			Cell cell = row.getCell(0);
			System.out.println(cell.getCellType() + "-" + cell.getStringCellValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 遍历整个表格
	 */
	@Test
	public void testReadSheet(){
		try {
			//3.8可以从WorkbookFactory创建
			Workbook wb = new HSSFWorkbook(PoiTest.class.getClassLoader().getResourceAsStream("jhhyqk.xls"));
			Sheet sheet = wb.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			for(int i = 0; i <= rowCount; i++){
				Row row = sheet.getRow(i);
				short cellCount = row.getLastCellNum();
				for(short cellIndex = 0; cellIndex < cellCount; cellIndex++){
					Cell cell = row.getCell(cellIndex);
					System.out.print(getCellValue(cell) + "--");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private String getCellValue(Cell cell){
		String result;
		switch(cell.getCellType()){
			case Cell.CELL_TYPE_BLANK:
				result = "";
			break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = String.valueOf(cell.getBooleanCellValue());
			break;
			case Cell.CELL_TYPE_ERROR:
				result = String.valueOf(cell.getErrorCellValue());
			break;
			case Cell.CELL_TYPE_FORMULA:
				result = String.valueOf(cell.getCachedFormulaResultType());
			break;
			case Cell.CELL_TYPE_NUMERIC:
				result = String.valueOf(cell.getNumericCellValue());
			break;
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
			break;
			default:
				result = "";
			break;
				
		}
		return result;
	}
	
	@Test
	public void testWrite(){
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("c:/test.xls"));
			Sheet sheet = wb.createSheet();
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("标题1");
			cell = row.createCell(1);
			cell.setCellValue("标题2");
			
			wb.write(fos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
