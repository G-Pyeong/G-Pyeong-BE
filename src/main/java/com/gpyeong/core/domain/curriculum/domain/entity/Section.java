package com.gpyeong.core.domain.curriculum.domain.entity;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.global.common.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sections")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Section extends BaseEntity {

    @Id
    private String id;

    private String subjectId;      // 참조하는 과목 ID
    private String sectionNumber;  // 분반 번호
    private String professor;      // 담당 교수명

    private String yearId;         // 개설 연도 ID
    private SemesterEnum semester; // 개설 학기

    private List<MeetingTime> meetingTimes; // 임베딩된 수업 시간들


    // 다른 분반과 시간 겹침 확인
    public boolean isConflict(Section other) {
        return this.meetingTimes.stream()
                .anyMatch(thisTime -> other.meetingTimes.stream()
                        .anyMatch(thisTime::isOverlapping));
    }
}