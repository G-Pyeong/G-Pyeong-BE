package com.gpyeong.core.domain.member.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import com.gpyeong.core.domain.common.domain.entity.Year;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "유저")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {
    
    @Id
    @Column(name = "user_id", length = 255)
    private String userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id", nullable = false)
    private Year admissionYear;
    
    @Column(name = "email", length = 255)
    private String email;
    
    @Column(name = "name", length = 255)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private GradeEnum grade;
    
    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;
    
    @Column(name = "provider_id", length = 255)
    private String providerId;
    
    public void setUniversity(University university) {
        this.university = university;
    }
    
    public void setAdmissionYear(Year admissionYear) {
        this.admissionYear = admissionYear;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
}
