package lulu.code_lab.framework.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelTemplate {

	public static final String DATA_LINE = "datas";
	public static final String DEFAULT_STYLE = "defaultStyle";
	public static final String CELL_STYLE = "styles";
	public static final String SER_NUM = "ser_num";
	private Workbook wb;
	private Sheet sheet;
	private Row curRow;
	/**默认样式*/
	private CellStyle defaultCellStyle;
	/**默认行高*/
	private float defaultRowHeight;
	private int initRowIndex;
	private int initCellIndex;
	private int curRowIndex;
	private int curCellIndex;
	private int lastRowIndex;
	private final Map<Integer, CellStyle> cellStyles = new HashMap<Integer, CellStyle>();
	private int serNumCellIndex;

	private ExcelTemplate() {

	}

	public static ExcelTemplate newInstance() {
		return new ExcelTemplate();
	}

	//读取相应的模板文档
	public void readTemplateByClasspath(String path) {
		try {
			wb = new HSSFWorkbook(ExcelTemplate.class.getClassLoader().getResourceAsStream(path));
			initTemplate();
		} catch (Exception e) {
			throw new RuntimeException("读取excel模板错误", e);
		}
	}

	public void initTemplate() {
		sheet = wb.getSheetAt(0);
		lastRowIndex = sheet.getLastRowNum();
		initConfigData();
		curRow = sheet.createRow(curRowIndex);
		curRow.setHeightInPoints(defaultRowHeight);
	}

	private void initConfigData() {
		boolean isFound = false;
		for (Row row : sheet) {
			if (isFound) {
				break;
			}
			for (Cell cell : row) {
				if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
					continue;
				}
				initRowCellIndex(row, cell);
				initDefaultStyle(row, cell);
				initCellStyles(cell);
			}
		}
	}

	private void initCellStyles(Cell cell) {
		String str = cell.getStringCellValue().trim();
		if (CELL_STYLE.equals(str)) {
			cellStyles.put(cell.getColumnIndex(), cell.getCellStyle());
		}
	}

	private void initDefaultStyle(Row row, Cell cell) {
		String str = cell.getStringCellValue().trim();
		if (DEFAULT_STYLE.equals(str)) {
			defaultCellStyle = cell.getCellStyle();
			defaultRowHeight = row.getHeightInPoints();
		}

	}

	private void initRowCellIndex(Row row, Cell cell) {
		String str = cell.getStringCellValue().trim();
		if (DATA_LINE.equals(str)) {
			initRowIndex = row.getRowNum();
			initCellIndex = cell.getColumnIndex();
			curRowIndex = initRowIndex;
			curCellIndex = initCellIndex;
		}
		if (SER_NUM.equals(str)) {
			serNumCellIndex = cell.getColumnIndex();
		}
	}

	public Row createNewRow() {
		curRowIndex++;
		if (lastRowIndex >= curRowIndex && curRowIndex != initRowIndex) {
			sheet.shiftRows(curRowIndex, lastRowIndex, 1);
			lastRowIndex++;
		}
		curRow = sheet.createRow(curRowIndex);
		curRow.setHeightInPoints(defaultRowHeight);
		curCellIndex = initCellIndex;
		return curRow;
	}

	public void createCell(String value) {
		Cell cell = curRow.createCell(curCellIndex);
		CellStyle cs = cellStyles.get(curCellIndex);
		if (null == cs) {
			cell.setCellStyle(defaultCellStyle);
		} else {
			cell.setCellStyle(cs);
		}
		cell.setCellValue(value);
		curCellIndex++;
	}

	public void repalceTitle(Map<String, String> titleMap) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
					continue;
				}
				String str = cell.getStringCellValue().trim();
				if (str.length() > 0) {
					String title = str.substring(1);
					if (titleMap.containsKey(title)) {
						cell.setCellValue(titleMap.get(title));
					}
				}
			}
		}
	}

	public void insertSerialNumber() {
		int index = 1;
		for (int dataRowIndex = initRowIndex; dataRowIndex < lastRowIndex; dataRowIndex++) {
			Cell cell = sheet.getRow(dataRowIndex).createCell(serNumCellIndex);
			cell.setCellValue(index++);

		}
	}

	public void writeTo(OutputStream out) {
		try {
			wb.write(out);
		} catch (IOException e) {
			throw new RuntimeException("写入时失败", e);
		}
	}

}
