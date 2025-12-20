package com.gpyeong.core.domain.member.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "members")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {
    
    @Id
    private String memberId;
    
    private Long universityId;
    
    private Long admissionYearId;
    
    @Indexed(unique = true)
    private String email;
    
    private String name;
    
    private Long departmentId;
    
    private GradeEnum grade;
    
    private OAuthProvider provider;
    
    private String providerId;
}
