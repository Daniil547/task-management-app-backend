package io.github.daniil547.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@MappedSuperclass
public abstract class Page extends Domain {
    @Column(length = 50)
    private String pageTitle;
    @Column(unique = true,
            nullable = false,
            length = 25)
    private String pageName;
    @Column
    private String pageDescription;
    //That's A title of Our
}
