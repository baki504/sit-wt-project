package a.b.c.app;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReportGeneratorTest {

	private static final Pattern REPORT_DIR_NAME_PATTERN = Pattern.compile("^(report-\\d{14})$");

	@Test
	public void test() throws Exception {

		// Exercise
		ReportGenerator.main(new String[0]);

		// Verify
		List<String> actual = Files.lines(Paths.get(findLatestReportDirPath() + "/report.html"), StandardCharsets.UTF_8)
				.collect(Collectors.toList());

		List<String> expected = Files.lines(Paths.get("report-expected/expected.html"), StandardCharsets.UTF_8)
				.collect(Collectors.toList());

		for (int i = 0; i < expected.size(); i++) {
			assertThat("Line: " + i + 1, actual.get(i), is(expected.get(i)));
		}

	}

	private static String findLatestReportDirPath() {

		List<File> currentDirFiles = Arrays.asList(new File(System.getProperty("user.dir")).listFiles());

		File currentDir = currentDirFiles.stream()
				.filter(dir -> REPORT_DIR_NAME_PATTERN.matcher(dir.getName()).find())
				.sorted((dir1, dir2) -> dir2.getName().compareTo(dir1.getName()))
				.findFirst()
				.get();

		return currentDir.getPath();

	}

}
