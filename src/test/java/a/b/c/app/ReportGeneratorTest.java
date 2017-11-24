package a.b.c.app;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReportGeneratorTest {

	@Test
	public void test() throws Exception {

		// Exercise
		int result = ReportGenerator.staticExecute();

		// Verify
		assertThat(result, is(0));

		List<String> actual = Files.lines(Paths.get("report.html"), StandardCharsets.UTF_8)
				.collect(Collectors.toList());

		List<String> expected = Files.lines(Paths.get("expected.html"), StandardCharsets.UTF_8)
				.collect(Collectors.toList());

		for (int i = 0; i < expected.size(); i++) {
			assertThat("Line: " + i + 1, actual.get(i), is(expected.get(i)));
		}

	}

}
