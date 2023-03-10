package collections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONObject;
import org.json.JSONTokener;

import constants.API;
import constants.Variables;
import io.restassured.response.Response;

public class Group {
	public static Response getGroupChildren() {
		return Request.GET(API.BaseURI, API.GET_GROUP_CHILDREN);
	}

	public static String getParentGroupID() {
		JSONObject resBody = new JSONObject(getGroupChildren().getBody().asString());
		JSONObject group = resBody.getJSONObject("group");
		return group.getString("g_id");
	}

	public static void createGroup(String groupName) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("src/test/java/testData/NewGroup.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject body = new JSONObject(tokener);
		body.put("workspace-id", Variables.WORKSPACE_ID);
		body.put("name", groupName);
		body.put("display_id", Variables.DISPLAY_ID + collections.Request.generateRandom());
		body.put("parent_g_id", Variables.PARENT_GROUP_ID);
		String basePath = API.CREATE_GROUP(Variables.PARENT_GROUP_ID);

		Response res = Request.POST(API.BaseURI, basePath, body.toString());
		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create group");
		} else {
			System.out.println("Create group successfully!");
		}

		JSONObject resBody = new JSONObject(res.getBody().asString());
		JSONObject group = resBody.getJSONObject("group");
		Variables.GROUP_ID = group.getString("g_id");
	}

	public static void deleteGroup() {
		JSONObject body = new JSONObject();
		body.put("id", Variables.GROUP_ID);
		System.out.println(body.toString());
		String basePath = API.DELETE_GROUP(Variables.GROUP_ID, Variables.WORKSPACE_ID);
		System.out.println(basePath);
		Response res = Request.DELETE(API.BaseURI, basePath, body.toString());
		if (res.getStatusCode() != 200) {
			System.out.println("Can't delete group");
		} else {
			System.out.println("Delete group successfully");
		}
		System.out.println(res.getBody().asString());
	}

	public static void addUserToGroup() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("src/test/java/testData/NewUserToGroup.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject body = new JSONObject(tokener);
		body.put("email", Variables.EMAIL);
		body.put("w_id", Variables.WORKSPACE_ID);
		body.put("g_id", Variables.GROUP_ID);
		body.put("group_id", Variables.GROUP_ID);

		Response res = Request.POST(API.BaseURI, API.ADD_USER_TO_GROUP, body.toString());
		System.out.println(res.asPrettyString());
		if (res.getStatusCode() != 200) {
			System.out.println("Can't add user to group");
		} else {
			System.out.println("Add user to group successfully!");
		}
	}

}
