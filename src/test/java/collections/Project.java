package collections;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Project {
	public static List<String> getTemplateProject(String token) {
		List<String> listTempProject = new ArrayList<String>();
		Response res = RestAssured.given().baseUri(constants.API.BaseURI).basePath(constants.API.GET_TEMPLATE_PROJECT)
				.header("Content-Type", "application/json").auth().oauth2(token).when().get();

		JSONObject bodyRes = new JSONObject(res.getBody().asString());
		JSONArray categories = bodyRes.getJSONArray("categories");
		JSONObject categorieEx = new JSONObject(categories.get(0).toString());
		JSONArray templates = categorieEx.getJSONArray("templates");

		for (Object object : templates) {
			JSONObject obj = new JSONObject(object.toString());
			listTempProject.add(obj.get("tp_id").toString());
		}
		return listTempProject;
	}

	public static String createNewProjectFromTemplate(String token, String jaName, String enName, String tp_id,
			String workspace_id) {
		JSONObject body = new JSONObject();
		JSONObject name = new JSONObject();
		name.put("ja", jaName);
		name.put("en", enName);
		body.put("name", name);
		body.put("tp_id", tp_id);
		body.put("workspace_id", workspace_id);
		System.out.println(body.toString());

		Response res = RestAssured.given().baseUri(constants.API.BaseURI)
				.basePath(constants.API.CREATE_NEW_PROJECT_FROM_TEMPLATE).header("Content-Type", "application/json")
				.auth().oauth2(token).body(body.toString()).when().post();

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new project");
		} else {
			System.out.println("Create new project successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());
		System.out.println(bodyRes.toString());
		return bodyRes.getString("project_id");
	}

	public static JSONObject getLastProj(String token) {
		Response res = RestAssured.given().baseUri(constants.API.BaseURI_V1)
				.basePath(constants.API.GET_LAST_PROJECT_ID_V1).header("Content-Type", "application/json").auth()
				.oauth2(token).when().get();

		JSONArray bodyArr = new JSONArray(res.getBody().asString());
		System.out.println(bodyArr);

		return (JSONObject) bodyArr.get(bodyArr.length() - 1);
	}

	public static String getLastProjID(String token) {
		return getLastProj(token).get("application_id").toString();
	}

}
