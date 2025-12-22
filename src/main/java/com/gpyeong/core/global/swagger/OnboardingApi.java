package com.gpyeong.core.global.swagger;

import com.gpyeong.core.domain.auth.application.dto.request.SignUpRequest;
import com.gpyeong.core.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 온보딩 관련 API 인터페이스
 */
@Tag(name = "온보딩", description = "신규 사용자 추가 정보 입력 및 이메일 인증 완료 API")
public interface OnboardingApi {

    @Operation(
        summary = "온보딩 완료",
        description = "소셜 로그인 후 대학, 학과 등 추가 정보를 입력하여 회원가입을 최종 완료합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "온보딩 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 (유효성 검증 실패)",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증 실패 (JWT 토큰 누락 또는 만료)",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        )
    })
    BaseResponse<Void> completeOnboarding(Integer userId, SignUpRequest request);
}
