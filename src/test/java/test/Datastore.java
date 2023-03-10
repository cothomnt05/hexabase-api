package test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Variables;

public class Datastore {
	String workspaceName;
	String jaName, enName, groupName;

	@BeforeClass
	public void before() {
		LocalDateTime current = LocalDateTime.now();
		String today = current.getYear() + "_" + current.getMonthValue() + "_" + current.getDayOfMonth()
				+ current.getHour() + "_" + current.getMinute() + "_" + current.getSecond();
		String present = current.getHour() + "_" + current.getMinute() + "_" + current.getSecond();
		workspaceName = "TestAPI" + today;
		jaName = "Project JA" + present;
		enName = "Project EN" + present;
		groupName = "Group" + today;

		// Login
		collections.Login.login();
	}

	@Test
	public void TC_01_create_new_datastore_from_template() throws FileNotFoundException {
		// Create new workspace
		collections.Workspace.createNewWorkspace(workspaceName);
		System.out.println(Variables.WORKSPACE_ID);
		Assert.assertTrue(collections.Workspace.getListWorkspaceName().contains(workspaceName));
		Assert.assertTrue(collections.Workspace.getListWorkspaceID().contains(Variables.WORKSPACE_ID));

		// Create new project
		Variables.USER_ID = collections.Workspace.getCurrentUserID();
		Variables.TEMPLATE_PROJECT_ID = collections.Project.getTemplateProject().get(0);

		collections.Project.createNewProjectFromTemplate(jaName, enName);
		System.out.println(Variables.PROJECT_ID);

		// Create new datastore from template
		Variables.TEMPLATE_DATASTORE_ID = collections.Datastore.getTemplateNameDatastore().get(0);
		collections.Datastore.createDatastoreFromTemplate();

		System.out.println(collections.Datastore.getListDatastoreID().toString());
	}

	@Test
	public void TC_02_create_and_update_created_item() throws FileNotFoundException {
		// Select created datastore
		Variables.DATASTORE_ID = collections.Datastore.getLastDatastoreID();

		// Create new Item
		collections.Datastore.createNewItemDatastore();

		// Update created Item
		Variables.REV_NO = collections.Datastore.getRevNoItem();
		Variables.ACTION_ID = collections.Datastore.getListActionID().get(0);
		collections.Datastore.updateItemDatastore();
	}

	@Test
	public void TC_03_delete_created_item() throws FileNotFoundException {
		// Select created datastore
		Variables.DATASTORE_ID = collections.Datastore.getLastDatastoreID();

		// Create new Item
		Variables.USER_ID = collections.Workspace.getCurrentUserID();
		collections.Datastore.createNewItemDatastore();

		// Delete created Item
		collections.Datastore.deleteItemDatastore();
	}

	@Test
	public void TC_04_creat_new_group_and_add_new_user_to_group() throws FileNotFoundException {
		// Create new group
		Variables.WORKSPACE_ID = collections.Workspace.getCurrentWorkspace();
		Variables.DISPLAY_ID = collections.Project.getDisplayId();
		Variables.PARENT_GROUP_ID = collections.Group.getParentGroupID();
		collections.Group.createGroup(groupName);

		// Add new user to group
		Variables.EMAIL = collections.Workspace.getEmail();
		collections.Group.addUserToGroup();
	}

	@AfterClass
	public static void after() {
		collections.Workspace.archiveWorkspace(collections.Workspace.getCurrentWorkspace());

	}

}
