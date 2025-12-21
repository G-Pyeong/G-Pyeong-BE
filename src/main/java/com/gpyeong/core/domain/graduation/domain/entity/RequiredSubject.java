package com.gpyeong.core.domain.graduation.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import com.gpyeong.core.domain.common.domain.entity.EssentialTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "required_subjects")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequiredSubject extends BaseEntity {
    
    @Id
    private String requiredSubjectId;
    
    private GradeEnum grade;
    
    private Long requirementId;
    
    private String subjectId;
    
    private EssentialTypeEnum essentialType;
}
