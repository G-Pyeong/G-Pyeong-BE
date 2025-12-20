package com.gpyeong.core.domain.auth.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.domain.auth.domain.entity.GradeId;
import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("온보딩 완료 - 사용자 추가 정보 업데이트")
    void completeOnboarding() {
        // given
        User existingUser = User.builder()
                .userId("testuser")
                .email("test@gachon.ac.kr")
                .name("Google Name")
                .providerId("google-12345")
                .provider(OAuthProvider.GOOGLE)
                .build();

        SignUpRequest request = new SignUpRequest(
                "홍길동",
                "컴퓨터공학과",
                1,
                2021,
                GradeId.THIRD
        );

        given(userRepository.findByUserId("testuser"))
                .willReturn(java.util.Optional.of(existingUser));
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        User updatedUser = userService.completeOnboarding("testuser", request);

        // then
        assertThat(updatedUser.getName()).isEqualTo("홍길동");
        assertThat(updatedUser.getUniversityId()).isEqualTo(1);
        assertThat(updatedUser.getDepartment()).isEqualTo("컴퓨터공학과");
        assertThat(updatedUser.getYearId()).isEqualTo(2021);
        assertThat(updatedUser.getGradeId()).isEqualTo(GradeId.THIRD);
        assertThat(updatedUser.getProviderId()).isEqualTo("google-12345");
        assertThat(updatedUser.getProvider()).isEqualTo(OAuthProvider.GOOGLE);

        verify(userRepository).save(any(User.class));
    }
}
