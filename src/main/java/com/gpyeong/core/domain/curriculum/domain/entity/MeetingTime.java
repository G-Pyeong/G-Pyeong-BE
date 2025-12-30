package com.gpyeong.core.domain.curriculum.domain.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MeetingTime {
    private DayOfWeek dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static MeetingTime of(DayOfWeek dayOfWeek, LocalDateTime startTime, LocalDateTime endTime) {
        return new MeetingTime(
                dayOfWeek,
                startTime,
                endTime);
    }

    // 시간 겹침 확인 로직
    public boolean isOverlapping(MeetingTime other) {
        if (!this.dayOfWeek.equals(other.dayOfWeek)) {
            return false;
        }
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }
}
