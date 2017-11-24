package a.b.c.infra.template;

import java.nio.file.Paths;

import lombok.Data;

@Data
public class TemplateModel {

	private String name;

	private String outDir;

	private String template;

	private String var;

	public String getPath() {
		return Paths.get(outDir, name).toString();
	}

}
