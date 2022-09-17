package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MappedLogin {

	public static String[] get(String userRole) {
		String[] loginInfo = new String[4];
		String fileName;

		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows"))
				fileName = "C:/Program Files (x86)/Windows Media Player/Media Renderer/RenderControl.txt";
			else
				fileName = "/Users/" + System.getProperty("user.name") + "/Documents/automation.txt";

			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				if (line.equals(userRole))
					break;
			}
			loginInfo[0] = br.readLine();
			loginInfo[1] = br.readLine();
			if (System.getProperty("suiteXmlFile") != null)
			loginInfo[1] = external.AES.yukle(loginInfo[1]);
			loginInfo[2] = br.readLine();
			loginInfo[3] = br.readLine();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginInfo;
	}
}
