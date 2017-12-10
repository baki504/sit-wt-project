package a.b.c.domain.testscript;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestScript {

	private static final String CASE_NAME_PREFIX = "ケース_";

	private String filePath;

	private List<TestCase> testCases = new ArrayList<>();

	public String getCaseNamePrefix() {
		return CASE_NAME_PREFIX;
	}

}
