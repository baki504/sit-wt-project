package a.b.c.domain.testscript;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestScriptReader {

	private TestScriptDao testScriptDao = new TestScriptDao();

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
			} else {
				readScript(file, testScripts);
			}
		}

	}

	private void readScript(File file, List<TestScript> testScripts) {
		TestScript testScript = testScriptDao.read(file);

		if (testScript == null) {
			return;
		}

		testScripts.add(testScript);

	}

}
