package a.b.c.domain.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import a.b.c.domain.testscript.TestScript;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ReportWriter {

	private FreeMarkerConfiguration configuration = new FreeMarkerConfiguration();

	private ReportDir reportDir = new ReportDir();

	public void write(List<TestScript> testScrits, Path reportFile) {

		if (testScrits.size() == 0) {
			return;
		}

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "TestScript Report");
		input.put("testScrits", testScrits);

		Template template = configuration.getTemplate();

		try (Writer consoleWriter = new OutputStreamWriter(System.out);
				Writer fileWriter = new FileWriter(reportFile.toFile())) {
			template.process(input, consoleWriter);
			template.process(input, fileWriter);

		} catch (IOException | TemplateException e1) {
			e1.printStackTrace();
		}

		moveToReportDir(reportFile);
	}

	private void moveToReportDir(Path reportFile) {
		try {
			FileUtils.moveFileToDirectory(reportFile.toFile(), reportDir.getDir(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
