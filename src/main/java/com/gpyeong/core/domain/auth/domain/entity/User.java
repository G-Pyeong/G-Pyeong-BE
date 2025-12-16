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
    
    private String password;
    
    private String school;
    
    private String department;
    
    private Integer admissionYear;
    
    private Integer grade;
    
    public void updateProfile(String name, String department, Integer admissionYear, Integer grade, String encodedNewPassword) {
        this.name = name;
        this.department = department;
        this.admissionYear = admissionYear;
        this.grade = grade;
        if (encodedNewPassword != null && !encodedNewPassword.isBlank()) {
            this.password = encodedNewPassword;
        }
    }
}

