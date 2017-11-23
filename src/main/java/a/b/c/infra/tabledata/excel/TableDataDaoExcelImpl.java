package a.b.c.infra.tabledata.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import a.b.c.infra.tabledata.TableData;
import a.b.c.infra.tabledata.TableDataDao;

public class TableDataDaoExcelImpl implements TableDataDao {

	private static final String TESTSCRIPT_SHEET_NAME = "TestScript";

	private ExcelReader reader = new ExcelReader();

	@Override
	public TableData load(String path) {
		Workbook workbook = reader.load(path);
		Sheet sheet = workbook.getSheet(TESTSCRIPT_SHEET_NAME);

		return reader.readSheet(sheet);
	}

}
