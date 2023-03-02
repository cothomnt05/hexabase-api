package collections;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Datastore {
	public static List<String> getTemplateNameDatastore(String token, String project_id, String currentWorkspaceId) {
		List<String> listTempDSName = new ArrayList<>();
		String BasePath = constants.API.Data_Store.GET_TEMPLATE_NAME_DATASTORE_V1.replace("<projectId>", project_id)
				.replace("<currentWorkspaceId>", currentWorkspaceId);

		Response res = RestAssured.given().baseUri(constants.API.BaseURI_V1).basePath(BasePath)
				.header("Content-Type", "application/json").auth().oauth2(token).when().get();

		JSONArray bodyRes = new JSONArray(res.getBody().asString());
		for (Object object : bodyRes) {
			listTempDSName.add(object.toString());
		}

		return listTempDSName;
	}

	public static String createDatastoreFromTemplate(String token, String user_id, String workspace_id,
			String project_id, String template_name, String lang_cd) {
		JSONObject body = new JSONObject();
		body.put("user_id", user_id);
		body.put("workspace_id", workspace_id);
		body.put("project_id", project_id);
		body.put("template_name", template_name);
		body.put("lang_cd", lang_cd);

		Response res = RestAssured.given().baseUri(constants.API.BaseURI_V1)
				.basePath(constants.API.Data_Store.CREATE_DATA_STORE_FROM_TEMPLATE_V1)
				.header("Content-Type", "application/json").auth().oauth2(token).body(body.toString()).when().post();

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new datastore from template");
		} else {
			System.out.println("Create new datastore from template successfully");
		}
		System.out.println(res.getBody().asString());

		return res.getBody().asString();

	}

//	public static JSONObject getLastDatastoreOfLastProj(String token) {
//		JSONArray datastoreArr = new JSONArray(Project.getLastProj(token).get("datastores").toString());
//		return (JSONObject) datastoreArr.get(datastoreArr.length() - 1);
//	}

//	public static String getLastDatastoreOfLastProjID(String token) {
//		return getLastDatastoreOfLastProj(token).get("d_id").toString();
//	}

}
