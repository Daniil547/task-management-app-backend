package io.github.daniil547.domain;

import lombok.Data;

import java.net.URL;
import java.util.List;
import java.util.Set;

@Data
public class Workspace extends NestedPage {
    private URL companyWebsiteUrl;
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;
    private Set<Member> members;
    private List<Board> boards;

    /*TODO: other methods*/
}
