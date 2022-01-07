package io.github.daniil547.workspace;

import io.github.daniil547.board.Board;
import io.github.daniil547.common.domain.Page;
import io.github.daniil547.user.member.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.net.URL;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Workspace extends Page {
    private URL companyWebsiteUrl;
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;
    private Set<Member> members;
    private List<Board> boards;

    /*TODO: other methods*/
}
