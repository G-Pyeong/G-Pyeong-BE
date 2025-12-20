package com.gpyeong.core.domain.auth.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gpyeong.core.global.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    private String userId;
    
    private String name;
    
    @Indexed(unique = true)
    private String email;
    
    private String providerId;
    
    private OAuthProvider provider;
    
    private Integer universityId;
    
    private String department;
    
    private Integer yearId;
    
    private GradeId gradeId;
    
    public void updateProfile(String name, String department, Integer yearId, GradeId gradeId) {
        this.name = name;
        this.department = department;
        this.yearId = yearId;
        this.gradeId = gradeId;
    }
}

