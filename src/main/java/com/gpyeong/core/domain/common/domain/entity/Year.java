package com.gpyeong.core.domain.common.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "년도")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Year extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "year_id")
    private Long yearId;
    
    @Column(name = "year_name")
    private Integer yearName;
}
