package test;

import java.time.LocalDateTime;
import java.util.Collections;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Datastore {
	String workspaceName;
	String jaName, enName;
	String lang_cd;
	String token;

	@BeforeClass
	public void before() {
		LocalDateTime current = LocalDateTime.now();
		String today = current.getYear() + "_" + current.getMonthValue() + "_" + current.getDayOfMonth()
				+ current.getHour() + "_" + current.getMinute() + "_" + current.getSecond();
		String present = current.getHour() + "_" + current.getMinute() + "_" + current.getSecond();
		workspaceName = "TestAPI" + today;
		jaName = "Project JA" + present;
		enName = "Project EN" + present;
		lang_cd = "ja";
		// Login
		token = collections.Login.login();
	}

//	@Test
	public void TC_01_create_new_datastore_from_template() {
		// Create new workspace
		collections.Workspace.createNewWorkspace(token, workspaceName);
		String current_workspace_id = collections.Workspace.getCurrentWorkspaceID(token);

		// Create new project
		String u_id = collections.Workspace.getCurrentUserID(token);
		String tp_id = collections.Project.getTemplateProject(token).get(0);

		String projId = collections.Project.createNewProjectFromTemplate(token, jaName, enName, tp_id,
				current_workspace_id);

		// Create new datastore from template
		String project_id = collections.Project.getLastProjID(token);

		String template_name = collections.Datastore.getTemplateNameDatastore(token, projId, current_workspace_id)
				.get(0);
		System.out.println("template name: " + template_name);
		collections.Datastore.createDatastoreFromTemplate(token, u_id, current_workspace_id, projId, template_name,
				lang_cd);
	}

	@Test
	public void TC_02_create_and_update_created_item() {
		String current_workspace_id = collections.Workspace.getCurrentWorkspaceID(token);
		String tp_id = collections.Project.getTemplateProject(token).get(0);
		System.out.println(collections.Project.createNewProjectFromTemplate(token, "testJA", "testEN", tp_id,
				current_workspace_id));

		System.out.println(collections.Project.getLastProjID(token));

	}

}
