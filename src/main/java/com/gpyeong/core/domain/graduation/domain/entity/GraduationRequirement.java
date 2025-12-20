package com.gpyeong.core.domain.graduation.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.curriculum.domain.entity.SubjectCategory;
import com.gpyeong.core.domain.member.domain.entity.Department;
import com.gpyeong.core.domain.common.domain.entity.Year;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "졸업요건")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraduationRequirement extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long requirementId;
    
    @Column(name = "min_credit")
    private Integer minCredit;
    
    @Column(name = "min_course_count")
    private Integer minCourseCount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SubjectCategory subjectCategory;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id", nullable = false)
    private Year year;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    
    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequiredSubject> requiredSubjects = new ArrayList<>();
    
    public void setSubjectCategory(SubjectCategory subjectCategory) {
        this.subjectCategory = subjectCategory;
    }
    
    public void setYear(Year year) {
        this.year = year;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public void addRequiredSubject(RequiredSubject requiredSubject) {
        requiredSubjects.add(requiredSubject);
        requiredSubject.setRequirement(this);
    }
}
