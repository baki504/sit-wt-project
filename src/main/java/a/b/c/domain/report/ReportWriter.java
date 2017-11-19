package a.b.c.domain.report;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import a.b.c.domain.testscript.TestScript;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class ReportWriter {

	public void write(List<TestScript> testScrits, Path reportFile) throws Exception {

		Configuration cfg = new Configuration();

		cfg.setClassForTemplateLoading(ReportWriter.class, "/templates");

		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "TestScript Report");
		input.put("testScrits", testScrits);

		Template template = cfg.getTemplate("helloworld.ftl");

		Writer consoleWriter = new OutputStreamWriter(System.out);
		template.process(input, consoleWriter);

		Writer fileWriter = new FileWriter(new File("output.html"));
		try {
			template.process(input, fileWriter);
		} finally {
			fileWriter.close();
		}

	}

}
