package com.gpyeong.core.global.swagger;

import com.gpyeong.core.domain.auth.application.dto.request.TokenReissueRequest;
import com.gpyeong.core.domain.auth.application.dto.response.LoginResponse;
import com.gpyeong.core.domain.auth.application.dto.response.TokenReissueResponse;
import com.gpyeong.core.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 관련 API 인터페이스
 */
@Tag(name = "인증 관리", description = "OAuth 로그인, 로그아웃, 토큰 관리")
public interface AuthApi {

    @Operation(
        summary = "구글 로그인 시작",
        description = "구글 OAuth2 로그인 페이지로 리다이렉트합니다. <br><b>주의:</b> Swagger의 'Try it out' 버튼은 CORS 제한으로 인해 작동하지 않습니다. <br>직접 브라우저 주소창에 <a href='/api/users/login/google'>/api/users/login/google</a>을 입력하여 접속하세요."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "302",
            description = "구글 로그인 페이지로 리다이렉트"
        )
    })
    void googleLogin(HttpServletResponse response) throws IOException;

    @Operation(
        summary = "로그인 성공 확인 (서버 전용 테스트용)",
        description = "OAuth2 로그인 성공 후 토큰을 확인하기 위한 서버 전용 엔드포인트입니다. <br>개발 시 프론트엔드가 없는 경우 'application-secret.yml'의 'redirect-url'을 이 주소로 설정하여 사용하세요."
    )
    BaseResponse<LoginResponse> loginSuccess(
        String accessToken,
        String refreshToken,
        Boolean isNewUser
    );

    @Operation(
        summary = "로그아웃",
        description = "현재 사용자를 로그아웃하고 access token을 화이트리스트에서 제거합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "로그아웃 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증 실패",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        )
    })
    BaseResponse<?> logout(HttpServletRequest request);
    
    @Operation(
        summary = "토큰 재발급",
        description = "refresh token을 사용하여 새로운 access token을 발급받습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "토큰 재발급 성공",
            content = @Content(schema = @Schema(implementation = TokenReissueResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 refresh token",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "만료된 refresh token",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))
        )
    })
    BaseResponse<TokenReissueResponse> reissueToken(TokenReissueRequest request);
}
