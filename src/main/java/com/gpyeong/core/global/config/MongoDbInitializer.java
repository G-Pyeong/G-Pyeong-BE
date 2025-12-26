package com.gpyeong.core.global.config;

import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.domain.entity.MeetingTime;
import com.gpyeong.core.domain.curriculum.domain.entity.DayOfWeek;
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
                // 개발/테스트용: 기존 데이터 초기화 후 다시 삽입
                subjectRepository.deleteAll();
                sectionRepository.deleteAll();

                if (subjectRepository.count() == 0) {
                        log.info("시드 데이터 삽입 중...");

                        // 1. 운영체제
                        Subject os = Subject.builder()
                                        .subjectId("subject-os-001")
                                        .name("운영체제")
                                        .credit(3)
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .categoryId("CAT_COMPUTER_SCIENCE")
                                        .build();
                        Subject savedOs = subjectRepository.save(os);

                        Section osSection1 = Section.builder()
                                        .subjectId(savedOs.getSubjectId())
                                        .sectionNumber("01")
                                        .professor("홍길동 교수")
                                        .yearId("2024")
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .meetingTimes(List.of(
                                                        MeetingTime.of(DayOfWeek.MON, LocalTime.parse("09:00"),
                                                                        LocalTime.parse("10:30")),
                                                        MeetingTime.of(DayOfWeek.WED, LocalTime.parse("09:00"),
                                                                        LocalTime.parse("10:30"))))
                                        .build();
                        sectionRepository.save(osSection1);

                        Section osSection2 = Section.builder()
                                        .subjectId(savedOs.getSubjectId())
                                        .sectionNumber("02")
                                        .professor("김철수 교수")
                                        .yearId("2024")
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .meetingTimes(List.of(
                                                        MeetingTime.of(DayOfWeek.TUE, LocalTime.parse("13:30"),
                                                                        LocalTime.parse("15:00")),
                                                        MeetingTime.of(DayOfWeek.THU, LocalTime.parse("13:30"),
                                                                        LocalTime.parse("15:00"))))
                                        .build();
                        sectionRepository.save(osSection2);

                        // 2. 데이터베이스
                        Subject db = Subject.builder()
                                        .subjectId("subject-db-001")
                                        .name("데이터베이스")
                                        .credit(3)
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .categoryId("CAT_COMPUTER_SCIENCE")
                                        .build();
                        Subject savedDb = subjectRepository.save(db);

                        Section dbSection = Section.builder()
                                        .subjectId(savedDb.getSubjectId())
                                        .sectionNumber("01")
                                        .professor("이영희 교수")
                                        .yearId("2024")
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .meetingTimes(List.of(
                                                        MeetingTime.of(DayOfWeek.FRI, LocalTime.parse("10:00"),
                                                                        LocalTime.parse("13:00"))))
                                        .build();
                        sectionRepository.save(dbSection);

                        // 3. 알고리즘
                        Subject algo = Subject.builder()
                                        .subjectId("subject-algo-001")
                                        .name("알고리즘")
                                        .credit(3)
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .categoryId("CAT_COMPUTER_SCIENCE")
                                        .build();
                        Subject savedAlgo = subjectRepository.save(algo);

                        Section algoSection = Section.builder()
                                        .subjectId(savedAlgo.getSubjectId())
                                        .sectionNumber("01")
                                        .professor("박지성 교수")
                                        .yearId("2024")
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .meetingTimes(List.of(
                                                        MeetingTime.of(DayOfWeek.MON, LocalTime.parse("15:00"),
                                                                        LocalTime.parse("16:30")),
                                                        MeetingTime.of(DayOfWeek.WED, LocalTime.parse("15:00"),
                                                                        LocalTime.parse("16:30"))))
                                        .build();
                        sectionRepository.save(algoSection);

                        // 4. 교양 (인문학) - 채움 알고리즘 테스트용
                        Subject history = Subject.builder()
                                        .subjectId("subject-history-001")
                                        .name("역사의 이해")
                                        .credit(2)
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .categoryId("CAT_HUMANITIES") // 인문학 카테고리
                                        .build();
                        Subject savedHistory = subjectRepository.save(history);

                        Section historySection = Section.builder()
                                        .subjectId(savedHistory.getSubjectId())
                                        .sectionNumber("01")
                                        .professor("최역사 교수")
                                        .yearId("2024")
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .meetingTimes(List.of(
                                                        MeetingTime.of(DayOfWeek.FRI, LocalTime.parse("14:00"),
                                                                        LocalTime.parse("16:00"))))
                                        .build();
                        sectionRepository.save(historySection);

                        // 5. 교양 (예술) - 채움 알고리즘 테스트용
                        Subject art = Subject.builder()
                                        .subjectId("subject-art-001")
                                        .name("음악의 이해")
                                        .credit(2)
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .categoryId("CAT_ARTS") // 예술 카테고리
                                        .build();
                        Subject savedArt = subjectRepository.save(art);

                        Section artSection = Section.builder()
                                        .subjectId(savedArt.getSubjectId())
                                        .sectionNumber("01")
                                        .professor("김음악 교수")
                                        .yearId("2024")
                                        .semester(SemesterEnum.FIRST_SEMESTER)
                                        .meetingTimes(List.of(
                                                        MeetingTime.of(DayOfWeek.TUE, LocalTime.parse("10:00"),
                                                                        LocalTime.parse("12:00"))))
                                        .build();
                        sectionRepository.save(artSection);

                        log.info("시드 데이터 삽입 완료!");
                }
        }
}