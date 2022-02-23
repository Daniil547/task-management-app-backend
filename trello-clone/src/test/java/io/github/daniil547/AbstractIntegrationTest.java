package io.github.daniil547;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daniil547.common.dto.DomainDto;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Main.class)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest<D extends DomainDto> {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    private final String endpoint;
    // private final WebMvcTestDataProvider dataProvider;

    public AbstractIntegrationTest(String endpoint/*, WebMvcTestDataProvider dataProvider*/) {
        this.endpoint = endpoint;
        //this.dataProvider = dataProvider;
    }

    public MvcResult testCreate(D dto,
                                ResultMatcher... matchers) throws Exception {
        String jsonContent = objectMapper.writeValueAsString(dto);

        return mockMvc.perform(MockMvcRequestBuilders
                                       .post(endpoint)
                                       .contentType(APPLICATION_JSON)
                                       .content(jsonContent)
                      ).andExpect(status().isCreated())
                      .andExpect(content().contentType(APPLICATION_JSON))
                      .andExpectAll(matchers)
                      .andDo(MockMvcResultHandlers.log())
                      .andReturn();
    }

    public MvcResult testGetById(UUID id,
                                 ResultMatcher... matchers) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                                       .get(endpoint + id.toString())
                      ).andExpect(status().isOk())
                      .andExpect(content().contentType(APPLICATION_JSON))
                      .andExpectAll(matchers)
                      .andDo(MockMvcResultHandlers.log())
                      .andReturn();
    }

    public void testGetAllSearchValidQuery(List<Pair<String, ResultMatcher[]>> searchesAndChecks) {
        List<Executable> asserts = new ArrayList<>();
        for (Pair<String, ResultMatcher[]> searchAndCheck : searchesAndChecks) {
            asserts.add(() -> mockMvc.perform(MockMvcRequestBuilders
                                                      .get(endpoint + "?search=" + searchAndCheck.getFirst())
                                     ).andExpect(status().isOk())
                                     .andExpect(content().contentType(APPLICATION_JSON))
                                     .andExpectAll(searchAndCheck.getSecond())
                                     .andDo(MockMvcResultHandlers.log()));
        }
        assertAll(asserts);
    }


    public MvcResult testUpdate(D dto,
                                ResultMatcher... matchers) throws Exception {
        String jsonContent = objectMapper.writeValueAsString(dto);

        return mockMvc.perform(MockMvcRequestBuilders
                                       .put(endpoint)
                                       .contentType(APPLICATION_JSON)
                                       .content(jsonContent)
                      ).andExpect(status().isOk())
                      .andExpect(content().contentType(APPLICATION_JSON))
                      .andExpectAll(matchers)
                      .andDo(MockMvcResultHandlers.log())
                      .andReturn();
    }

    public void testDelete(UUID id) {
        assertAll(
                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .delete(endpoint + id.toString())
                ).andExpect(status().isNoContent()),
                () -> mockMvc.perform(MockMvcRequestBuilders
                                              .get(endpoint + id.toString())
                ).andExpect(status().isNotFound())
        );
    }
}