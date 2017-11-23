package a.b.c.domain.report;

import java.util.ArrayList;
import java.util.List;

import a.b.c.domain.testscript.TestScript;
import a.b.c.infra.template.TemplateModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Report extends TemplateModel {

	private String title;

	private List<TestScript> testScripts = new ArrayList<>();

	public Report() {
		setTemplate("report-template.ftl");
		setVar("report");
	}

}
