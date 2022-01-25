package io.github.daniil547.workspace;

import io.github.daniil547.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkspaceTest extends BaseTest {
    public static final DefaultWorkspaceService WORKSPACE_SERVICE = new DefaultWorkspaceService(
            new WorkspaceJdbcRepository(dataSource)
    );

    private static final String PAGE_NAME = "workspace123";
    private static final String WORKSPACE_TITLE = "Smth Workspace";
    private static final String COMPANY_WEBSITE_URL = "https://example.com";
    private static Workspace workspace;

    private static final UUID DUMMY_ID = UUID.fromString("123e4567-e89b-12d3-a456-434414174961");

    @Test
    @Order(1)
    public void createNew() {

        workspace = WORKSPACE_SERVICE.create(WORKSPACE_TITLE, PAGE_NAME, "");

        assertAll(
                () -> assertNotNull(workspace),
                () -> assertEquals(PAGE_NAME, workspace.getPageName()),
                () -> assertEquals(WORKSPACE_TITLE, workspace.getPageTitle()),
                () -> assertEquals(WorkspaceVisibility.PRIVATE, workspace.getVisibility())
                 );
    }

    @Test
    @Order(2)
    public void createConflicting() {
        assertThrows(DuplicateKeyException.class,
                     () -> WORKSPACE_SERVICE.create(WORKSPACE_TITLE, PAGE_NAME, ""));
    }

    @Test
    @Order(3)
    public void updateExisting() {
        String newPageName = "newname987";
        String newTitle = "New Name";
        String newDescr = "somethingSomethingNew";
        WorkspaceVisibility newVisibility = WorkspaceVisibility.PUBLIC;


        workspace.setPageName(newPageName);
        workspace.setPageTitle(newTitle);
        workspace.setPageDescription(newDescr);
        workspace.setVisibility(newVisibility);

        workspace = WORKSPACE_SERVICE.update(workspace);

        assertAll(
                () -> assertEquals(newPageName, workspace.getPageName()),
                () -> assertEquals(newVisibility, workspace.getVisibility()),
                () -> assertEquals(newTitle, workspace.getPageTitle()),
                () -> assertEquals(newDescr, workspace.getPageDescription())
                 );
    }

    @Test
    @Order(4)
    public void updateNonExisting() {
        UUID actualId = workspace.getId();
        workspace.setId(DUMMY_ID);

        assertThrows(IllegalStateException.class, () -> WORKSPACE_SERVICE.update(workspace));

        workspace.setId(actualId);
    }

    @Test
    @Order(5)
    public void getExistingById() {
        assertEquals(workspace, WORKSPACE_SERVICE.getById(workspace.getId()).get());
    }

    @Test
    @Order(6)
    public void getNonExistingById() {
        assertEquals(Optional.empty(), WORKSPACE_SERVICE.getById(DUMMY_ID));
    }

    @Test
    @Order(7)
    public void getAll() {
        //this is a new one, as we have updated the previous one
        Workspace temp = WORKSPACE_SERVICE.create(WORKSPACE_TITLE, PAGE_NAME, "");
        List<Workspace> allUsers = WORKSPACE_SERVICE.getAll();
        assertAll(
                () -> assertTrue(allUsers.contains(workspace)),
                () -> assertTrue(allUsers.contains(temp))
                 );
    }

    @Test
    @Order(8)
    public void deleteById() {
        WORKSPACE_SERVICE.deleteById(workspace.getId());
        assertEquals(Optional.empty(), WORKSPACE_SERVICE.getById(workspace.getId()));
    }
}
