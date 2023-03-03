package collections;

import constants.Variables;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Request {
	public static Response GET(String baseURI, String basePath) {

		return RestAssured.given().baseUri(baseURI).basePath(basePath).header("Content-Type", "application/json").auth()
				.oauth2(Variables.TOKEN).when().get();
	}

	public static Response POST(String baseURI, String basePath, String body) {

		return RestAssured.given().baseUri(baseURI).basePath(basePath).header("Content-Type", "application/json").auth()
				.oauth2(Variables.TOKEN).body(body).when().post();
	}

	public static Response PUT(String baseURI, String basePath, String body) {

		return RestAssured.given().baseUri(baseURI).basePath(basePath).header("Content-Type", "application/json").auth()
				.oauth2(Variables.TOKEN).body(body).when().put();
	}

	public static Response PATCH(String baseURI, String basePath, String body) {

		return RestAssured.given().baseUri(baseURI).basePath(basePath).header("Content-Type", "application/json").auth()
				.oauth2(Variables.TOKEN).body(body).when().patch();
	}

	public static Response DELETE(String baseURI, String basePath) {

		return RestAssured.given().baseUri(baseURI).basePath(basePath).header("Content-Type", "application/json").auth()
				.oauth2(Variables.TOKEN).when().delete();
	}

}
