package a.b.c.domain.testscript;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import a.b.c.infra.tabledata.TableData;
import a.b.c.infra.tabledata.TableDataDao;
import a.b.c.infra.tabledata.excel.TableDataDaoExcelImpl;

public class TestScriptDao {

	private static final String TESTSCRIPT_SUFFIX = ".xlsx";

	private static final String TESTSCRIPT_SHEET_NAME = "TestScript";

	private static final String CASE_NAME_PREFIX = "ケース_";

	private TableDataDao tableDataDao = new TableDataDaoExcelImpl();

	public TestScript read(File scriptFile) {

		if (!StringUtils.endsWith(scriptFile.getName(), TESTSCRIPT_SUFFIX)) {
			return null;
		}

		TableData testScriptSheetData = tableDataDao.read(scriptFile.getAbsolutePath(),
				TESTSCRIPT_SHEET_NAME);

		List<TestCase> testCases = readTestCases(testScriptSheetData);

		return new TestScript(scriptFile, testCases);
	}

	private List<TestCase> readTestCases(TableData tableData) {
		return tableData.getHeaderRow().getColumnNames().stream().map(columnName -> {

			String caseName = StringUtils.substringAfter(columnName, CASE_NAME_PREFIX);

			if (StringUtils.isEmpty(caseName)) {
				return null;
			}

			int stepCount = tableData.getRows().stream()
					.mapToInt(row -> StringUtils.isEmpty(row.getCellValue(columnName)) ? 0 : 1)
					.sum();

			return new TestCase(caseName, stepCount);

		}).filter(testCase -> testCase != null).collect(Collectors.toList());

	}

}
