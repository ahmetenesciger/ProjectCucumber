package utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ServiceReplicator {
		
	protected String sendGet(String baseUrl, String ep, String[][] headers) {
		Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//api//" + headers[0][1] + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		String body = "";
		int statusCode = 200;
		try {
			body = new String(Files.readAllBytes(path), charset);
			statusCode = Integer.parseInt(body.substring(body.indexOf("StatusCode:")+11, body.indexOf("\n", body.indexOf("StatusCode:"))).trim());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		RestAssured.baseURI = baseUrl;
		Response response = null;
		int count=-1;
		for (String[] strings : headers) {
			if (strings[0].equals(""))
				break;
			count++;
		}
		if (count == 0) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.get(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
		}
		else if (count == 1) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.get(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 2) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.get(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 3) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.header(headers[3][0], headers[3][1])
					.get(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 4) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.header(headers[3][0], headers[3][1])
					.header(headers[4][0], headers[4][1])
					.get(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 5) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.header(headers[3][0], headers[3][1])
					.header(headers[4][0], headers[4][1])
					.header(headers[5][0], headers[5][1])
					.get(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		System.out.println(response.getBody().asString());
		return response.getBody().asString();
	}
	
	
	protected String sendDelete(String baseUrl, String ep, String[][] headers) {
		Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//api//" + headers[0][1] + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		String body = "";
		int statusCode = 200;
		try {
			body = new String(Files.readAllBytes(path), charset);
			statusCode = Integer.parseInt(body.substring(body.indexOf("StatusCode:")+11, body.indexOf("\n", body.indexOf("StatusCode:"))).trim());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		RestAssured.baseURI = baseUrl;
		Response response = null;
		int count=-1;
		for (String[] strings : headers) {
			if (strings[0].equals(""))
				break;
			count++;
		}
		
		if (count == 1) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.delete(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 2) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.delete(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 3) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.header(headers[3][0], headers[3][1])
					.delete(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		else if (count == 4) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.header(headers[3][0], headers[3][1])
					.header(headers[4][0], headers[4][1])
					.delete(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}  
		else if (count == 5) {
					response = RestAssured.given()
					.header("Accept-Encoding", "gzip, deflate, br")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header(headers[1][0], headers[1][1])
					.header(headers[2][0], headers[2][1])
					.header(headers[3][0], headers[3][1])
					.header(headers[4][0], headers[4][1])
					.header(headers[5][0], headers[5][1])
					.delete(ep)
					.then()
					.statusCode(statusCode)
					.extract()
					.response();
		}
		System.out.println(response.getBody().asString());
		return response.getBody().asString();
	}
	
	
	protected String sendPost(String baseUrl, String ep, String[][] headers) {
		Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//api//" + headers[0][1] + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		String body = "";
		int statusCode = 200;
			try {
				body = new String(Files.readAllBytes(path), charset);
				statusCode = Integer.parseInt(body.substring(body.indexOf("StatusCode:")+11, body.indexOf("\n", body.indexOf("StatusCode:"))).trim());
				body = body.substring(body.indexOf("Body:")+5, body.indexOf("StatusCode:")).trim();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		RestAssured.baseURI = baseUrl;
		Response response = null;
		int count=-1;
		for (String[] strings : headers) {
			if (strings[0].equals(""))
				break;
			count++;
		}
		if (count == 0) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.body(body)
			.when()
			.post(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
		else if (count == 1) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.body(body)
			.when()
			.post(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 2) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.body(body)
			.when()
			.post(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 3) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.header(headers[3][0], headers[3][1])
			.post(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 4) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.header(headers[3][0], headers[3][1])
			.header(headers[4][0], headers[4][1])
			.body(body)
			.when()
			.post(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 5) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.header(headers[3][0], headers[3][1])
			.header(headers[4][0], headers[4][1])
			.header(headers[5][0], headers[5][1])
			.body(body)
			.when()
			.post(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
System.out.println(response.getBody().asString());
return response.getBody().asString();
	}
	
	
	protected String sendPut(String baseUrl, String ep, String[][] headers) {
		Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//api//" + headers[0][1] + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		String body = "";
		int statusCode = 200;
		try {
			body = new String(Files.readAllBytes(path), charset);
			statusCode = Integer.parseInt(body.substring(body.indexOf("StatusCode:")+11, body.indexOf("\n", body.indexOf("StatusCode:"))).trim());
				body = body.substring(body.indexOf("Body:")+5, body.indexOf("StatusCode:")).trim();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		RestAssured.baseURI = baseUrl;
		Response response = null;
		int count=-1;
		for (String[] strings : headers) {
			if (strings[0].equals(""))
				break;
			count++;
		}
		if (count == 1) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.body(body)
			.when()
			.put(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 2) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.body(body)
			.when()
			.put(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 3) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.header(headers[3][0], headers[3][1])
			.put(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 4) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.header(headers[3][0], headers[3][1])
			.header(headers[4][0], headers[4][1])
			.body(body)
			.when()
			.put(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
else if (count == 5) {
			response = RestAssured.given()
			.header("Accept-Encoding", "gzip, deflate, br")
			.header("Content-Type", "application/json;charset=UTF-8")
			.header(headers[1][0], headers[1][1])
			.header(headers[2][0], headers[2][1])
			.header(headers[3][0], headers[3][1])
			.header(headers[4][0], headers[4][1])
			.header(headers[5][0], headers[5][1])
			.body(body)
			.when()
			.put(ep)
			.then()
			.statusCode(statusCode)
			.extract()
			.response();
}
System.out.println(response.getBody().asString());
return response.getBody().asString();
	}
	
}

