package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

/**
 * When an assertion fails, don't throw an exception but record the failure.
 * Calling {@code
 * assertAll()} will cause an exception to be thrown if at least one assertion
 * failed.
 */
public class SoftAssert extends Assertion {
	// LinkedHashMap to preserve the order
	private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
	private static final String DEFAULT_SOFT_ASSERT_MESSAGE = "The following asserts failed:";

	@Override
	protected void doAssert(IAssert<?> a) {
		onBeforeAssert(a);
		try {
			a.doAssert();
			onAssertSuccess(a);
		} catch (AssertionError ex) {
			onAssertFailure(a, ex);
			m_errors.put(ex, a);
		} finally {
			onAfterAssert(a);
		}
	}

	public void assertAll() {
		assertAll(null);
	}

	public void assertAll(String message) {
		if (!m_errors.isEmpty()) {
			StringBuilder sb = new StringBuilder(null == message ? DEFAULT_SOFT_ASSERT_MESSAGE : message);
			boolean first = true;
			for (AssertionError error : m_errors.keySet()) {
				if (first) {
					first = false;
				} else {
//          sb.append(",");
				}
				sb.append("\n\t");
				sb.append(getErrorDetails(error));
			}
			try {
				if (System.getenv("JENKINS_HOME") != null) {
					Charset charset = StandardCharsets.UTF_8;
					String buildFolderPath = System.getenv("JENKINS_HOME");
					String jobNamePath = buildFolderPath + "\\jobs\\" + System.getenv("JOB_NAME");
					String reportFilePath = jobNamePath + "\\buildReport.txt";
					String reportFilePath_TEMP = jobNamePath + "\\buildReport_TEMP.txt";

					String text = "";
					File file = new File(reportFilePath);
					if (!file.exists())
					file.createNewFile();
					Path path = Paths.get(reportFilePath);
					Files.newByteChannel(path).close();
					
					File file_TEMP = new File(reportFilePath_TEMP);
					if (file_TEMP.exists())
						file_TEMP.delete();
					file_TEMP.createNewFile();
					
					Path path_TEMP = Paths.get(reportFilePath_TEMP);
					Files.newByteChannel(path_TEMP).close();
					Files.write(path_TEMP, sb.toString().getBytes(charset));   //içine yazdı

					first = true;
					String line;
					FileReader fr = new FileReader(file_TEMP);
					BufferedReader br = new BufferedReader(fr);
					while ((line = br.readLine()) != null) {
						if (line.replace(" ", "").trim().length() < 1)
							continue;
						if (first) { 
							first = false;
							text += "<div style=\"font-size:13px;\"><div style=\"color:red;\">--------------------</div> <p style=\"color:red;\">" + line;
						} else {
							if (line.contains("Step No:"))
								text += "<p style=\"color:DarkRed;\">" + line + "</p>";
							else
								text += line + "<br>";
						}
					}
					text += "</div><p></p>";
					br.close();
					String content;
					content = new String(Files.readAllBytes(path), charset);
					content += text;
					Files.write(path, content.getBytes(charset));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new AssertionError(sb.toString());
		}
	}
}
