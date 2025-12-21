package com.gpyeong.core.domain.common.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 학년도 정보를 나타내는 엔티티
 * yearId: MongoDB 내부 ID (1, 2, 3...)
 * yearName: 실제 연도 (2024, 2025...)
 */
@Document(collection = "years")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Year extends BaseEntity {
    
    @Id
    private String yearId;
    
    private Integer yearName;
}
