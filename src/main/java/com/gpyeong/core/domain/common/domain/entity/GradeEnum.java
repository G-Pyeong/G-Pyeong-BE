package com.gpyeong.core.domain.common.domain.entity;

public enum GradeEnum {
    FIRST("1학년"),
    SECOND("2학년"),
    THIRD("3학년"),
    FOURTH("4학년");

    private final String description;

    GradeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
