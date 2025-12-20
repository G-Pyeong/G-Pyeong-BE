package com.gpyeong.core.domain.member.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "대학")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class University extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long universityId;
    
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();
    
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();
    
    public void addDepartment(Department department) {
        departments.add(department);
        department.setUniversity(this);
    }
    
    public void addMember(Member member) {
        members.add(member);
        member.setUniversity(this);
    }
}
