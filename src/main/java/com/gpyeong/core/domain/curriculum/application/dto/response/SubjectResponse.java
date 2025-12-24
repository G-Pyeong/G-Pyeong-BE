package com.gpyeong.core.domain.curriculum.application.dto.response;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;

public record SubjectResponse(
        String subjectId,
        String name,
        Integer credit,
        String departmentId,
        SemesterEnum semester,
        String categoryId) {
    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.getSubjectId(),
                subject.getName(),
                subject.getCredit(),
                subject.getDepartmentId(),
                subject.getSemester(),
                subject.getCategoryId());
    }
}
