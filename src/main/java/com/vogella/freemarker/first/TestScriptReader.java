package com.vogella.freemarker.first;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestScriptReader {

	public List<TestScript> readRecursively(Path targetDir) {

		List<TestScript> testScripts = new ArrayList<>();

		// テスト用
		List<TestCase> testCases1 = new ArrayList<>();
		List<TestCase> testCases2 = new ArrayList<>();

		testCases1.add(new TestCase("001", 3));
		testCases1.add(new TestCase("002", 6));
		testScripts.add(new TestScript("dir1/dir2/TestScript1.xlsx", testCases1));
		testCases2.add(new TestCase("001", 10));
		testScripts.add(new TestScript("dir1/dir2/TestScript2.xlsx", testCases2));

		return testScripts;
	}

}
