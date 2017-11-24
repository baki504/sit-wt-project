package a.b.c.app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import a.b.c.domain.report.ReportWriter;
import a.b.c.domain.testscript.TestScript;
import a.b.c.domain.testscript.TestScriptReader;

public class ReportGenerator {

	TestScriptReader reader = new TestScriptReader();

	ReportWriter writer = new ReportWriter();

	public static void main(String[] args) throws Exception {
		System.exit(new ReportGenerator().generate(Paths.get("testscript"), Paths.get("report.html")));
	}

	public int generate(Path targetDir, Path reportFile) throws Exception {
		try {

			List<TestScript> testScrits = reader.readRecursively(targetDir);

			writer.write(testScrits, reportFile);

			return 0;
		} catch (Exception e) {
			e.printStackTrace();

			return 1;
		}
	}

}
