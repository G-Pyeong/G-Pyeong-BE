package com.gpyeong.core.domain.timetable.domain.entity;

import com.gpyeong.core.global.common.BaseEntity;
import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.common.domain.entity.Year;
import com.gpyeong.core.domain.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "시간표")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Timetable extends BaseEntity {
    
    @Id
    @Column(name = "timetable_id", length = 255)
    private String timetableId;
    
    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id", nullable = false)
    private Year year;
    
    @Column(name = "semester", nullable = false)
    @Enumerated(EnumType.STRING)
    private SemesterEnum semester;
    
    @Column(name = "summary")
    private Integer summary;
    
    @Column(name = "total_credit")
    private Integer totalCredit;
    
    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimetableItem> timetableItems = new ArrayList<>();
    
    public void setYear(Year year) {
        this.year = year;
    }
    
    public void addTimetableItem(TimetableItem timetableItem) {
        timetableItems.add(timetableItem);
        timetableItem.setTimetable(this);
    }
}
