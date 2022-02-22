package io.github.daniil547.user;

import io.github.daniil547.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest extends AbstractIntegrationTest<UserDto> {
    private UserDto aliceDto;
    private UserDto johnDto;

    public UserIntegrationTest() {
        super("/users/");
    }

    @BeforeAll
    public void init() {
        aliceDto = new UserDto();
        aliceDto.setFirstName("Alice");
        aliceDto.setLastName("Doe");
        aliceDto.setEmail("alice@doe.com");
        aliceDto.setUsername("alice1337");

        johnDto = new UserDto();
        johnDto.setFirstName("John");
        johnDto.setLastName("Doe");
        johnDto.setUsername("john01134");
        johnDto.setEmail("john@doe.com");
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        MvcResult mvcResult = super.testCreate(aliceDto,
                                               jsonPath("$.id").exists(),
                                               jsonPath("$.username", is(aliceDto.getUsername())),
                                               jsonPath("$.firstName", is(aliceDto.getFirstName())),
                                               jsonPath("$.lastName", is(aliceDto.getLastName())),
                                               jsonPath("$.email", is(aliceDto.getEmail())),
                                               jsonPath("$.about").isEmpty()
        );

        aliceDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                          UserDto.class);
        johnDto = objectMapper.readValue(super.testCreate(johnDto).getResponse().getContentAsString(),
                                         UserDto.class);
    }

    @Test
    @Order(2)
    public void testGetAllSearchValidQuery() throws Exception {
        List<String> ids = List.of(aliceDto.getId().toString(), johnDto.getId().toString());

        super.testGetAllSearchValidQuery(List.of(
                Pair.of("lastName:Doe", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", is(in(ids))),
                        jsonPath("$.content.[1].id", is(in(ids)))
                }),
                Pair.of("firstName:Alice,lastName:Doe", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(aliceDto.getId().toString()))
                }),
                Pair.of("firstName:Alice|firstName:John", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", is(in(ids))),
                        jsonPath("$.content.[1].id", is(in(ids)))
                }),
                Pair.of("firstName~_lic_", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(aliceDto.getId().toString()))
                })
        ));
    }

    @Test
    @Order(3)
    public void testGetById() throws Exception {
        super.testGetById(johnDto.getId(),
                          jsonPath("$.id", is(johnDto.getId().toString())),
                          jsonPath("$.username", is(johnDto.getUsername())),
                          jsonPath("$.firstName", is(johnDto.getFirstName())),
                          jsonPath("$.lastName", is(johnDto.getLastName())),
                          jsonPath("$.email", is(johnDto.getEmail())),
                          jsonPath("$.about").isEmpty());
    }

    @Test
    @Order(4)
    public void testUpdate() throws Exception {
        aliceDto.setFirstName("Alicia");
        aliceDto.setLastName("Robertson");
        aliceDto.setUsername("aliciaCool");
        aliceDto.setEmail("alicia@robertson.com");
        aliceDto.setAbout("Lorem ipsum dolor sit amet");

        super.testUpdate(aliceDto, jsonPath("$.id", is(aliceDto.getId().toString())),
                         jsonPath("$.username", is(aliceDto.getUsername())),
                         jsonPath("$.firstName", is(aliceDto.getFirstName())),
                         jsonPath("$.lastName", is(aliceDto.getLastName())),
                         jsonPath("$.email", is(aliceDto.getEmail())),
                         jsonPath("$.about", is(aliceDto.getAbout()))
        );
    }

    @Test
    @Order(5)
    public void testDelete() throws Exception {
        super.testDelete(johnDto.getId());
    }
}
