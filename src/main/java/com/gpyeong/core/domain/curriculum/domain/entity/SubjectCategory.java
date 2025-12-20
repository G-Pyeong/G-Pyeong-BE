package com.gpyeong.core.domain.curriculum.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "과목_계열")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectCategory extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private SubjectCategory parentCategory;
    
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectCategory> childCategories = new ArrayList<>();
    
    @OneToMany(mappedBy = "subjectCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();
    
    public void setParentCategory(SubjectCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
    
    public void addChildCategory(SubjectCategory childCategory) {
        childCategories.add(childCategory);
        childCategory.setParentCategory(this);
    }
    
    public void addSubject(Subject subject) {
        subjects.add(subject);
        subject.setSubjectCategory(this);
    }
}
