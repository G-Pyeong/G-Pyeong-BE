package com.gpyeong.core.domain.timetable.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timetables")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Timetable extends BaseEntity {
    
    @Id
    private String timetableId;
    
    private Integer userId; // memberId에서 변경
    
    private String yearId;
    
    private SemesterEnum semester;
    
    private String summary;
    
    private Integer totalCredit;
}
