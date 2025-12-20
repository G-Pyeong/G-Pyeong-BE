package com.gpyeong.core.domain.common.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "years")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Year extends BaseEntity {
    
    @Id
    private Long yearId;
    
    private Integer yearName;
}
