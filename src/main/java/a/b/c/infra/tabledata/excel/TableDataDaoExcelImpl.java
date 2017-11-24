package a.b.c.infra.tabledata.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import a.b.c.infra.tabledata.TableData;
import a.b.c.infra.tabledata.TableDataCatalog;
import a.b.c.infra.tabledata.TableDataDao;

public class TableDataDaoExcelImpl implements TableDataDao {

	private ExcelReader reader = new ExcelReader();

	@Override
	public TableData read(String path, String name) {
		TableDataCatalog tableDataCatalog = readAll(path);
		return tableDataCatalog.get(name);
	}

	private TableDataCatalog readAll(String path) {
        TableDataCatalog catalog = new TableDataCatalog();
        Workbook workbook = reader.load(path);

        final int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            TableData tableData = reader.readSheet(sheet);
            if (tableData == null) {
                continue;
            }
            catalog.add(tableData);
        }

        return catalog;

	}


}
