package com.gpyeong.core.domain.curriculum.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.member.domain.entity.Department;
import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.graduation.domain.entity.RequiredSubject;
import com.gpyeong.core.domain.timetable.domain.entity.TimetableItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "과목")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "credit", nullable = false)
    private Integer credit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    
    @Column(name = "semester", nullable = false)
    @Enumerated(EnumType.STRING)
    private SemesterEnum semester;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SubjectCategory subjectCategory;
    
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequiredSubject> requiredSubjects = new ArrayList<>();
    
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimetableItem> timetableItems = new ArrayList<>();
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public void setSubjectCategory(SubjectCategory subjectCategory) {
        this.subjectCategory = subjectCategory;
    }
    
    public void addRequiredSubject(RequiredSubject requiredSubject) {
        requiredSubjects.add(requiredSubject);
        requiredSubject.setSubject(this);
    }
    
    public void addTimetableItem(TimetableItem timetableItem) {
        timetableItems.add(timetableItem);
        timetableItem.setSubject(this);
    }
}
