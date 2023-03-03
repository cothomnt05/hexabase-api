package test;

import java.time.LocalDateTime;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Datastore {
	String workspaceName;
	String jaName, enName;
	String lang_cd;

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
		collections.Login.login();
	}

	@Test
	public void TC_01_create_new_datastore_from_template() {
		// Create new workspace
		collections.Workspace.createNewWorkspace(workspaceName);
		String current_workspace_id = collections.Workspace.getCurrentWorkspaceID();

		// Create new project
		String u_id = collections.Workspace.getCurrentUserID();
		String tp_id = collections.Project.getTemplateProject().get(0);

		String projId = collections.Project.createNewProjectFromTemplate(jaName, enName, tp_id, current_workspace_id);

		// Create new datastore from template
//		String project_id = collections.Project.getLastProjID();

		String template_name = collections.Datastore.getTemplateNameDatastore(projId, current_workspace_id).get(0);
		collections.Datastore.createDatastoreFromTemplate(u_id, current_workspace_id, projId, template_name, lang_cd);
	}

	@Test
	public void TC_02_create_and_update_created_item() {
		String current_workspace_id = collections.Workspace.getCurrentWorkspaceID();
		String tp_id = collections.Project.getTemplateProject().get(0);
		System.out.println(
				collections.Project.createNewProjectFromTemplate("testJA", "testEN", tp_id, current_workspace_id));

		System.out.println(collections.Project.getLastProjID());

	}

	@AfterClass
	public static void after() {
		collections.Workspace.archiveWorkspace(collections.Workspace.getCurrentWorkspaceID());

	}

}
