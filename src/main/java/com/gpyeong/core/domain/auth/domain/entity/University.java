package com.gpyeong.core.domain.auth.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "universities")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class University extends BaseEntity {
    
    @Id
    private String universityId;

    private String universityName;
    
    @Builder.Default
    private List<String> departmentIds = new ArrayList<>();
    @Builder.Default
    private List<String> memberIds = new ArrayList<>();
}
