package com.gpyeong.core.domain.timetable.domain.service;

import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import com.gpyeong.core.domain.curriculum.domain.repository.SubjectRepository;
import com.gpyeong.core.domain.timetable.application.dto.request.TimetableGenerationRequest;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.exception.code.status.GlobalErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableGenerationService {

    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;

    private static final int SCORE_DAY_OFF = 50;
    private static final int SCORE_NO_MORNING = 30;
    private static final LocalTime MORNING_LIMIT = LocalTime.of(10, 0);

    public List<Section> generateAlgorithm(TimetableGenerationRequest request) {
        log.info("Starting timetable generation for user with request: {}", request);

        // 필수 과목들의 모든 분반 조회
        List<List<Section>> requiredSubjectGroups = new ArrayList<>();
        if (request.requiredSubjectIds() != null) {
            for (String subjectId : request.requiredSubjectIds()) {
                List<Section> sections = sectionRepository.findBySubjectId(subjectId);
                if (sections.isEmpty()) {
                    log.error("No sections found for subjectId: {}", subjectId);
                    throw new RestApiException(GlobalErrorStatus._BAD_REQUEST);
                }
                log.info("Found {} sections for subjectId: {}", sections.size(), subjectId);
                requiredSubjectGroups.add(sections);
            }
        }

        // 백트래킹 탐색 (최적 조합 찾기)
        List<Section> bestCombination = new ArrayList<>();
        int[] maxScore = { -1 };

        backtrack(0, new ArrayList<>(), requiredSubjectGroups, request, bestCombination, maxScore);

        if (bestCombination.isEmpty()) {
            log.error("Failed to find any valid timetable combination for required subjects.");
            throw new RestApiException(GlobalErrorStatus._BAD_REQUEST);
        }

        // 교양 과목 자동 채움 (Beam Search)
        if (request.targetCredits() != null && request.preferredCategoryIds() != null) {
            fillElectives(bestCombination, request);
        }

        return bestCombination;
    }

    private void backtrack(int depth, List<Section> current,
            List<List<Section>> groups,
            TimetableGenerationRequest request,
            List<Section> bestCombination, int[] maxScore) {

        // Base Case: 모든 과목을 선택함
        if (depth == groups.size()) {
            int score = calculateScore(current, request);
            if (score > maxScore[0]) {
                maxScore[0] = score;
                bestCombination.clear();
                bestCombination.addAll(new ArrayList<>(current));
            }
            return;
        }

        // Recursive Step
        List<Section> candidates = groups.get(depth);

        for (Section section : candidates) {
            if (!isConflict(current, section)) {
                current.add(section);
                backtrack(depth + 1, current, groups, request, bestCombination, maxScore);
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean isConflict(List<Section> current, Section target) {
        return current.stream().anyMatch(existing -> existing.isConflict(target));
    }

    private int calculateScore(List<Section> timetable, TimetableGenerationRequest request) {
        int score = 0;

        // 공강일 확인
        List<String> classDays = timetable.stream()
                .flatMap(s -> s.getMeetingTimes().stream())
                .map(mt -> mt.getDayOfWeek().name())
                .distinct()
                .toList();

        if (request.preferredDayOffs() != null) {
            for (String dayOff : request.preferredDayOffs()) {
                if (!classDays.contains(dayOff))
                    score += SCORE_DAY_OFF;
            }
        }

        // 오전 수업 회피
        if (request.avoidMorning()) {
            boolean hasMorning = timetable.stream()
                    .flatMap(s -> s.getMeetingTimes().stream())
                    .anyMatch(mt -> mt.getStartTime().isBefore(MORNING_LIMIT));
            if (!hasMorning)
                score += SCORE_NO_MORNING;
        }

        // 밀집도 (Compactness) 점수 (우주공강 패널티)
        var meetingsByDay = timetable.stream()
                .flatMap(s -> s.getMeetingTimes().stream())
                .collect(Collectors
                        .groupingBy(com.gpyeong.core.domain.curriculum.domain.entity.MeetingTime::getDayOfWeek));

        int totalGapMinutes = 0;
        for (var entry : meetingsByDay.entrySet()) {

            List<com.gpyeong.core.domain.curriculum.domain.entity.MeetingTime> meetings = new ArrayList<>(
                    entry.getValue());
            meetings.sort((m1, m2) -> m1.getStartTime().compareTo(m2.getStartTime()));

            for (int i = 0; i < meetings.size() - 1; i++) {
                LocalTime endCurrent = meetings.get(i).getEndTime();
                LocalTime startNext = meetings.get(i + 1).getStartTime();

                long gap = java.time.Duration.between(endCurrent, startNext).toMinutes();
                if (gap > 0) {
                    totalGapMinutes += gap;
                }
            }
        }

        // 30분 공강당 1점 감점
        score -= (totalGapMinutes / 30);

        return score;
    }

    public int calculateTotalCredits(List<Section> sections) {
        List<String> subjectIds = sections.stream()
                .map(Section::getSubjectId)
                .distinct()
                .collect(Collectors.toList());

        List<Subject> subjects = subjectRepository.findAllById(subjectIds);

        return subjects.stream()
                .mapToInt(Subject::getCredit)
                .sum();
    }

    // 교양 과목 자동 채움 (Beam Search, K=5)
    private void fillElectives(List<Section> bestCombination, TimetableGenerationRequest request) {
        int targetCredits = request.targetCredits() != null ? request.targetCredits() : 0;
        int BEAM_WIDTH = 5;

        // 선호 카테고리의 과목 및 분반 조회 (Batch)
        List<Subject> allCandidates = subjectRepository.findByCategoryIdIn(request.preferredCategoryIds());
        List<String> allCandidateIds = allCandidates.stream().map(Subject::getSubjectId).toList();
        List<Section> allCandidateSections = sectionRepository.findBySubjectIdIn(allCandidateIds);

        java.util.Map<String, Integer> subjectCreditMap = allCandidates.stream()
                .collect(Collectors.toMap(Subject::getSubjectId, Subject::getCredit));

        // 기존 필수 과목들의 학점 정보도 맵에 추가 필요 (calculateTotalCredits에서 사용됨)
        List<String> currentSubjectIds = bestCombination.stream().map(Section::getSubjectId).toList();
        List<Subject> currentSubjects = subjectRepository.findAllById(currentSubjectIds);
        currentSubjects.forEach(s -> subjectCreditMap.putIfAbsent(s.getSubjectId(), s.getCredit()));

        List<List<Section>> beam = new ArrayList<>();
        beam.add(new ArrayList<>(bestCombination));

        boolean changed = true;
        while (changed) {
            changed = false;
            List<List<Section>> nextBeam = new ArrayList<>();

            for (List<Section> currentTimetable : beam) {
                int currentCredits = calculateTotalCreditsOptimized(currentTimetable, subjectCreditMap);
                if (currentCredits >= targetCredits) {
                    nextBeam.add(currentTimetable); // 이미 목표 달성한 경우 유지
                    continue;
                }

                // 이미 선택된 과목 ID 식별
                List<String> usedSubjectIds = currentTimetable.stream()
                        .map(Section::getSubjectId)
                        .toList();

                // 확장 가능한 후보군 탐색
                for (Subject subject : allCandidates) {
                    // 이미 듣고 있는 과목은 제외
                    if (usedSubjectIds.contains(subject.getSubjectId()))
                        continue;

                    // 해당 과목의 분반들 시도
                    List<Section> sectionsOfSubject = allCandidateSections.stream()
                            .filter(s -> s.getSubjectId().equals(subject.getSubjectId()))
                            .toList();

                    for (Section section : sectionsOfSubject) {
                        if (!isConflict(currentTimetable, section)) {
                            // 새로운 시간표 후보 생성
                            List<Section> newTimetable = new ArrayList<>(currentTimetable);
                            newTimetable.add(section);
                            nextBeam.add(newTimetable);
                            changed = true;
                        }
                    }
                }
            }

            if (!changed)
                break;

            // 평가 및 Beam Cutting
            // 1순위: 학점 목표 오차(Diff) 최소화 (Math.abs(current - target))
            // 2순위: 시간표 점수(Score) 최대화
            nextBeam.sort((t1, t2) -> {
                int cred1 = calculateTotalCreditsOptimized(t1, subjectCreditMap);
                int cred2 = calculateTotalCreditsOptimized(t2, subjectCreditMap);
                int diff1 = Math.abs(cred1 - targetCredits);
                int diff2 = Math.abs(cred2 - targetCredits);

                if (diff1 != diff2) {
                    return Integer.compare(diff1, diff2); // 오차 작은 순
                } else {
                    return Integer.compare(calculateScore(t2, request), calculateScore(t1, request)); // 점수 높은 순

                }
            });

            if (nextBeam.size() > BEAM_WIDTH) {
                beam = nextBeam.subList(0, BEAM_WIDTH);
            } else {
                beam = nextBeam;
            }
        }

        // 최종 선택: Beam 중 가장 좋은 것 선택
        if (!beam.isEmpty()) {
            beam.sort((t1, t2) -> {
                int cred1 = calculateTotalCreditsOptimized(t1, subjectCreditMap);
                int cred2 = calculateTotalCreditsOptimized(t2, subjectCreditMap);
                int diff1 = Math.abs(cred1 - targetCredits);
                int diff2 = Math.abs(cred2 - targetCredits);

                if (diff1 != diff2) {
                    return Integer.compare(diff1, diff2);
                } else {
                    return Integer.compare(calculateScore(t2, request), calculateScore(t1, request));
                }
            });

            List<Section> finalBest = beam.get(0);
            bestCombination.clear();
            bestCombination.addAll(finalBest);
        }
    }

    private int calculateTotalCreditsOptimized(List<Section> sections, java.util.Map<String, Integer> creditMap) {
        return sections.stream()
                .map(Section::getSubjectId)
                .distinct()
                .mapToInt(id -> creditMap.getOrDefault(id, 0)) // Cache hit expected
                .sum();
    }
}
