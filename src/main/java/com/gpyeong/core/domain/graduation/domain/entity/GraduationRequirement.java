package com.gpyeong.core.domain.graduation.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "graduation_requirements")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraduationRequirement extends BaseEntity {
    
    @Id
    private Long requirementId;
    
    private Integer minCredit;
    
    private Integer minCourseCount;
    
    private Long categoryId;
    
    private Long yearId;
    
    private Long departmentId;
    
    private List<String> requiredSubjectIds = new ArrayList<>();
}
