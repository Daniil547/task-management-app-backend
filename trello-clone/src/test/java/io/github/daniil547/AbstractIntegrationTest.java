package io.github.daniil547;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.util.Reflection;
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
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                      .andExpectAll(checkJsonFields(dto))
                      .andExpectAll(matchers)
                      .andDo(MockMvcResultHandlers.log())
                      .andReturn();
    }

    public MvcResult testGetById(D dto,
                                 ResultMatcher... matchers) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                                       .get(endpoint + dto.getId().toString())
                      ).andExpect(status().isOk())
                      .andExpect(content().contentType(APPLICATION_JSON))
                      .andExpect(jsonPath("$.id", is(dto.getId().toString())))
                      .andExpectAll()
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
                      .andExpect(jsonPath("$.id", is(dto.getId().toString())))
                      .andExpectAll(checkJsonFields(dto))
                      .andExpectAll(matchers)
                      .andDo(MockMvcResultHandlers.log())
                      .andReturn();
    }

    public void testDelete(UUID id) {
        assertAll(
                () -> mockMvc.perform(MockMvcRequestBuilders.delete(endpoint + id.toString())
                             ).andExpect(status().isNoContent())
                             .andDo(MockMvcResultHandlers.log()),

                () -> mockMvc.perform(MockMvcRequestBuilders.get(endpoint + id.toString())
                             ).andExpect(status().isNotFound())
                             .andDo(MockMvcResultHandlers.log())
        );
    }

    public static <AnyD extends DomainDto> ResultMatcher[] checkJsonFields(AnyD dto) {
        return checkJsonFields(dto, "$.");
    }

    public static <AnyD extends DomainDto> ResultMatcher[] checkJsonFields(AnyD dto, String prefix) {
        Class<? extends DomainDto> dtoClass = dto.getClass();
        List<String> ignoredProperties = dtoClass.isAnnotationPresent(JsonIgnoreProperties.class)
                                         ? List.of(dtoClass.getAnnotation(JsonIgnoreProperties.class).value())
                                         : Collections.emptyList();

        ArrayList<ResultMatcher> jsonPathChecks = new ArrayList<>();

        ReflectionUtils.doWithMethods(dtoClass, method -> { // method callback
            String methodName = method.getName();
            String propertyName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4); // remove prefix

            if (method.isAnnotationPresent(JsonProperty.class)) {
                propertyName = method.getAnnotation(JsonProperty.class).value();
            }

            if (propertyName.equals("id")) { //id is set by persistence provider, so in some cases we don't know it beforehand
                jsonPathChecks.add(jsonPath("$.id").exists());
            } else {
                Object valueBehindGetter = Reflection.invokeMethod(dto, methodName);
                if (valueBehindGetter == null) return;
                Class<?> propertyClass = method.getReturnType();

                if (Collection.class.isAssignableFrom(propertyClass)) { // check if the property is a collection
                    Class<?> typeOfElements = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
                    int i = 0;
                    if (DomainDto.class.isAssignableFrom(typeOfElements)) { // check if the collection contains DTOs
                        for (DomainDto element : (Collection<DomainDto>) valueBehindGetter) {
                            jsonPathChecks.addAll(List.of(checkJsonFields(element, prefix + propertyName + "[" + i + "].")));
                            i++;
                        }
                        return;
                    }
                }
                if (propertyClass.isEnum() || UUID.class.isAssignableFrom(propertyClass)) { //JsonPath can recognize equal enums and UUIDs as equal only when they are converted to strings
                    valueBehindGetter = valueBehindGetter.toString();
                }
                if (DomainDto.class.isAssignableFrom(propertyClass)) {
                    jsonPathChecks.addAll(List.of(checkJsonFields((DomainDto) valueBehindGetter, prefix + propertyName + ".")));
                } else {
                    System.out.println(propertyName);
                    jsonPathChecks.add(
                            jsonPath(prefix + propertyName,
                                     is(valueBehindGetter)
                            )
                    );
                }

            }
        }, method -> { // method filter
            String methodName = method.getName();
            String nameNoPrefix = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            return methodName.contains("get") && !ignoredProperties.contains(nameNoPrefix) && !methodName.equals("getClass");
        });

        assertFalse(jsonPathChecks.isEmpty());
        return jsonPathChecks.toArray(new ResultMatcher[0]); //according to the stackoverflow passing an empty array is either the same or faster than a pre-sized one
    }
}