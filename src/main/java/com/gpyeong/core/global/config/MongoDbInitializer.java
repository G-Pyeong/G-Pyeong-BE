package com.gpyeong.core.global.config;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.MeetingTime;
import com.gpyeong.core.domain.curriculum.domain.entity.Section;
import com.gpyeong.core.domain.curriculum.domain.entity.Subject;
import com.gpyeong.core.domain.curriculum.domain.repository.SectionRepository;
import com.gpyeong.core.domain.curriculum.domain.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.time.LocalTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MongoDbInitializer implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;
    private final SubjectRepository subjectRepository;
    private final SectionRepository sectionRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing MongoDB collections...");

        // 1. 컬렉션 생성 로직
        List.of("years", "users", "universities", "departments", "subjects",
                "subject_categories", "timetables", "timetable_items",
                "graduation_requirements", "required_subjects", "sections")
                .forEach(this::createCollectionIfNotExists);

        log.info("MongoDB initialization completed!");

        // 2. 시드 데이터 삽입
        initSeedData();
    }

    private void createCollectionIfNotExists(String collectionName) {
        try {
            if (!mongoTemplate.collectionExists(collectionName)) {
                mongoTemplate.createCollection(collectionName);
                log.info("Created collection: {}", collectionName);
            }
        } catch (Exception e) {
            log.warn("Error creating collection {}: {}", collectionName, e.getMessage());
        }
    }

    private void initSeedData() {
        if (subjectRepository.count() == 0) {
            log.info("시드 데이터 삽입 중...");

            // 과목 생성
            Subject os = Subject.builder()
                    .name("운영체제")
                    .credit(3)
                    .semester(SemesterEnum.FIRST_SEMESTER)
                    .build();
            Subject savedOs = subjectRepository.save(os);

            // 분반 생성
            Section osSection = Section.builder()
                    .subjectId(savedOs.getSubjectId()) // Subject 엔티티 필드명 확인
                    .sectionNumber("01")
                    .professor("홍길동 교수")
                    .yearId("2024")
                    .semester(SemesterEnum.FIRST_SEMESTER)
                    .meetingTimes(List.of(
                            MeetingTime.of("MON", LocalTime.parse("09:00"), LocalTime.parse("10:30")),
                            MeetingTime.of("WED", LocalTime.parse("09:00"), LocalTime.parse("10:30"))))
                    .build();

            sectionRepository.save(osSection);
            log.info("시드 데이터 삽입 완료!");
        }
    }
}