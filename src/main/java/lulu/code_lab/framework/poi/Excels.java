package lulu.code_lab.framework.poi;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.castor.Castors;

public class Excels {

	@SuppressWarnings("rawtypes")
	public static void exportObj2ExcelTemplate(String template, Class clazz, List objs, Map<String, String> titleMap,
			boolean outputTitleRow, boolean insertSerNum, OutputStream os) {
		List<ExcelHeader> ehs = getExcelHeaders(clazz);
		ExcelTemplate et = ExcelTemplate.newInstance();
		et.readTemplateByClasspath(template);
		try {
			if (outputTitleRow) {
				for (ExcelHeader eh : ehs) {
					et.createCell(eh.getTitle());
				}
				et.createNewRow();
			}
			for (int i = 0; i < objs.size(); i++) {
				Object obj = objs.get(i);
				for (ExcelHeader eh : ehs) {
					Object result = eh.getMethod().invoke(obj);
					et.createCell(result.toString());
				}
				if (i + 1 < objs.size()) {
					et.createNewRow();
				}
			}
			if (insertSerNum) {
				et.insertSerialNumber();
			}
			et.repalceTitle(titleMap);
			et.writeTo(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<ExcelHeader> getExcelHeaders(Class clazz) {
		List<ExcelHeader> ehs = new ArrayList<ExcelHeader>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Method[] methods = superClass.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get") && method.isAnnotationPresent(ExcelResource.class)) {
					ExcelResource er = method.getAnnotation(ExcelResource.class);
					ehs.add(new ExcelHeader(er, method));
				}
			}
		}
		Collections.sort(ehs);
		return ehs;
	}

	@SuppressWarnings("rawtypes")
	public static void exportObj2Excel(Class clazz, List objs, OutputStream output) {
		List<ExcelHeader> ehs = getExcelHeaders(clazz);
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row titleRow = sheet.createRow(0);
		for (int i = 0; i < ehs.size(); i++) {
			titleRow.createCell(i).setCellValue(ehs.get(i).getTitle());
		}
		try {
			int rowIndex = 1;
			for (Object obj : objs) {
				Row row = sheet.createRow(rowIndex);
				for (int i = 0; i < ehs.size(); i++) {
					Object result = ehs.get(i).getMethod().invoke(obj);
					row.createCell(i).setCellValue(result.toString());
				}
				rowIndex++;
			}

			wb.write(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> List<T> readExcel2Objs(File excelFile, Class<T> clazz, int titleLine, int tailLine) {
		Workbook wb = null;
		List<T> objs = new ArrayList<T>();
		try {
			wb = new HSSFWorkbook(new FileInputStream(excelFile));
			Sheet sheet = wb.getSheetAt(0);
			Map<Integer, Method> headMap = getHeadMap(sheet.getRow(titleLine), clazz);
			Castors castors = Castors.me();
			for (int rowIndex = titleLine + 1, rowCount = sheet.getLastRowNum(); rowIndex <= rowCount - tailLine; rowIndex++) {
				T obj = clazz.newInstance();
				Row row = sheet.getRow(rowIndex);
				for (Map.Entry<Integer, Method> entry : headMap.entrySet()) {
					int cellIndex = entry.getKey();
					Method setMethod = entry.getValue();
					Object cellValue = castors.castTo(row.getCell(cellIndex).getStringCellValue(),
							setMethod.getParameterTypes()[0]);
					setMethod.invoke(obj, cellValue);

				}
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objs;
	}

	private static Object getCellValue(Cell cell) {
		Object result;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			result = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_ERROR:
			result = cell.getErrorCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			result = cell.getCachedFormulaResultType();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			result = cell.getNumericCellValue();
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

	public static Map<Integer, Method> getHeadMap(Row row, Class clazz) throws IntrospectionException {
		List<ExcelHeader> ehs = getExcelHeaders(clazz);
		Map<Integer, Method> headMap = new HashMap<Integer, Method>();
		for (int i = 0, cellCount = row.getLastCellNum(); i < cellCount; i++) {
			Cell cell = row.getCell(i);
			String title = cell.getStringCellValue().trim();
			for (ExcelHeader eh : ehs) {
				if (title.equals(eh.getTitle())) {
					String fieldName = eh.getMethod().getName().replaceFirst("get|is", "").toLowerCase();
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
					Method setMethod = pd.getWriteMethod();
					if (setMethod != null) {
						headMap.put(i, setMethod);
					}
				}
			}
		}
		return headMap;
	}

	static class ExcelHeader implements Comparable<ExcelHeader> {

		private int order;
		private String title;
		private Method method;

		public ExcelHeader(ExcelResource er, Method method) {
			this.order = er.order();
			this.title = er.title();
			this.method = method;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		@Override
		public int compareTo(ExcelHeader o) {
			return this.order < o.getOrder() ? -1 : (this.order == o.getOrder() ? 0 : 1);
		}

	}
}
