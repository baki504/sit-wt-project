package a.b.c.domain.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import a.b.c.domain.testscript.TestScript;
import a.b.c.infra.exception.ReportGenerationException;
import a.b.c.infra.template.TemplateEngine;
import a.b.c.infra.template.freemarker.TemplateEngineFreeMakerImpl;

public class ReportWriter {

	private static final String REPORT_TITLE = "TestScript Report";

	private TemplateEngine templateEngine = new TemplateEngineFreeMakerImpl();

	public void write(List<TestScript> testScripts, Path reportFile) {

		if (testScripts.isEmpty()) {
			return;
		}

		removeOld(reportFile);

		Report report = buildReport(testScripts, reportFile);

		templateEngine.write(report);

	}

	private void removeOld(Path reportFile) {
		try {
			Files.deleteIfExists(reportFile);
		} catch (IOException e) {
			throw new ReportGenerationException(e);
		}

	}

	private Report buildReport(List<TestScript> testScripts, Path reportFile) {
		Report report = new Report();
		report.setTitle(REPORT_TITLE);
		report.setTestScripts(testScripts);
		report.setName(reportFile.getFileName().toString());
		report.setOutDir(reportFile.toAbsolutePath().getParent().toString());

		return report;
	}

}
