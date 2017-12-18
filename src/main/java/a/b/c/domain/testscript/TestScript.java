package a.b.c.domain.testscript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestScript {

	private File file;

	private List<TestCase> testCases = new ArrayList<>();

	public String getFilePath() {
		String filePath[] = StringUtils.split(file.getPath(), File.separator);
		return filePath == null ? StringUtils.EMPTY : StringUtils.join(filePath, "/");
	}

}
