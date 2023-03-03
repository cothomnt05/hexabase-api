package collections;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public class Datastore {
	public static List<String> getTemplateNameDatastore(String project_id, String currentWorkspaceId) {
		List<String> listTempDSName = new ArrayList<>();
		String BasePath = constants.API.Data_Store.GET_TEMPLATE_NAME_DATASTORE_V1.replace("<projectId>", project_id)
				.replace("<currentWorkspaceId>", currentWorkspaceId);

		Response res = Request.GET(constants.API.BaseURI_V1, BasePath);

		JSONArray bodyRes = new JSONArray(res.getBody().asString());
		for (Object object : bodyRes) {
			listTempDSName.add(object.toString());
		}

		return listTempDSName;
	}

	public static String createDatastoreFromTemplate(String user_id, String workspace_id, String project_id,
			String template_name, String lang_cd) {
		JSONObject body = new JSONObject();
		body.put("user_id", user_id);
		body.put("workspace_id", workspace_id);
		body.put("project_id", project_id);
		body.put("template_name", template_name);
		body.put("lang_cd", lang_cd);

		Response res = Request.POST(constants.API.BaseURI_V1,
				constants.API.Data_Store.CREATE_DATA_STORE_FROM_TEMPLATE_V1, body.toString());

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new datastore from template");
		} else {
			System.out.println("Create new datastore from template successfully");
		}

		return res.getBody().asString();

	}

	public static JSONObject getLastDatastoreOfLastProj() {
		JSONArray datastoreArr = new JSONArray(Project.getLastProj().get("datastores").toString());
		return (JSONObject) datastoreArr.get(datastoreArr.length() - 1);
	}

	public static String getLastDatastoreOfLastProjID() {
		return getLastDatastoreOfLastProj().get("d_id").toString();
	}

}
