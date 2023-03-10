package collections;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import constants.API;
import constants.Variables;
import io.restassured.response.Response;

public class Workspace {
	public static void createNewWorkspace(String workspaceName) {
		// Tao body
		JSONObject body = new JSONObject();
		body.put("name", workspaceName);

		// Call request POST create workspace
		Response res = Request.POST(constants.API.BaseURI, constants.API.CREATE_NEW_WORKSPACE, body.toString());

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create workspace");
		} else {
			System.out.println("Create workspace successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());

		Variables.WORKSPACE_ID = bodyRes.getString("w_id");
	}

	public static Response getUserInfo() {
		return Request.GET(constants.API.BaseURI, constants.API.GET_USER_INFO);
	}

	public static String getCurrentUserID() {
		JSONObject resObj = new JSONObject(getUserInfo().getBody().asString());

		return resObj.getString("u_id");
	}

	public static String getEmail() {
		JSONObject resObj = new JSONObject(getUserInfo().getBody().asString());

		return resObj.getString("email");
	}

	public static void archiveWorkspace(String workspaceID) {
		JSONObject body = new JSONObject();
		body.put("w_id", workspaceID);
		body.put("archived", true);

		Response res = Request.POST(constants.API.BaseURI_V1, constants.API.ARCHIVE_WORKSPACE, body.toString());
		int statusCode = res.getStatusCode();

		if (statusCode != 200) {
			System.out.println("Can't delete workspace");
		} else {
			System.out.println("Delete workspace successfully");
		}
	}

	public static String getCurrentWorkspace() {
		Response res = Request.GET(API.BaseURI, API.GET_CURRENT_WORKSPACE);

		JSONObject resObj = new JSONObject(res.getBody().asString());

		return resObj.getString("workspace_id");

	}

	public static Response getListWorkspace() {
		return Request.GET(API.BaseURI, API.GET_LIST_WORKSPACE);
	}

	public static List<String> getListWorkspaceID() {
		List<String> workspaceId = new ArrayList<>();
		JSONObject resBody = new JSONObject(getListWorkspace().getBody().asString());
		JSONArray workspaces = (JSONArray) resBody.get("workspaces");
		for (int i = 0; i < workspaces.length(); i++) {
			JSONObject obj = workspaces.getJSONObject(i);
			workspaceId.add(obj.get("workspace_id").toString());
		}
		return workspaceId;

	}

	public static List<String> getListWorkspaceName() {
		List<String> workspaceName = new ArrayList<>();
		JSONObject resBody = new JSONObject(getListWorkspace().getBody().asString());
		JSONArray workspaces = (JSONArray) resBody.get("workspaces");
		for (int i = 0; i < workspaces.length(); i++) {
			JSONObject obj = workspaces.getJSONObject(i);
			workspaceName.add(obj.get("workspace_name").toString());
		}
		return workspaceName;
	}

}
