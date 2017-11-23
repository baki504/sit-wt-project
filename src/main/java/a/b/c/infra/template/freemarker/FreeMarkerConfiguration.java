package a.b.c.infra.template.freemarker;

import java.io.IOException;

import a.b.c.infra.ConfigurationException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerConfiguration {

	private static final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

	private static final String REPORT_TEMPLATE_PATH = "/templates";

	private static final String DEFAULT_ENCODING = "UTF-8";

	static {
		configuration.setClassForTemplateLoading(FreeMarkerConfiguration.class, REPORT_TEMPLATE_PATH);
		configuration.setDefaultEncoding(DEFAULT_ENCODING);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
	}

	public Template getTemplate(String path) {
		Template template = null;

		try {
			template = configuration.getTemplate(path);
		} catch (IOException e) {
			throw new ConfigurationException(e);
		}

		return template;
	}

	public String getDefaultEncoding() {
		return DEFAULT_ENCODING;
	}

}
