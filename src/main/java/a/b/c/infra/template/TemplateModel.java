package a.b.c.infra.template;

import org.apache.commons.io.FilenameUtils;

import lombok.Data;

@Data
public class TemplateModel {

	private String name;

	private String outDir;

	private String template;

	private String var;

	public String getPath() {
		return FilenameUtils.concat(outDir, name);
	}

}
