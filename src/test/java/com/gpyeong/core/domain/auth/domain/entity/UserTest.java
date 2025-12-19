package com.gpyeong.core.domain.auth.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("User 프로필 업데이트 테스트")
    void updateProfile() {
        // given
        User user = User.builder()
                .userId("testuser")
                .email("test@gachon.ac.kr")
                .name("홍길동")
                .providerId("google-12345")
                .provider(OAuthProvider.GOOGLE)
                .universityId(1)
                .department("소프트웨어학과")
                .yearId(2021)
                .gradeId(GradeId.THIRD)
                .build();

        String newName = "홍길순";
        String newDepartment = "인공지능학과";
        Integer newYearId = 2022;
        GradeId newGradeId = GradeId.SECOND;

        // when
        user.updateProfile(newName, newDepartment, newYearId, newGradeId);

        // then
        assertThat(user.getName()).isEqualTo(newName);
        assertThat(user.getDepartment()).isEqualTo(newDepartment);
        assertThat(user.getYearId()).isEqualTo(newYearId);
        assertThat(user.getGradeId()).isEqualTo(newGradeId);
    }

    @Test
    @DisplayName("User OAuth 로그인 - 신규 사용자 생성")
    void createOAuthUser() {
        // given & when
        User user = User.builder()
                .userId("testuser")
                .email("test@gachon.ac.kr")
                .name("홍길동")
                .providerId("google-12345")
                .provider(OAuthProvider.GOOGLE)
                // universityId, department, yearId, gradeId는 온보딩에서 설정
                .build();

        // then
        assertThat(user.getProviderId()).isEqualTo("google-12345");
        assertThat(user.getProvider()).isEqualTo(OAuthProvider.GOOGLE);
        assertThat(user.getUniversityId()).isNull(); // 온보딩 전
        assertThat(user.getGradeId()).isNull(); // 온보딩 전
    }
}
