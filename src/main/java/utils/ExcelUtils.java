package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static XSSFWorkbook workbook;

	static {

		try {
			FileInputStream fis = new FileInputStream("src/test/resources/testData/TestData.xlsx");
			workbook = new XSSFWorkbook(fis);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getRowCount(String sheetName) {

		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getLastRowNum();
	}

	public static int getColumnCount(String sheetName) {

		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getRow(0).getLastCellNum();
	}

	public static String getCellData(String sheetName, int rowNumber, int cellNumber) {

		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.getCell(cellNumber);
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);

	}

}
