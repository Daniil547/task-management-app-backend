package io.github.daniil547.common.rest_api_search;

public enum SearchOperation {
    EQUALITY, INEQUALITY, GREATER_THAN, LESS_THAN, LIKE;

    public static final String[] SIMPLE_OPERATION_SET = {":", "!", ">", "<", "~"};

    public static SearchOperation fromString(String str) {
        return switch (str) {
            case ":" -> EQUALITY;
            case "!" -> INEQUALITY;
            case ">" -> GREATER_THAN;
            case "<" -> LESS_THAN;
            case "~" -> LIKE;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }
}
