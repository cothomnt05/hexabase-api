package collections;

import org.json.JSONObject;

import io.restassured.response.Response;

public class Workspace {
	public static String createNewWorkspace(String workspaceName) {
		JSONObject body = new JSONObject();
		body.put("name", workspaceName);

		Response res = Request.POST(constants.API.BaseURI, constants.API.CREATE_NEW_WORKSPACE, body.toString());

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create workspace");
		} else {
			System.out.println("Create workspace successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());

		return bodyRes.getString("w_id");
	}

	public static Response getUserInfo() {
		return Request.GET(constants.API.BaseURI, constants.API.GET_USER_INFO);
	}

	public static String getCurrentWorkspaceID() {
		JSONObject resObj = new JSONObject(getUserInfo().getBody().asString());

		return resObj.getString("current_workspace_id");
	}

	public static String getCurrentUserID() {
		JSONObject resObj = new JSONObject(getUserInfo().getBody().asString());

		return resObj.getString("u_id");
	}

	public static void archiveWorkspace(String workspaceID) {
		JSONObject body = new JSONObject();
		body.put("w_id", workspaceID);

		Response res = Request.POST(constants.API.BaseURI_V1, constants.API.ARCHIVE_WORKSPACE, body.toString());
		int statusCode = res.getStatusCode();

		if (statusCode != 200) {
			System.out.println("Can't delete workspace");
		} else {
			System.out.println("Delete workspace successfully");
		}
	}

}
