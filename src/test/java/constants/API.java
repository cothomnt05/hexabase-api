package constants;

public class API {
	public static final String BaseURI = "https://dev-api.hexabase.com";

	public static final String BaseURI_V1 = "https://dev.hexabase.com";

	public static final String LOGIN = "/api/v0/login";// get token

	public static final String GET_USER_INFO = "/api/v0/userinfo"; // get currentWorkspaceId, get currentUserId,
																	// userRolesId

	public static final String CREATE_NEW_WORKSPACE = "/api/v0/workspaces"; // get workspaceId

	public static final String GET_TEMPLATE_PROJECT = "/api/v0/templates"; // templateId

	public static final String GET_LAST_PROJECT_ID_V1 = "/v1/api/get_applications_list"; // get last projectId

	public static final String CREATE_NEW_PROJECT_FROM_TEMPLATE = "/api/v0/applications"; // get projectId

	public static class Data_Store {

		public static final String GET_TEMPLATE_NAME_DATASTORE_V1 = "/v1/api/get_templates?p_id=<projectId>&w_id=<currentWorkspaceId>";

		public static final String CREATE_DATA_STORE_FROM_TEMPLATE_V1 = "/v1/api/create_datastore_from_template";

		public static final String CREATE_NEW_ITEM = "/api/v0/applications/:project-id/datastores/:datastore-id/items/new";

	}

}
