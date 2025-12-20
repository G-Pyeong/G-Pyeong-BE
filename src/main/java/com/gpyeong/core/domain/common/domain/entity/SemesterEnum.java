package com.gpyeong.core.domain.common.domain.entity;

public enum SemesterEnum {
    FIRST_SEMESTER("1학기"),
    SECOND_SEMESTER("2학기");

    private final String description;

    SemesterEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
