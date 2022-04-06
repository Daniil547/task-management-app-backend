package io.github.daniil547.workspace;

import io.github.daniil547.common.domain.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "workspaces")
@AttributeOverride(name = "pageDescription",
                   column = @Column(length = 1000))
public class Workspace extends Page {
    @Column(length = 75)
    private String companyWebsiteUrl;

    @Column(columnDefinition = "enum('PUBLIC', 'PRIVATE')")
    @Enumerated(EnumType.STRING)
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;
}
