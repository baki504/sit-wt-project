package a.b.c.domain.report;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;

public class ReportDir {

	private static File dir;

	private static final String REPORT_RESOURCE = "css/style.css";

	static {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String reportDirName = "report-" + LocalDateTime.now().format(f);

		dir = new File(reportDirName);

		try {
			InputStream srcStream = ClassLoader.getSystemResourceAsStream("report/" + REPORT_RESOURCE);
			File destFile = new File(dir, REPORT_RESOURCE);
			FileUtils.copyInputStreamToFile(srcStream, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public File getDir() {
		return dir;
	}

}
