package a.b.c.domain.testscript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import a.b.c.infra.tabledata.RowData;
import a.b.c.infra.tabledata.TableData;
import a.b.c.infra.tabledata.TableDataDao;
import a.b.c.infra.tabledata.excel.TableDataDaoExcelImpl;

public class TestScriptDao {

	private static final String TESTSCRIPT_SHEET_NAME = "TestScript";

	private static final String TESTSCRIPT_SUFFIX = ".xlsx";

	private TableDataDao excelDao = new TableDataDaoExcelImpl();

	public TestScript load(File file) {
		TestScript testScript = new TestScript();
		testScript.setFilePath(file.getPath().replaceAll("\\\\", "/"));

		if (file.getName().endsWith(TESTSCRIPT_SUFFIX)) {
			loadScript(excelDao, testScript);
		} else {
			return null;

		}

		return testScript;

	}

	private void loadScript(TableDataDao dao, TestScript testScript) {
		TableData tableData = dao.read(testScript.getFilePath(), TESTSCRIPT_SHEET_NAME);

		List<String> caseHeaders = new ArrayList<>();

		RowData firstRow = tableData.getRows().iterator().next();
		for (Entry<String, String> row : firstRow.getData().entrySet()) {
			if (row.getKey().startsWith(testScript.getCaseNamePrefix())) {
				caseHeaders.add(row.getKey());
			}
		}

		List<TestCase> testCases = new ArrayList<>();

		for (String caseHeader : caseHeaders) {
			testCases.add(loadTestCase(caseHeader, tableData, testScript.getCaseNamePrefix()));
		}

		testScript.setTestCases(testCases);

	}

	private TestCase loadTestCase(String caseHeader, TableData tableData, String caseNamePrefix) {
		TestCase testCase = new TestCase();

		int stepCount = 0;
		for (RowData rowData : tableData.getRows()) {
			for (Entry<String, String> entry : rowData.getData().entrySet()) {
				if (entry.getKey().equals(caseHeader) && StringUtils.isNotEmpty(entry.getValue())) {
					stepCount++;
				}
			}
		}
		
		testCase.setName(StringUtils.substringAfter(caseHeader, caseNamePrefix));
		testCase.setStepCount(stepCount);

		return testCase;

	}

}
