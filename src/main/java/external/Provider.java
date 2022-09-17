package external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Random;

public class Provider {

	
	public Connection MyConnectionString(String env) throws Exception {

//		String fileName = "C:\\automation\\connection.txt";
//		File file = new File(fileName);
//		FileReader fr = new FileReader(file);
//		BufferedReader br = new BufferedReader(fr);
//		String line;
//		while ((line = br.readLine()) != null) {
//			if (line.equals(env))
//				break;
//		}
//
//		String hostname = br.readLine();
//		String port = br.readLine();
//		String dbName = br.readLine();
//		String userName = br.readLine();
//		String password = br.readLine();	
//		br.close();
		Connection con = null;
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
		String jdbcUrl = "";
//		String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;		
		con = DriverManager.getConnection(jdbcUrl);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("Trying again to connect..");
			 con = DriverManager.getConnection("", "", "");
             }
		return con;
	}

	public String ExecuteScalar(String query, String env) {
		try{
		Connection con = MyConnectionString(env);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		String value = rs.getString(1);
		con.close();
		rs.close();
		st.close();
		return value;
		}
		catch(Exception ex) {
		return null;	
		}
	}

	public void ExecuteCommand(String query, String env) throws Exception {
		Connection con = MyConnectionString(env);
		Statement st = con.createStatement();
		st.execute(query);
		st.close();
		con.close();	
	}

	public String[][] GetDataTable(String query, String env) {
		try {
			int col = 0, row = 0;
			Connection con = MyConnectionString(env);
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rowdata = (ResultSetMetaData) rs.getMetaData();

			if (!rs.next()) {
				System.out.println("db'den geri değer dönmedi");
				return null;
			}

			col = rowdata.getColumnCount();
			rs.last();
			row = rs.getRow();

			String[][] datatable = new String[row + 1][col];

			for (int i = 0; i < col; i++) {
				datatable[0][i] = rowdata.getColumnName(i + 1);
			}

			int i = 0;
			rs.beforeFirst();
			while (rs.next()) {
				i++;
				for (int k = 0; k < col; k++)
					datatable[i][k] = rs.getString(k + 1);
			}

			con.close();
			rs.close();
			st.close();
			return datatable;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}

	}

//	public String[][] GetDataTable(String query, String env) throws Exception {
//		try {
//			int col = 0, row = 0;
//			String[] DBInfo = MyConnectionString(env);
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			Connection con = DriverManager.getConnection("jdbc:sqlserver://"+ DBInfo[0] +";databaseName=" + DBInfo[2], DBInfo[3], DBInfo[4]);Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery(query);
//
//			ResultSetMetaData rowdata = (ResultSetMetaData) rs.getMetaData();
//			col = rowdata.getColumnCount();
//
//			while (rs.next())
//				row++;
//			rs.close();
//
//			String[][] datatable = new String[row][col];
//			ResultSet rs2 = st.executeQuery(query);
//
//			while (rs2.next()) {
//				for (int i = 0; i < row; i++) {
//					for(int k =0; k< col; k++)
//					datatable[i][k] = rs2.getString(k+1);
//				}
//			}
//			
//			con.close();
//			rs2.close();
//			st.close();
//			return datatable;
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//			return null;
//		}
//
//	}

}



//package dbmodel;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.Statement;
//import java.util.Random;
//
//public class Provider {
//
////	public void main(String[] args) throws Exception {
////
////		 ExecuteScalar("select * from environments where rownum=1", "VTAS");
////		// ExecuteCommand("update KEYTORC_RESERVATION set gsm_no='4444'",
////		// "VTAS");
////		try {
////			GetDataTable("select * from environments where system='TRAFGEN'", "VTAS");
////			System.out.println("basarili");
////		} catch (Exception ex) {
////			System.out.println("hata!");
////		}
////
////	}
//
//	public Connection MyConnectionString(String env) throws Exception {
//
////		String fileName = "C:\\automation\\connection.txt";
////		File file = new File(fileName);
////		FileReader fr = new FileReader(file);
////		BufferedReader br = new BufferedReader(fr);
////		String line;
////		while ((line = br.readLine()) != null) {
////			if (line.equals(env))
////				break;
////		}
//		
//		String hostname = "";
//		String port = "";
//		String dbName = "";
//		String userName = "";
//		String password = "";
//
//		if (env.equals("automationDB")) {
//			hostname = "OqmEBImssiGzo/MmmkwTow/691Kwwq8Z4aGysQEYSDv7GjdabYpWbxDedpvEuKojXkU2JPPRfjVXPd0pSDqaHg==";
//			port = "Z3PU0nU4cb+DlIme5nPEIg==";
//			dbName = "ftbSjE6SE1rDNMInGujCQw==";
//			userName = "vlKdfw0HfGtywjcNCx/Mgg==";
//			password = "DOXgio39cybS+BP5lri96A==";
//		} else if (env.equals("martiDB")) {
//			hostname = "OqmEBImssiGzo/MmmkwTow/691Kwwq8Z4aGysQEYSDv7GjdabYpWbxDedpvEuKojXkU2JPPRfjVXPd0pSDqaHg==";
//			port = "Z3PU0nU4cb+DlIme5nPEIg==";
//			dbName = "pd/OF89RiPtXd0vrjNudIQ==";
//			userName = "ng6eYFEt/Ph6lsjbwooc69OANdgMgPnI+rQD8l2Xn2I=";
//			password = "KjHhdDn+gpI/zzZ5And4enl4o0g4HfyxwUcCfO/P078=";
//		}
//
//		String jdbcUrl = "jdbc:postgresql://" + AES.getNeededText(hostname) + ":" + AES.getNeededText(port) + "/" + AES.getNeededText(dbName) + "?user=" + AES.getNeededText(userName)
//				+ "&password=" + AES.getNeededText(password);
//		Connection con = DriverManager.getConnection(jdbcUrl);
//		return con;
//
//	}
//
//	public String SetRandomString(int byteLength) {
//
//		String text = "";
//		for (int i = 0; i < byteLength; i++) {
//			Random rnd = new Random();
//			char c = (char) (rnd.nextInt(26) + 'a');
//			text = text + c;
//		}
//		return text;
//	}
//
//	public String checkDBCell(String prefix, String value) {
//		if (value == null || value.isEmpty()) {
//			value = prefix + SetRandomString(15);
//		}
//		return value;
//	}
//
//	public String ExecuteScalar(String query, String env) throws Exception {
//		Connection con = MyConnectionString(env);
//		Statement st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		rs.next();
//		String value = rs.getString(1);
//		con.close();
//		rs.close();
//		st.close();
//		return value;
//	}
//
//	public void ExecuteCommand(String query, String env) throws Exception {
//		Connection con = MyConnectionString(env);
//		Statement st = con.createStatement();
//		st.execute(query);
//		st.close();
//		con.close();
//
//	}
//
//	public String[][] GetDataTable(String query, String env) {
//		try {
//			int col = 0, row = 0;
//			Connection con = MyConnectionString(env);
//			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			ResultSet rs = st.executeQuery(query);
//			ResultSetMetaData rowdata = (ResultSetMetaData) rs.getMetaData();
//
//			if (!rs.next()) {
//				System.out.println("db'den geri değer dönmedi");
//				return null;
//			}
//
//			col = rowdata.getColumnCount();
//			rs.last();
//			row = rs.getRow();
//
//			String[][] datatable = new String[row + 1][col];
//
//			for (int i = 0; i < col; i++) {
//				datatable[0][i] = rowdata.getColumnName(i + 1);
//			}
//
//			int i = 0;
//			rs.beforeFirst();
//			while (rs.next()) {
//				i++;
//				for (int k = 0; k < col; k++)
//					datatable[i][k] = rs.getString(k + 1);
//			}
//
//			con.close();
//			rs.close();
//			st.close();
//			return datatable;
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//			return null;
//		}
//
//	}
//
////	public String[][] GetDataTable(String query, String env) throws Exception {
////		try {
////			int col = 0, row = 0;
////			String[] DBInfo = MyConnectionString(env);
////			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
////			Connection con = DriverManager.getConnection("jdbc:sqlserver://"+ DBInfo[0] +";databaseName=" + DBInfo[2], DBInfo[3], DBInfo[4]);Statement st = con.createStatement();
////			ResultSet rs = st.executeQuery(query);
////
////			ResultSetMetaData rowdata = (ResultSetMetaData) rs.getMetaData();
////			col = rowdata.getColumnCount();
////
////			while (rs.next())
////				row++;
////			rs.close();
////
////			String[][] datatable = new String[row][col];
////			ResultSet rs2 = st.executeQuery(query);
////
////			while (rs2.next()) {
////				for (int i = 0; i < row; i++) {
////					for(int k =0; k< col; k++)
////					datatable[i][k] = rs2.getString(k+1);
////				}
////			}
////			
////			con.close();
////			rs2.close();
////			st.close();
////			return datatable;
////		} catch (Exception ex) {
////			System.out.println(ex.getMessage());
////			return null;
////		}
////
////	}
//
//}
