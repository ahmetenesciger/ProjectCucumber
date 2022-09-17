package test;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.Configuration;
import utilities.ServiceReplicator;
import external.Provider;

public class ServiceBase extends ServiceReplicator{
	protected Configuration configurationGet;
//    protected String[][] testParameters;
//    protected String queryGetParameters;

	Provider provider = new Provider();

	public ServiceBase() {

	}

	protected void wait(int second) {
		try {
			TimeUnit.SECONDS.sleep(second);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected String getValueOfJSONResult(String result, String key) {
		result = result.substring(result.indexOf(key) + key.length() + 1);
		result = result.substring(result.indexOf("\"") + 1);
		result = result.substring(0, result.indexOf("\"", 0));
		System.out.println(key + " : " + result);
		return result;
	}

	protected String[][] paramHeaders(String service_name) {		
		String[][] params = { { "serviceName", service_name }, { "", "" }, { "", "" }, { "", "" }, { "", "" },
				{ "", "" } };
		try {
		if (System.getProperty("suiteXmlFile") == null) {
			String fileName = System.getProperty("user.dir") + "//src//test//resources//api//" + service_name + ".txt";
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.toLowerCase().contains("headers:")) {  //header gibi query, path çıkarabilirsinde. ama böyle iş görüyor.
					for (int i = 1; i < 6; i++) {
						line = br.readLine();
						if (line.toLowerCase().contains("body:") || line.toLowerCase().contains("statuscode:") || line.toLowerCase().contains("response:"))
							break;
						try {
							params[i][0] = line.substring(0, line.indexOf(":")).trim();
							params[i][1] = line.substring(line.indexOf(":") + 1).trim();
						} catch (Exception ex) {
							i--;
						}
					}
				}
			}
			br.close();
		} else {
			for (int i = 1; i < 6; i++) {
				params[i][0] = (System.getProperty("apiKeyParameter" + (i + 1)) == null) ? ""
						: System.getProperty("apiKeyParameter" + (i + 1));
				params[i][1] = (System.getProperty("apiValueParameter" + (i + 1)) == null) ? ""
						: System.getProperty("apiValueParameter" + (i + 1));
			}
		}
	} catch (Exception ex) {

	}
		return params;
		
	}
	
	// eğer value'da koseli parantez varsa bu paramtrede belirtilmelidir.
	protected void setBody(String service_name, String key, String value, int index) {
		Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//api//" + service_name + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		String content;
		try {
			content = new String(Files.readAllBytes(path), charset);
			String nextText = content.substring(content.indexOf(key) + key.length());
			int nextTextComma = nextText.indexOf(",");
			nextText = nextText.substring(nextTextComma);
			if (value.equals("true") || value.equals("false"))
				value = value.replace("\"", "");
			else
				value = "\"" + value + "\"";
			for (char digit : value.toCharArray()) {
				if ((int) digit > 47 && (int) digit < 58) {
					value = value.replace("\"", "");
					break;
				}
			}
			if (value.equals("true") || value.equals("false")) {
				value = value.replace("\"", "");
			}
			nextText = " " + value + nextText;
			content = content.substring(0, content.indexOf(key) + key.length() + 2) + nextText;
			Files.write(path, content.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setHeader(String service_name, String key, String value) {
		Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//api//" + service_name + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		String content = null;
			try {
				content = new String(Files.readAllBytes(path), charset);
			
			String firstValue = content.substring(0, content.indexOf(key) + key.length());		
			String lastValue = content.substring(content.indexOf(key) + key.length());		
			String oldValue = lastValue.substring(lastValue.indexOf(":")+1, lastValue.indexOf("\n"));	
			lastValue = lastValue.substring(lastValue.indexOf("\n"));

			content = firstValue + ":" + value + lastValue;
			Files.write(path, content.getBytes(charset));
			System.out.println("degisen header degeri(" + key + ") : " + oldValue + " >>> " + value);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	protected String get(String baseUrl, String endPoint, String[][] headers) {
		for (int i = 1; i < Integer.parseInt(System.getProperty("requestCount")); i++) {
			new Thread(() -> {
				sendGet(baseUrl, endPoint, headers);
			}).start();
			System.out.println("Service " + (i) + " is running.");
			wait(Integer.parseInt(System.getProperty("interval")));
		}
		Date date1 = new Date();
		String response = sendGet(baseUrl, endPoint, headers);
		Date date2 = new Date();
		System.out.println("Son çağrılan servis " + (date2.getTime() - date1.getTime())
				+ " MILLISECONDS sonra tamamlandı..\n--\n");
		return response;
	}
	
	protected String delete(String baseUrl, String endPoint, String[][] headers) {
		for (int i = 1; i < Integer.parseInt(System.getProperty("requestCount")); i++) {
			new Thread(() -> {
				sendDelete(baseUrl, endPoint, headers);
			}).start();
			System.out.println("Service " + (i) + " is running.");
			wait(Integer.parseInt(System.getProperty("interval")));
		}
		Date date1 = new Date();
		String response = sendDelete(baseUrl, endPoint, headers);
		Date date2 = new Date();
		System.out.println("Son çağrılan servis " + (date2.getTime() - date1.getTime())
				+ " MILLISECONDS sonra tamamlandı..\n--\n");

		// gelen response daki "message" alanını kontrol eder. true = SUCCESS (en hızlı
		// yöntem) bu yüzden yük testi burdan yapılmaya başlanınca bu yöntem seçileebilir statuscode kontol yerine
//		assertEquals(
//				response.substring(response.indexOf("message") + 9,
//						response.indexOf(",", response.indexOf("message")) - 1).replace("\"", "").toLowerCase(),
//				"success");
		return response;
	}

	protected String post(String baseUrl, String endPoint, String[][] headers) {
		for (int i = 1; i < Integer.parseInt(System.getProperty("requestCount")); i++) {
			new Thread(() -> {
				sendPost(baseUrl, endPoint, headers);
			}).start();
			System.out.println("Service " + (i) + " is running.");
			wait(Integer.parseInt(System.getProperty("interval")));
		}
		Date date1 = new Date();
		String response = sendPost(baseUrl, endPoint, headers);
		Date date2 = new Date();
		System.out.println("Son çağrılan servis " + (date2.getTime() - date1.getTime())
				+ " MILLISECONDS sonra tamamlandı..\n--\n");
		return response;
	}


	protected String put(String baseUrl, String endPoint, String[][] headers) {
		for (int i = 1; i < Integer.parseInt(System.getProperty("requestCount")); i++) {
			new Thread(() -> {
				sendPut(baseUrl, endPoint, headers);
			}).start();
			System.out.println("Service " + (i) + " is running.");
			wait(Integer.parseInt(System.getProperty("interval")));
		}
		Date date1 = new Date();
		String response = sendPut(baseUrl, endPoint, headers);
		Date date2 = new Date();
		System.out.println("Son çağrılan servis " + (date2.getTime() - date1.getTime())
				+ " MILLISECONDS sonra tamamlandı..\n--\n");
		return response;
	}

	


	// THREAD modeli deişiceksen bundan ilerle
//  TasarimThread thread1 = new TasarimThread("Tasarim1");
//  thread1.start();
//  TasarimThread thread2 = new TasarimThread("Tasarim2");
//  thread2.start();
//	class TasarimThread implements Runnable {
//	    Thread tasarimThread;
//	    private String tasarimAd;
//	    TasarimThread(String ad) {
//	        tasarimAd = ad;
//	    }
//	    @Override
//	     public void run() {
//	        System.out.println("Thread çalışıyor" + tasarimAd);
//	        run_get();
//	    }
//	    public void start() {
//	        System.out.println("Thread başladı!");
//	        if (tasarimThread == null) 
//	        {
//	            tasarimThread = new Thread(this, tasarimAd);
//	            tasarimThread.start();
//	        }
//	    }
//	}
	// public String get_token(int case_id) throws Exception{
//	String endPoint = "";
//	String accessToken = "";
//	String otp = "";
//	RestAssured.baseURI = System.getProperty("baseURL");
//
//	if (System.getProperty("appName").equals("ride")) {
//		endPoint = "/v"+ testParameters[15][4] +"/dispatch/" + testParameters[15][1];
//		otp = provider.ExecuteScalar("select sms_code from customers where mobile_phone ='" + testParameters[case_id][6] + "';", "martiDB");
//		Response response = RestAssured
//				.given()
//				.header("timezone", "3")
//				.contentType("application/json")
//				.body("{ \"smsCode\": \""+ otp +"\", \"mobilePhoneCountryCode\": \"90\", \"mobilePhone\": \"" + testParameters[case_id][6] + "\" }")
//				.when()
//				.post(endPoint)
//				.then()
//				.extract()
//				.response();
//		accessToken = response.path("data.accessToken");
//	}
//	else if (System.getProperty("appName").equals("operator")) {
//		endPoint = "/v"+ testParameters[59][4] +"/dispatch/" + testParameters[59][1];
//		otp = provider.ExecuteScalar("select otp_token from users where mobile_phone = '" + testParameters[case_id][6] + "';", "martiDB");
//		Response response = RestAssured
//				.given()
//				.header("timezone", "3")
//				.contentType("application/json")
//				.body("{ \"mobilePhoneNumber\": \""+ testParameters[case_id][6] +"\", \"token\": \""+ otp +"\" }")
//				.when()
//				.post(endPoint)
//				.then()
//				.extract()
//				.response();
//			System.out.println(response.getBody().asString());
//			accessToken = response.path("data.accessToken");
//	}
//		return accessToken;
//}
	
	
	
	
	
	
	
//	protected String run_post() {
//
//////String tcId 			= testParameters[case_id][0];
////String endPoint			= testParameters[case_id][1];
//////String type				= testParameters[case_id][2];
////String statusCode 		= testParameters[case_id][3];
////String version 			= testParameters[case_id][4];
////String inputParameters	= testParameters[case_id][5];
////
////
//////////////set parameters////////////////
//////*
////endPoint = "/v"+ version +"/dispatch/" + endPoint;
//////*
////if (inputParameters == null) {
////	inputParameters = "";
////}
////
////if (dynamic_parameter != null) {
////	inputParameters = dynamic_parameter;
////}
//
////////////////////////////--
//
//		RestAssured.baseURI = System.getProperty("baseURL");
//		Response response = RestAssured.given().contentType("application/json;charset=utf-8")
//				.body("{\n" + "  \"cancelAllChildCoupons\": true,\n"
//						+ "  \"couponId\": \"5f20f855-63f5-4813-9396-10f0293d86a9\",\n"
//						+ "  \"toBeCanceledCouponCount\": 1\n" + "}")
//				.when().post().then()
////		.statusCode(Integer.parseInt(statusCode))
////		.body("isSuccess", equalTo(true))   // e bunu acarsin. bu onemli..
////		.body("message", equalTo("SUCCESS"))
//				.extract().response();
//
////gelen response daki "message" alanını kontrol eder. true = SUCCESS (en hızlı yöntem)
//		assertEquals(response.getBody().asString().substring(response.getBody().asString().indexOf("message") + 9,
//				response.getBody().asString().indexOf(",", response.getBody().asString().indexOf("message")) - 1)
//				.replace("\"", "").toLowerCase(), "success");
//		System.out.println(
//				"enpointname" + " --- RESPONSE: " + response.getBody().asString().substring(0, 120) + "... \n--");
//		return response.getBody().asString();
//	}
	
	
	
	
	// protected String getold(String ep, String[][] headers) {
//	Date date1 = new Date();
//	RestAssured.baseURI = System.getProperty("baseURL");
//	Response response = RestAssured
//	.given()
//	.header("Accept-Encoding", "gzip, deflate, br")
//	.header("Content-Type", "application/json;charset=UTF-8")
//	.header(headers[0][0], headers[0][1])
//	.header(headers[1][0], headers[1][1])
//	.header(headers[2][0], headers[2][1])
//	.header(headers[3][0], headers[3][1])
//	.header(headers[4][0], headers[4][1])
//	.get(ep)
//	.then()
//	.statusCode(200)
//	.extract()
//	.response();
//	Date date2 = new Date();
//	
//	//gelen response daki "message" alanını kontrol eder. true = SUCCESS (en hızlı yöntem)
//	assertEquals(response.getBody().asString().substring(response.getBody().asString().indexOf("message") + 9, response.getBody().asString().indexOf(",", response.getBody().asString().indexOf("message"))-1).replace("\"", "").toLowerCase(), "success");	
//	System.out.println("enpointname" + " --- RESPONSE: " + response.getBody().asString() + "... \n--");
//    System.out.println("Çağrılan servis " + (date2.getTime() - date1.getTime()) + " MILLISECONDS sonra tamamlandı..\n--\n");
//	return response.getBody().asString();
//	}
}