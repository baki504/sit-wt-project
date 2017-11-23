package a.b.c.domain.testscript;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import a.b.c.infra.tabledata.RowData;
import a.b.c.infra.tabledata.TableData;
import a.b.c.infra.tabledata.excel.TableDataDaoExcelImpl;

public class TestScriptDao {

	private static final String TESTSCRIPT_SUFFIX = ".xlsx";

	private TableDataDaoExcelImpl tableDataDaoExcelImpl = new TableDataDaoExcelImpl();

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
		TableData tableData = tableDataDaoExcelImpl.load(testScript.getFile().getAbsolutePath());

		List<TestCase> testCases = readTestCase(tableData);

		testScript.setTestCases(testCases);

	}

	private List<TestCase> readTestCase(TableData tableData) {

		if (tableData == null) {
			return Collections.emptyList();
		}

		List<TestCase> testCases = new ArrayList<>();
		for (String caseName : tableData.getCaseNames()) {
			testCases.add(readCase(tableData, caseName));
		}

		return testCases;
	}

	private TestCase readCase(TableData tableData, String caseName) {
		int stepCount = 0;

		for (RowData rowData : tableData.getRows()) {
			String cellValue = rowData.getData().get(caseName);
			stepCount = StringUtils.isEmpty(cellValue) ? stepCount : stepCount + 1;
		}

		return new TestCase(caseName, stepCount);
	}

}
