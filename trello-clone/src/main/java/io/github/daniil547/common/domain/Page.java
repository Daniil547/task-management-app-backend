package io.github.daniil547.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Page extends Domain {
    private String pageTitle;
    private String pageName;
    private String pageDescription;
}
