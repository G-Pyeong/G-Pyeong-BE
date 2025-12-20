package com.gpyeong.core.domain.timetable.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "시간표에_속한_과목")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimetableItem extends BaseEntity {
    
    @Id
    @Column(name = "timetable_item_id", length = 255)
    private String timetableItemId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timetable_id", nullable = false)
    private Timetable timetable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
