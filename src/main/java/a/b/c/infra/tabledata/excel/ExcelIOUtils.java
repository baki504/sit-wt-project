package a.b.c.infra.tabledata.excel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

class ExcelIOUtils {

	ExcelIOUtils() {
	}

	static Map<String, Integer> readHeader(Row headerRow) {

		Map<String, Integer> header = new LinkedHashMap<>();

		final int lastCellNum = headerRow.getLastCellNum();
		for (int i = 0; i < lastCellNum; i++) {
			String headerCellValue = retriveCellValue(headerRow.getCell(i));

			header.put(headerCellValue, i);
		}

		return header;

	}

	static String retriveCellValue(Cell cell) {
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			return Double.toString(cell.getNumericCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return cell.getStringCellValue();
		}

	}
}
