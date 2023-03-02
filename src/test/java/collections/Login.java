package collections;

import java.io.File;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Login {
	public static String login() {
		Response res = RestAssured.given().baseUri(constants.API.BaseURI).basePath(constants.API.LOGIN)
				.header("Content-Type", "application/json").body(new File("./src/test/java/testData/userInfo.json"))
				.when().post();

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't login");
		} else {
			System.out.println("Login successfully");
		}
		JSONObject resObj = new JSONObject(res.getBody().asString());

		String token = resObj.getString("token");

		return token;
	}

}
