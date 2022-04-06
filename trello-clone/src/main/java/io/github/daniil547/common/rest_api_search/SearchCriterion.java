package io.github.daniil547.common.rest_api_search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchCriterion {
    private String key;
    private SearchOperation operation;
    private Object value;
}