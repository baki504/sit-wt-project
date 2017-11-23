package a.b.c.domain.testscript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScript {

	private File file;

	private String filePath;

	private List<TestCase> testCases = new ArrayList<>();

}
