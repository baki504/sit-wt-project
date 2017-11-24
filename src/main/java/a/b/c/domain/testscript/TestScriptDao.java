package a.b.c.domain.testscript;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import a.b.c.infra.tabledata.RowData;
import a.b.c.infra.tabledata.TableData;
import a.b.c.infra.tabledata.TableDataDao;
import a.b.c.infra.tabledata.excel.TableDataDaoExcelImpl;

public class TestScriptDao {

	private static final String TESTSCRIPT_SUFFIX = ".xlsx";

	private static final String CASE_NAME_PREFIX = "ケース_";

	private TableDataDao tableDataDao = new TableDataDaoExcelImpl();

	public TestScript load(File scriptFile) {
		TestScript testScript = new TestScript();
		testScript.setFile(scriptFile);
		testScript.setFilePath(scriptFile.getPath().replaceAll("\\\\", "/"));

		if (scriptFile.getName().endsWith(TESTSCRIPT_SUFFIX)) {
			loadScript(testScript);
		} else {
			return null;
		}

		return testScript;

	}

	private void loadScript(TestScript testScript) {
		TableData tableData = tableDataDao.load(testScript.getFile().getAbsolutePath());

		List<TestCase> testCases = readTestCase(tableData);

		testScript.setTestCases(testCases);

	}

	private List<TestCase> readTestCase(TableData tableData) {

		if (tableData.isEmpty()) {
			return Collections.emptyList();
		}

		List<TestCase> testCases = new ArrayList<>();
		RowData headerRow = tableData.getRows().get(0);

		for (String caseName : findCaseName(headerRow)) {
			testCases.add(readCase(tableData, caseName));
		}

		return testCases;
	}

	private List<String> findCaseName(RowData headerRow) {
		List<String> caseNames = new ArrayList<>();
		for (Entry<String, String> row : headerRow.getData().entrySet()) {
			if (row.getKey().startsWith(CASE_NAME_PREFIX)) {
				caseNames.add(row.getKey());
			}
		}

		return caseNames;
	}

	private TestCase readCase(TableData tableData, String caseName) {
		String name = caseName.substring(CASE_NAME_PREFIX.length());
		int stepCount = 0;

		for (RowData rowData : tableData.getRows()) {
			String cellValue = rowData.getData().get(caseName);
			stepCount = StringUtils.isEmpty(cellValue) ? stepCount : stepCount + 1;
		}

		return new TestCase(name, stepCount);
	}

}
