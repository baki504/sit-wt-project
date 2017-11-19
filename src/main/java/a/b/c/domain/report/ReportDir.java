package a.b.c.domain.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;

public class ReportDir {

	private static File dir;

	private static final String[] reportResouces = { "css/style.css", "/css/bootstrap.min.css" };

	static {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String reportDirName = "report-" + LocalDateTime.now().format(f);

		dir = new File(reportDirName);

		try {
			for (int i = 0; i < reportResouces.length; i++) {
				InputStream srcStream = ClassLoader.getSystemResourceAsStream("report/" + reportResouces[i]);
				File destFile = new File(dir, reportResouces[i]);
				FileUtils.copyInputStreamToFile(srcStream, destFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public File getDir() {
		return dir;
	}

}
