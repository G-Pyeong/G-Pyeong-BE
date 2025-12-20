package com.gpyeong.core.domain.member.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "universities")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class University extends BaseEntity {
    
    @Id
    private Long universityId;
    
    private List<Long> departmentIds = new ArrayList<>();
    private List<String> memberIds = new ArrayList<>();
}
