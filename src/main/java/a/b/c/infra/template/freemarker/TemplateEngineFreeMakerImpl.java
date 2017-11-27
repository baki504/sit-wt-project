package a.b.c.infra.template.freemarker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import a.b.c.infra.exception.ReportGenerationException;
import a.b.c.infra.template.TemplateEngine;
import a.b.c.infra.template.TemplateModel;
import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateEngineFreeMakerImpl implements TemplateEngine {

	private FreeMarkerConfiguration configuration = new FreeMarkerConfiguration();

	private String defaultEncoding = configuration.getDefaultEncoding();

	@Override
	public void write(TemplateModel model) {

		Map<String, Object> root = new HashMap<>();
		root.put(model.getVar(), model);

		Template template = configuration.getTemplate(model.getTemplate());

		try (FileOutputStream out = new FileOutputStream(model.getPath());
				Writer file = new OutputStreamWriter(out, defaultEncoding)) {
			Environment env = template.createProcessingEnvironment(root, file);
			env.setOutputEncoding(defaultEncoding);
			env.process();
		} catch (IOException | TemplateException e) {
			throw new ReportGenerationException(e);
		}

	}

}
