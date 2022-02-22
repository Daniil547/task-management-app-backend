package io.github.daniil547.user.member;

import io.github.daniil547.AbstractProvider;
import io.github.daniil547.board.Board;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class BoardMemberIntegrationTest extends AbstractMemberIntegrationTest<BoardMember, Board> {
    @Autowired
    public BoardMemberIntegrationTest(AbstractProvider<Board> placeProvider) {
        super("/boards/members/", placeProvider);
    }
}
