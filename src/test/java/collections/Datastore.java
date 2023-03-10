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

public class Datastore {
	public static List<String> getTemplateNameDatastore() {
		List<String> listTempDSName = new ArrayList<>();
		String BasePath = API.Data_Store.GET_TEMPLATE_NAME_DATASTORE_V1(Variables.PROJECT_ID, Variables.WORKSPACE_ID);

		Response res = Request.GET(API.BaseURI_V1, BasePath);

		JSONArray bodyRes = new JSONArray(res.getBody().asString());
		for (Object object : bodyRes) {
			listTempDSName.add(object.toString());
		}

		return listTempDSName;
	}

	public static void createDatastoreFromTemplate() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("src/test/java/testData/NewDatastore.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject body = new JSONObject(tokener);
		body.put("user_id", Variables.USER_ID);
		body.put("workspace_id", Variables.WORKSPACE_ID);
		body.put("project_id", Variables.PROJECT_ID);
		body.put("template_name", Variables.TEMPLATE_DATASTORE_ID);

		Response res = Request.POST(API.BaseURI_V1, API.Data_Store.CREATE_DATA_STORE_FROM_TEMPLATE_V1, body.toString());

		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new datastore from template");
		} else {
			System.out.println("Create new datastore from template successfully");
		}

		Variables.DATASTORE_ID = res.getBody().asString().replace("\"", "");
	}

	public static void createNewItemDatastore() throws FileNotFoundException {
		String[] users_to_publish = { Variables.USER_ID };
		// Tao body
		FileInputStream file = new FileInputStream("src/test/java/testData/NewItem.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject body = new JSONObject(tokener);
		JSONObject access_key_updates = body.getJSONObject("access_key_updates");
		access_key_updates.put("users_to_publish", users_to_publish);
		String basePath = API.Data_Store.CREATE_NEW_ITEM(Variables.PROJECT_ID, Variables.DATASTORE_ID);

		Response res = Request.POST(API.BaseURI, basePath, body.toString());
		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't create new Item");
		} else {

			System.out.println("Create new Item successfully");
		}

		JSONObject bodyRes = new JSONObject(res.getBody().asString());
		Variables.ITEM_ID = bodyRes.getString("item_id");
	}

	public static List<String> getListDatastoreID() {
		List<String> listDatastore = new ArrayList<>();
		String basePath = API.Data_Store.GET_LIST_CREATED_DATASTORE(Variables.PROJECT_ID);

		Response res = Request.GET(API.BaseURI, basePath);

		JSONArray arr = new JSONArray(res.getBody().asString());

		for (Object object : arr) {
			JSONObject obj = new JSONObject(object.toString());
			listDatastore.add(obj.getString("datastore_id"));
		}

		return listDatastore;
	}

	public static String getLastDatastoreID() {
		List<String> listDatastore = getListDatastoreID();

		return listDatastore.get(listDatastore.size() - 1);
	}

	public static void updateItemDatastore() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("src/test/java/testData/UpdateItem.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject obj = new JSONObject(tokener);
		obj.put("rev_no", Variables.REV_NO);
		obj.put("action_id", Variables.ACTION_ID);
		System.out.println(obj.toString());

		String basePath = API.Data_Store.UPDATE_ITEM_DATASTORE(Variables.PROJECT_ID, Variables.DATASTORE_ID,
				Variables.ITEM_ID);

		Response res = Request.POST(API.BaseURI, basePath, obj.toString());
		JSONObject resBody = new JSONObject(res.getBody().asString());
		JSONObject rev_no = resBody.getJSONObject("rev_no");
		Variables.REV_NO = rev_no.getInt("rev_no");

	}

	public static Response getDatastoreItemDetails() {
		String basePath = API.Data_Store.GET_DATASTORE_ITEM_DETAILS(Variables.PROJECT_ID, Variables.DATASTORE_ID,
				Variables.ITEM_ID);
		System.out.println(basePath);
		return Request.GET(API.BaseURI, basePath);
	}

	public static int getRevNoItem() {
		JSONObject obj = new JSONObject(getDatastoreItemDetails().getBody().asString());
		return obj.getInt("rev_no");

	}

	public static List<String> getListActionID() {
		List<String> listActionID = new ArrayList<>();
		JSONObject obj = new JSONObject(getDatastoreItemDetails().getBody().asString());
		JSONArray arr = new JSONArray(obj.get("item_actions").toString());
		for (int i = 0; i < arr.length(); i++) {
			JSONObject item_actions = new JSONObject(arr.get(i).toString());
			listActionID.add(item_actions.getString("action_id"));
		}
		return listActionID;

	}

	public static void deleteItemDatastore() {
		JSONObject body = new JSONObject();
		String basePath = API.Data_Store.DELETE_ITEM_DATASTORE(Variables.PROJECT_ID, Variables.DATASTORE_ID,
				Variables.ITEM_ID);
		Response res = Request.DELETE(API.BaseURI, basePath, body.toString());
		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't delete item datastore");
		} else {
			System.out.println("Delete item successfully!");
		}
	}

	public static void deleteAllItemDatastore() {
		JSONObject body = new JSONObject();
		String basePath = API.Data_Store.DELETE_ALL_ITEM_DATASTORE(Variables.PROJECT_ID, Variables.DATASTORE_ID);

		Response res = Request.DELETE(API.BaseURI, basePath, body.toString());
		int statusCode = res.getStatusCode();
		if (statusCode != 200) {
			System.out.println("Can't delete all item datastore");
		} else {
			System.out.println("Delete all item successfully!");
		}
	}

	public static void getListItems() throws FileNotFoundException {
		List<String> listItems = new ArrayList<>();
		FileInputStream file = new FileInputStream("src/test/java/testData/GetListItemBodyRequest.json");
		JSONTokener tokener = new JSONTokener(file);
		JSONObject body = new JSONObject(tokener);
		body.put("project_id", Variables.PROJECT_ID);
		body.put("datastore_id", Variables.DATASTORE_ID);

		Response res = Request.POST(API.BaseURI_V1, API.Data_Store.GET_LIST_ITEMS_V1, body.toString());
		System.out.println(res.getBody().asPrettyString());
		JSONObject resBody = new JSONObject(res.getBody().asPrettyString());
		JSONArray items = resBody.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {
			JSONObject obj = new JSONObject(items.get(i).toString());
			listItems.add(obj.getString("i_id"));
		}
		System.out.println(listItems.toString());
	}

}
