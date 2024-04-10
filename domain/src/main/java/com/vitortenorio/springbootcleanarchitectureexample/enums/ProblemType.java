package com.vitortenorio.springbootcleanarchitectureexample.enums;

public enum ProblemType {
    INVALID_FIELD("/invalid-field", "Invalid field"),
    USER_ERROR("/user-error", "User error");

    private final String title;
    private final String uri;

    ProblemType(String uri, String title) {
        this.uri = uri;
        this.title = title;
    }

    public String title() {
        return this.title;
    }

    public String uri() {
        return this.uri;
    }
}
