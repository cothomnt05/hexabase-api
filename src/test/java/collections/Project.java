package collections;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public class Project {
	public static List<String> getTemplateProject() {
		List<String> listTempProject = new ArrayList<String>();

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

	public static String createNewProjectFromTemplate(String jaName, String enName, String tp_id, String workspace_id) {
		JSONObject body = new JSONObject();
		JSONObject name = new JSONObject();
		name.put("ja", jaName);
		name.put("en", enName);
		body.put("name", name);
		body.put("tp_id", tp_id);
		body.put("workspace_id", workspace_id);

		Response res = Request.POST(constants.API.BaseURI, constants.API.CREATE_NEW_PROJECT_FROM_TEMPLATE,
				body.toString());

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new project");
		} else {
			System.out.println("Create new project successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());
		return bodyRes.getString("project_id");
	}

	public static JSONObject getLastProj() {
		Response res = Request.GET(constants.API.BaseURI_V1, constants.API.GET_LAST_PROJECT_ID_V1);

		JSONArray bodyArr = new JSONArray(res.getBody().asString());
		System.out.println(bodyArr);

		return (JSONObject) bodyArr.get(bodyArr.length() - 1);
	}

	public static String getLastProjID() {
		return getLastProj().get("application_id").toString();
	}

}
