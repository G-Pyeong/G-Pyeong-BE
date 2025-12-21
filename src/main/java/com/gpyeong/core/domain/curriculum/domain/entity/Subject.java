package com.gpyeong.core.domain.curriculum.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "subjects")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject extends BaseEntity {
    
    @Id
    private String subjectId;
    
    private String name;
    
    private Integer credit;
    
    private String departmentId;
    
    private SemesterEnum semester;
    
    private String categoryId;
    
    @Builder.Default
    private List<String> requiredSubjectIds = new ArrayList<>();
    
    @Builder.Default
    private List<String> timetableItemIds = new ArrayList<>();
}
