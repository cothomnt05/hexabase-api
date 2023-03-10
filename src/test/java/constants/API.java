package constants;

public class API {
	// Base URI
	public static final String BaseURI = "https://dev-api.hexabase.com";

	public static final String BaseURI_V1 = "https://dev.hexabase.com";

	// Login and Account
	public static final String LOGIN = "/api/v0/login";// get token

	public static final String GET_USER_INFO = "/api/v0/userinfo"; // get currentWorkspaceId, get currentUserId,
																	// userRolesId

	// Workspace
	public static final String CREATE_NEW_WORKSPACE = "/api/v0/workspaces"; // get workspaceId

	public static final String GET_LIST_WORKSPACE = "/api/v0/workspaces";

	public static final String GET_CURRENT_WORKSPACE = "/api/v0/workspacecurrent";

	public static final String ARCHIVE_WORKSPACE = "/v1/api/archive_workspace";

	// Project
	public static final String GET_TEMPLATE_PROJECT = "/api/v0/templates"; // templateId

	public static final String GET_PROJECT_LIST_V1 = "/v1/api/get_applications_list";

	public static final String CREATE_NEW_PROJECT_FROM_TEMPLATE = "/api/v0/applications"; // get projectId

	// Datastore
	public static class Data_Store {

		public static final String GET_TEMPLATE_NAME_DATASTORE_V1(String projectId, String currentWorkspaceId) {
			return "/v1/api/get_templates?p_id=" + projectId + "&w_id=" + currentWorkspaceId;
		}

		public static final String CREATE_DATA_STORE_FROM_TEMPLATE_V1 = "/v1/api/create_datastore_from_template";

		public static final String CREATE_NEW_ITEM(String projectID, String datastoreID) {
			return "/api/v0/applications/" + projectID + "/datastores/" + datastoreID + "/items/new";
		}

		public static final String GET_LIST_CREATED_DATASTORE(String projectID) {
			return "/api/v0/applications/" + projectID + "/datastores";
		}

		// Item
		public static final String GET_DATASTORE_ITEM_DETAILS(String projectID, String datastoreID, String itemID) {
			return "/api/v0/applications/" + projectID + "/datastores/" + datastoreID + "/items/details/" + itemID;
		}

		public static final String UPDATE_ITEM_DATASTORE(String projectID, String datastoreID, String itemID) {
			return "/api/v0/applications/" + projectID + "/datastores/" + datastoreID + "/items/edit/" + itemID;
		}

		public static final String DELETE_ITEM_DATASTORE(String projectID, String datastoreID, String itemID) {
			return "/api/v0/applications/" + projectID + "/datastores/" + datastoreID + "/items/delete/" + itemID;
		}

		public static final String DELETE_ALL_ITEM_DATASTORE(String projectID, String datastoreID) {
			return "/api/v0/applications/" + projectID + "/datastores/" + datastoreID + "/items/delete";
		}

		public static final String GET_LIST_ITEMS_V1 = "v1/api/get_paginate_items_with_search";

	}

	// Group
	public static final String GET_GROUP_CHILDREN = "/api/v0/groups";

	public static final String CREATE_GROUP(String parentGroup_gId) {
		return "/api/v0/groups/" + parentGroup_gId;
	}

	public static final String DELETE_GROUP(String groupID, String workspaceId) {
		return "/api/v0/groups/" + groupID + "?workspace-id=" + workspaceId;
	}

	public static final String ADD_USER_TO_GROUP = "/api/v0/users";

}
