package a.b.c.domain.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import a.b.c.infra.ReportGenerationException;

public class ReportDir {

	private static File dir;

	private static final String REPORT_DIR_NAME = "report";

	private static final String REPORT_RESOURCE = "report/css/style.css";

	static {

		try {
			FileUtils.deleteDirectory(new File(REPORT_DIR_NAME));

			dir = new File(REPORT_DIR_NAME);

			InputStream srcStream = ClassLoader.getSystemResourceAsStream(REPORT_RESOURCE);
			File destFile = new File(REPORT_RESOURCE);
			FileUtils.copyInputStreamToFile(srcStream, destFile);

		} catch (IOException e) {
			throw new ReportGenerationException(e);
		}

	}

	public File getDir() {
		return dir;
	}

	public String getPath() {
		return getDir().getAbsolutePath();
	}

}
