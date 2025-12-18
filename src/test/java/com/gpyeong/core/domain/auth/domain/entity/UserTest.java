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
                .password("oldPassword")
                .name("홍길동")
                .universityId(1)
                .department("소프트웨어학과")
                .yearId(2021)
                .grade(3)
                .build();

        String newName = "홍길순";
        String newDepartment = "인공지능학과";
        Integer newYearId = 2022;
        Integer newGrade = 2;
        String newPassword = "newPassword";

        // when
        user.updateProfile(newName, newDepartment, newYearId, newGrade, newPassword);

        // then
        assertThat(user.getName()).isEqualTo(newName);
        assertThat(user.getDepartment()).isEqualTo(newDepartment);
        assertThat(user.getYearId()).isEqualTo(newYearId);
        assertThat(user.getGrade()).isEqualTo(newGrade);
        assertThat(user.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("User 프로필 업데이트 - 비밀번호 미변경")
    void updateProfile_NoPasswordChange() {
        // given
        User user = User.builder()
                .userId("testuser")
                .email("test@gachon.ac.kr")
                .password("oldPassword")
                .name("홍길동")
                .universityId(1)
                .department("컴퓨터공학과")
                .yearId(2021)
                .grade(3)
                .build();

        // when
        user.updateProfile("홍길순", "인공지능학과", 2022, 2, null);

        // then
        assertThat(user.getPassword()).isEqualTo("oldPassword");
    }
}
