package com.gpyeong.core.domain.auth.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.repository.UserRepository;
import com.gpyeong.core.global.exception.RestApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailVerificationService emailVerificationService;



    @Test
    @DisplayName("회원가입 성공 - 이메일 인증 완료됨")
    void signUpSuccess() {
        // given
        SignUpRequest request = new SignUpRequest(
                "test@gachon.ac.kr",
                "testuser",
                "Password123!",
                "홍길동",
                "컴퓨터공학과",
                1,
                2021,
                3
        );

        given(emailVerificationService.isVerified(request.email())).willReturn(true);
        given(passwordEncoder.encode(request.password())).willReturn("encodedPassword");
        given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        User savedUser = userService.save(request);

        // then
        assertThat(savedUser.getUserId()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@gachon.ac.kr");
        assertThat(savedUser.getName()).isEqualTo("홍길동");
        assertThat(savedUser.getUniversityId()).isEqualTo(1);
        assertThat(savedUser.getDepartment()).isEqualTo("컴퓨터공학과");
        assertThat(savedUser.getYearId()).isEqualTo(2021);
        assertThat(savedUser.getGrade()).isEqualTo(3);
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");

        verify(emailVerificationService).isVerified(request.email());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 미인증")
    void signUpFail_UnverifiedEmail() {
        // given
        SignUpRequest request = new SignUpRequest(
                "test@gachon.ac.kr",
                "testuser",
                "Password123!",
                "홍길동",
                "컴퓨터공학과",
                1,
                2021,
                3
        );

        given(emailVerificationService.isVerified(request.email())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(RestApiException.class)
                .hasMessageContaining("인증이 필요합니다");
    }
}
