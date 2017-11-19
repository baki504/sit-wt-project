package a.b.c.domain.report;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerConfiguration {

	private static final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

	private static final String REPORT_TEMPLATE_NAME = "report-template.ftl";

	private static final String REPORT_TEMPLATE_PATH = "/templates";

	static {
		configuration.setClassForTemplateLoading(FreeMarkerConfiguration.class, REPORT_TEMPLATE_PATH);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
	}

	public Template getTemplate() {
		Template template = null;

		try {
			template = configuration.getTemplate(REPORT_TEMPLATE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return template;
	}

}
