package collections;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Workspace {
	public static String createNewWorkspace(String token, String workspaceName) {
		JSONObject body = new JSONObject();
		body.put("name", workspaceName);

		Response res = RestAssured.given().baseUri(constants.API.BaseURI).basePath(constants.API.CREATE_NEW_WORKSPACE)
				.header("Content-Type", "application/json").auth().oauth2(token).body(body.toString()).when().post();

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create workspace");
		} else {
			System.out.println("Create workspace successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());

		return bodyRes.getString("w_id");
	}

	public static Response getUserInfo(String token) {
		return RestAssured.given().baseUri(constants.API.BaseURI).basePath(constants.API.GET_USER_INFO)
				.header("Content-Type", "application/json").auth().oauth2(token).when().get();

	}

	public static String getCurrentWorkspaceID(String token) {
		JSONObject resObj = new JSONObject(getUserInfo(token).getBody().asString());

		return resObj.getString("current_workspace_id");
	}

	public static String getCurrentUserID(String token) {
		JSONObject resObj = new JSONObject(getUserInfo(token).getBody().asString());

		return resObj.getString("u_id");
	}

}
