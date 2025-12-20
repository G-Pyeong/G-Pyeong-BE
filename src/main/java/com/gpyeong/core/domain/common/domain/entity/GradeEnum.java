package com.gpyeong.core.domain.common.domain.entity;

public enum GradeEnum {
    FIRST_YEAR("1학년"),
    SECOND_YEAR("2학년"),
    THIRD_YEAR("3학년"),
    FOURTH_YEAR("4학년");

    private final String description;

    GradeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
