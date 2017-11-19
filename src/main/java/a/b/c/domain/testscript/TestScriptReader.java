package a.b.c.domain.testscript;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestScriptReader {

	private static final String TESTSCRIPT_SUFFIX = ".xlsx";

	private String CASE_NAME_PREFIX = "ケース_";

	private int CASE_NAME_START_INDEX = CASE_NAME_PREFIX.length();

	public List<TestScript> readRecursively(Path targetDir) {

		List<TestScript> testScripts = new ArrayList<>();

		File testScriptDir = new File(targetDir.toString());

		if (!testScriptDir.exists()) {
			return null;
		}

		findScript(testScriptDir, testScripts);

		return testScripts;
	}

	private void findScript(File file, List<TestScript> testScripts) {

		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				findScript(f, testScripts);
			} else if (f.getName().endsWith(TESTSCRIPT_SUFFIX)) {
				testScripts.add(new TestScript(f.getPath().replace("\\", "/"), readTestCase(f)));
			}
		}
	}

	private List<TestCase> readTestCase(File f) {

		List<TestCase> testCases = new ArrayList<>();

		try {
			Workbook workbook = WorkbookFactory.create(f);
			Sheet sheet = workbook.getSheet("TestScript");

			Row scriptHeaderRow = sheet.getRow(0);
			List<Integer> caseIndexes = readCaseIndex(sheet, scriptHeaderRow);

			for (Integer i : caseIndexes) {
				int testStep = 0;
				int allStepCount = countStep(sheet);
				for (int j = 1; j < allStepCount; j++) {
					Cell cell = sheet.getRow(j).getCell(i, MissingCellPolicy.RETURN_BLANK_AS_NULL);
					testStep = cell == null ? testStep : testStep + 1;
				}
				testCases.add(new TestCase(
						scriptHeaderRow.getCell(i).getStringCellValue().substring(CASE_NAME_START_INDEX), testStep));
			}

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		return testCases;
	}

	private static int countStep(Sheet sheet) {
		int index = 1;
		while (true) {
			if (sheet.getRow(index) == null
					|| sheet.getRow(index).getCell(0, MissingCellPolicy.RETURN_BLANK_AS_NULL) == null) {
				break;
			}
			index++;
		}
		return index;
	}

	private List<Integer> readCaseIndex(Sheet sheet, Row scriptHeaderRow) {

		List<Integer> caseIndexes = new ArrayList<>();
		for (int i = 0; i < scriptHeaderRow.getLastCellNum(); i++) {
			if (scriptHeaderRow.getCell(i).getStringCellValue().startsWith(CASE_NAME_PREFIX)) {
				caseIndexes.add(i);
			}
		}
		return caseIndexes;
	}

}
