package com.gpyeong.core.domain.auth.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.GradeEnum;

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
    
    // 유저 시퀀스 관리를 위한 이름
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    @lombok.Setter
    private Integer userId;
    
    private String name;
    
    @Indexed(unique = true)
    private String email;
    
    private String providerId;
    
    private OAuthProvider provider;
    
    private Integer universityId;
    
    private String department;
    
    private Integer yearId;
    
    private GradeEnum grade;
    
    private Gender gender;
    
    public void updateProfile(String name, String department, Integer yearId, GradeEnum grade, Gender gender) {
        this.name = name;
        this.department = department;
        this.yearId = yearId;
        this.grade = grade;
        this.gender = gender;
    }
}

