package com.gpyeong.core.domain.common.domain.entity;

public enum EssentialTypeEnum {
    REQUIRED("필수"),
    ELECTIVE("선택");

    private final String description;

    EssentialTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
