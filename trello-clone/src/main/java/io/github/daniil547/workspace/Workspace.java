package io.github.daniil547.workspace;

import io.github.daniil547.board.Board;
import io.github.daniil547.common.domain.Page;
import io.github.daniil547.user.member.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.net.URL;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Workspace extends Page {
    private URL companyWebsiteUrl;
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;
    private Set<Member> members;
    private List<Board> boards;

    /*TODO: other methods*/
}
