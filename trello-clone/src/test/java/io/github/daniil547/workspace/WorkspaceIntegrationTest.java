package io.github.daniil547.workspace;

import io.github.daniil547.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class WorkspaceIntegrationTest extends AbstractIntegrationTest<WorkspaceDto> {

    private WorkspaceDto myWorkspace;
    private WorkspaceDto hisWorkspace;
    @Autowired
    WorkspaceRepository repo;

    public WorkspaceIntegrationTest() {
        super("/workspaces/");
    }

    @BeforeAll
    public void init() {
        myWorkspace = new WorkspaceDto();
        myWorkspace.setPageName("myWorkspace");
        myWorkspace.setPageTitle("My workspace");
        myWorkspace.setVisibility(WorkspaceVisibility.PRIVATE);
        myWorkspace.setCompanyWebsiteUrl("https://my-company.com");
        hisWorkspace = new WorkspaceDto();
        hisWorkspace.setPageName("hisWorkspace");
        hisWorkspace.setPageTitle("His workspace");
        hisWorkspace.setVisibility(WorkspaceVisibility.PRIVATE);
        hisWorkspace.setCompanyWebsiteUrl("https://his-company.com");
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        MvcResult mvcResult = super.testCreate(myWorkspace,
                                               jsonPath("$.id").exists(),
                                               jsonPath("$.pageName", is(myWorkspace.getPageName())),
                                               jsonPath("$.pageTitle", is(myWorkspace.getPageTitle())),
                                               jsonPath("$.companyWebsiteUrl", is(myWorkspace.getCompanyWebsiteUrl())),
                                               jsonPath("$.visibility", is(myWorkspace.getVisibility().toString())),
                                               jsonPath("$.pageDescription").isEmpty());

        myWorkspace = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                             WorkspaceDto.class);
        hisWorkspace = objectMapper.readValue(super.testCreate(hisWorkspace).getResponse().getContentAsString(),
                                              WorkspaceDto.class);
    }


    @Test
    @Order(2)
    public void testGetAllSearchInvalidQuery() throws Exception {

        assertAll(
                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get("/workspaces/?search=")
                             ).andExpect(status().isBadRequest())
                             .andDo(MockMvcResultHandlers.log()),

                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get("/workspaces/?search=asd:dsa")
                             ).andExpect(status().isBadRequest())
                             .andDo(MockMvcResultHandlers.log()),

                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get("/workspaces/?search=pageName")
                             ).andExpect(status().isBadRequest())
                             .andDo(MockMvcResultHandlers.log()),

                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get("/workspaces/?search=pageName:,pageDescription:Doe")
                             ).andExpect(status().isBadRequest())
                             .andDo(MockMvcResultHandlers.log()),

                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get("/workspaces/?search=pageName:pageDescription:Doe")
                             ).andExpect(status().isBadRequest())
                             .andDo(MockMvcResultHandlers.log()),

                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get("/workspaces/?search=pageName:Bob,pageDescription:Doe|")
                             ).andExpect(status().isBadRequest())
                             .andDo(MockMvcResultHandlers.log())
        );
    }

    @Test
    @Order(3)
    public void testGetAllSearchValidQuery() {
        List<String> ids = List.of(myWorkspace.getId().toString(),
                                   hisWorkspace.getId().toString());

        super.testGetAllSearchValidQuery(List.of(
                Pair.of("pageName:myWorkspace", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(0)))
                }),
                Pair.of("pageName:myWorkspace,pageTitle~___workspace", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(0)))
                }),
                Pair.of("pageName:myWorkspace|pageName:hisWorkspace", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", in(ids)),
                        jsonPath("$.content.[1].id", in(ids))
                })
        ));
    }

    @Test
    @Order(4)
    public void testGetById() throws Exception {
        super.testGetById(hisWorkspace.getId(),
                          jsonPath("$.id", is(hisWorkspace.getId().toString())),
                          jsonPath("$.pageName", is(hisWorkspace.getPageName())),
                          jsonPath("$.pageTitle", is(hisWorkspace.getPageTitle())),
                          jsonPath("$.companyWebsiteUrl", is(hisWorkspace.getCompanyWebsiteUrl())),
                          jsonPath("$.visibility", is(hisWorkspace.getVisibility().toString())),
                          jsonPath("$.pageDescription").isEmpty());
    }

    @Test
    @Order(5)
    public void testUpdate() throws Exception {
        myWorkspace.setPageTitle("NEW my workspace");
        myWorkspace.setPageName("NEWmyWorkspace");
        myWorkspace.setVisibility(WorkspaceVisibility.PUBLIC);
        myWorkspace.setPageDescription("Lorem ipsum dolor sit amet");

        super.testUpdate(myWorkspace, jsonPath("$.id", is(myWorkspace.getId().toString())),
                         jsonPath("$.pageName", is(myWorkspace.getPageName())),
                         jsonPath("$.pageTitle", is(myWorkspace.getPageTitle())),
                         jsonPath("$.companyWebsiteUrl", is(myWorkspace.getCompanyWebsiteUrl())),
                         jsonPath("$.visibility", is(myWorkspace.getVisibility().toString())),
                         jsonPath("$.pageDescription", is(myWorkspace.getPageDescription()))
        );
    }

    @Test
    @Order(6)
    public void testDelete() throws Exception {
        super.testDelete(hisWorkspace.getId());
    }
}