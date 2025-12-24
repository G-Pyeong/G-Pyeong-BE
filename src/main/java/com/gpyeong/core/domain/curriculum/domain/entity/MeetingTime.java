package com.gpyeong.core.domain.curriculum.domain.entity;

import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MeetingTime {
    private String dayOfWeek; // MON, TUE, WED ....
    private LocalTime startTime;
    private LocalTime endTime;

    public static MeetingTime of(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
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
