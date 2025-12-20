package com.gpyeong.core.domain.timetable.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timetable_items")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimetableItem extends BaseEntity {
    
    @Id
    private String timetableItemId;
    
    private String timetableId;
    
    private Long subjectId;
}
