package com.gpyeong.core.domain.timetable.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "timetables")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Timetable extends BaseEntity {
    
    @Id
    private String timetableId;
    
    private String memberId;
    
    private Long yearId;
    
    private SemesterEnum semester;
    
    private Integer summary;
    
    private Integer totalCredit;
    
    private List<String> timetableItemIds = new ArrayList<>();
}
