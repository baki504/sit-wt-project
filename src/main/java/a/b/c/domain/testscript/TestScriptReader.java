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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestScriptReader {

	private static final String TESTSCRIPT_SUFFIX = ".xlsx";

	private static final String TESTSCRIPT_SHEET_NAME = "TestScript";

	private static final String CASE_NAME_PREFIX = "ケース_";

	private static final int CASE_NAME_START_INDEX = CASE_NAME_PREFIX.length();

	public List<TestScript> readRecursively(Path targetDir) {

		List<TestScript> testScripts = new ArrayList<>();

		File testScriptDir = targetDir.toFile();

		if (!testScriptDir.exists()) {
			return testScripts;
		}

		read(testScriptDir, testScripts);

		return testScripts;
	}

	private void read(File targetFile, List<TestScript> testScripts) {

		for (File file : targetFile.listFiles()) {
			if (file.isDirectory()) {
				read(file, testScripts);
			} else if (file.getName().endsWith(TESTSCRIPT_SUFFIX)) {
				testScripts.add(readTestScript(file));
			}
		}

	}

	private TestScript readTestScript(File file) {

		String filePath = file.getPath().replace("\\", "/");
		List<TestCase> testCases = readTestCase(file);

		return new TestScript(filePath, testCases);
	}

	private List<TestCase> readTestCase(File file) {

		List<TestCase> testCases = new ArrayList<>();

		try {
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheet(TESTSCRIPT_SHEET_NAME);

			Row headerRow = sheet.getRow(0);

			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				String headerCellValue = headerRow.getCell(i).getStringCellValue();

				if (headerCellValue.startsWith(CASE_NAME_PREFIX)) {
					testCases.add(readCaseColumn(sheet, i));
				}
			}

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		return testCases;
	}

	private TestCase readCaseColumn(Sheet sheet, int caseIndex) {

		String name = extractName(sheet.getRow(0).getCell(caseIndex));
		int stepCount = countStep(sheet, caseIndex);

		return new TestCase(name, stepCount);
	}


	private String extractName(Cell caseHeader) {
		return caseHeader.getStringCellValue().substring(CASE_NAME_START_INDEX);
	}

	private int countStep(Sheet sheet, int caseIndex) {

		int stepCount = 0;

		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			String stepValue = sheet.getRow(i).getCell(caseIndex).getStringCellValue();

			if (stepValue.length() > 0) {
				stepCount++;
			}
		}

		return stepCount;
	}
}
