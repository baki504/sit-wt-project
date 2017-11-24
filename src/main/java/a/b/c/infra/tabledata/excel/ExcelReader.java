package a.b.c.infra.tabledata.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import a.b.c.infra.TableDataLoadingException;
import a.b.c.infra.tabledata.RowData;
import a.b.c.infra.tabledata.TableData;

public class ExcelReader {

	public Workbook load(String path) {
		try (InputStream fis = new FileInputStream(path)) {
			return new XSSFWorkbook(fis);
		} catch (IOException e) {
			throw new TableDataLoadingException(e);
		}

	}

	public TableData readSheet(Sheet sheet) {
		TableData tableData = new TableData();

		Row headerRow = sheet.getRow(0);
		if (headerRow == null) {
			return null;
		}

		Map<String, Integer> header = ExcelIOUtils.readHeader(headerRow);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				break;
			}

			readRow(header, row, tableData);
		}

		return tableData.isEmpty() ? null : tableData;

	}

	private void readRow(Map<String, Integer> header, Row row, TableData tableData) {
		RowData rowData = new RowData();

		for (Entry<String, Integer> entry : header.entrySet()) {
			Cell cell = row.getCell(entry.getValue());
			rowData.setCellValue(entry.getKey(), ExcelIOUtils.retriveCellValue(cell));
		}

		tableData.add(rowData);

	}

}
