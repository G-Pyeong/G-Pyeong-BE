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
@Table(name = "학과")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();
    
    public void setUniversity(University university) {
        this.university = university;
    }
    
    public void addMember(Member member) {
        members.add(member);
        member.setDepartment(this);
    }
}
