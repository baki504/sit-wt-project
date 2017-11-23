package a.b.c.infra.tabledata.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import a.b.c.infra.UnExpectedException;
import a.b.c.infra.tabledata.RowData;
import a.b.c.infra.tabledata.TableData;

public class ExcelReader {

	private static final String CASE_NAME_PREFIX = "ケース_";

	public Workbook load(String path) {
		try (InputStream fis = new FileInputStream(path)) {
			return new XSSFWorkbook(fis);
		} catch (IOException e) {
			throw new UnExpectedException(e);
		}

	}

	public TableData readSheet(Sheet sheet) {
		Row headerRow = sheet.getRow(0);

		if (headerRow == null) {
			return null;
		}

		TableData tableData = new TableData();
		Map<String, Integer> caseHeader = findCaseHeader(headerRow);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				break;
			}

			readRow(caseHeader, row, tableData);
		}

		return tableData.isEmpty() ? null : tableData;

	}

	private Map<String, Integer> findCaseHeader(Row headerRow) {
		Map<String, Integer> caseHeader = new LinkedHashMap<>();

		final int lastCellNum = headerRow.getLastCellNum();
		for (int i = 0; i < lastCellNum; i++) {
			String headerCellValue = headerRow.getCell(i).getStringCellValue();

			if (headerCellValue.startsWith(CASE_NAME_PREFIX)) {
				caseHeader.put(headerCellValue, i);
			}
		}

		return caseHeader;

	}

	private void readRow(Map<String, Integer> caseHeader, Row row, TableData tableData) {
		RowData rowData = new RowData();

		for (Entry<String, Integer> entry : caseHeader.entrySet()) {
			String caseName = entry.getKey().substring(CASE_NAME_PREFIX.length());
			Cell cell = row.getCell(entry.getValue());
			rowData.setCellValue(caseName, cell.getStringCellValue());
		}

		tableData.add(rowData);

	}

}
