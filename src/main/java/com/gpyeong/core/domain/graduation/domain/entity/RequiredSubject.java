package com.gpyeong.core.domain.graduation.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.common.domain.entity.GradeEnum;
import com.gpyeong.core.domain.common.domain.entity.EssentialTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "졸업요건에_속한_과목")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequiredSubject extends BaseEntity {
    
    @Id
    @Column(name = "required_subject_id", length = 255)
    private String requiredSubjectId;
    
    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private GradeEnum grade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private GraduationRequirement requirement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @Column(name = "essential_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EssentialTypeEnum essentialType;
    
    public void setGrade(GradeEnum grade) {
        this.grade = grade;
    }
    
    public void setRequirement(GraduationRequirement requirement) {
        this.requirement = requirement;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public void setEssentialType(EssentialTypeEnum essentialType) {
        this.essentialType = essentialType;
    }
}
