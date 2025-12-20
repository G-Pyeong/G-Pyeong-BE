package com.gpyeong.core.domain.auth.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "departments")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department extends BaseEntity {
    
    @Id
    private Long departmentId;
    
    private String name;
    
    private Long universityId;
    
    private List<String> memberIds = new ArrayList<>();
}
