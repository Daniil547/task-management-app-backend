package io.github.daniil547.user.member;

import io.github.daniil547.AbstractIntegrationTest;
import io.github.daniil547.AbstractProvider;
import io.github.daniil547.UserProvider;
import io.github.daniil547.common.domain.Page;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public abstract class AbstractMemberIntegrationTest<E extends Member, P extends Page> extends AbstractIntegrationTest<MemberDto> {
    private MemberDto firstMember;
    private MemberDto secondMember;

    private final AbstractProvider<P> placeProvider;
    @Autowired
    private UserProvider userProvider;

    public AbstractMemberIntegrationTest(String endpoint, AbstractProvider<P> placeProvider) {
        super(endpoint);
        this.placeProvider = placeProvider;
    }

    @BeforeAll
    public void init() {
        UUID place = placeProvider.ensureExists().getId();
        UUID user1Id = userProvider.ensureExists().getId();
        UUID user2Id = userProvider.ensureExists().getId();

        firstMember = new MemberDto();
        firstMember.setPlaceId(place);
        firstMember.setUserId(user1Id);
        firstMember.setRole(Role.OWNER);

        secondMember = new MemberDto();
        secondMember.setPlaceId(place);
        secondMember.setUserId(user2Id);
        secondMember.setRole(Role.ADMIN);
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        MvcResult mvcResult = super.testCreate(firstMember,
                                               jsonPath("$.id").exists(),
                                               jsonPath("$.placeId", is(firstMember.getPlaceId().toString())),
                                               jsonPath("$.userId", is(firstMember.getUserId().toString())),
                                               jsonPath("$.role", is(firstMember.getRole().toString())));

        firstMember = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                             MemberDto.class);
        secondMember = objectMapper.readValue(super.testCreate(secondMember).getResponse().getContentAsString(),
                                              MemberDto.class);
    }

    // TODO use dataproviders
    // TODO use jsonpath predicates
    // TODO factor out common code


    @Test
    @Order(2)
    public void testGetAllSearchValidQuery() {
        List<String> ids = List.of(firstMember.getId().toString(),
                                   secondMember.getId().toString());

        super.testGetAllSearchValidQuery(List.of(
                Pair.of(/*TODO*/"placeId:" + firstMember.getPlaceId().toString(), new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", in(ids)),
                        jsonPath("$.content.[1].id", in(ids))
                }),
                Pair.of("userId:" + firstMember.getUserId().toString(), new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(0)))
                })/*,
                TODO Pair.of("role:ADMIN", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(1)))
                })*/
        ));
    }

    @Test
    @Order(3)
    public void testGetById() throws Exception {
        super.testGetById(secondMember);
    }

    @Test
    @Order(4)
    public void testUpdate() throws Exception {
        secondMember.setRole(Role.MEMBER);

        super.testUpdate(firstMember);
    }

    @Test
    @Order(5)
    public void testDelete() {
        super.testDelete(secondMember.getId());
    }
}
