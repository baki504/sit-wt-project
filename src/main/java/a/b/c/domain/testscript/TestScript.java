package a.b.c.domain.testscript;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TestScript {

	private String filePath;

	private List<TestCase> testCases = new ArrayList<>();

}
