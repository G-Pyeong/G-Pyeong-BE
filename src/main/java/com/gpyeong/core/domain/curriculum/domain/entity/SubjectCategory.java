package com.gpyeong.core.domain.curriculum.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "subject_categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectCategory extends BaseEntity {
    
    @Id
    private String categoryId;
    
    private String name;
    
    private String parentCategoryId;
    
    @Builder.Default
    private List<String> childCategoryIds = new ArrayList<>();
    
    @Builder.Default
    private List<String> subjectIds = new ArrayList<>();
}
