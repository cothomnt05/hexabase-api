package collections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import constants.API;
import constants.Variables;
import io.restassured.response.Response;

public class Project {
	public static List<String> getTemplateProject() {
		List<String> listTempProject = new ArrayList<String>();

		// Call request get Template Project
		Response res = Request.GET(constants.API.BaseURI, constants.API.GET_TEMPLATE_PROJECT);

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

	public static void createNewProjectFromTemplate(String jaName, String enName) throws FileNotFoundException {
		// Tao body
		FileInputStream file = new FileInputStream("src/test/java/testData/NewProject.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject obj = new JSONObject(tokener);
		JSONObject name = obj.getJSONObject("name");
		name.put("ja", jaName);
		name.put("en", enName);

		obj.put("tp_id", Variables.TEMPLATE_PROJECT_ID);
		obj.put("workspace_id", Variables.WORKSPACE_ID);
		System.out.println(obj.toString());

		// Call Request POST create project
		Response res = Request.POST(constants.API.BaseURI, constants.API.CREATE_NEW_PROJECT_FROM_TEMPLATE,
				obj.toString());

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new project");
		} else {
			System.out.println("Create new project successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());
		Variables.PROJECT_ID = bodyRes.getString("project_id");
	}

	public static Response getListProject() {
		return Request.GET(API.BaseURI_V1, API.GET_PROJECT_LIST_V1);
	}

	public static String getCreatedProjectID() {
		System.out.println(getListProject().getBody().asPrettyString());
		JSONArray arrRes = new JSONArray(getListProject().getBody().asString());
		return arrRes.getJSONObject(1).get("application_id").toString();
	}

	public static String getDisplayId() {
		JSONArray arrRes = new JSONArray(getListProject().getBody().asString());
		return arrRes.getJSONObject(1).get("display_id").toString();
	}

}
