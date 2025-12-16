package com.gpyeong.core.domain.auth.ui;

import com.gpyeong.core.domain.auth.application.dto.request.EmailRequest;
import com.gpyeong.core.domain.auth.application.dto.request.EmailVerificationRequest;
import com.gpyeong.core.domain.auth.domain.service.EmailVerificationService;
import com.gpyeong.core.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email Auth", description = "이메일 인증 API")
@RestController
@RequestMapping("/api/auth/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @Operation(summary = "인증 코드 전송", description = "입력한 이메일(@gachon.ac.kr)로 6자리 인증 코드를 전송합니다.")
    @PostMapping("/send")
    public BaseResponse<Void> sendVerificationCode(@RequestBody @Valid EmailRequest request) {
        emailVerificationService.sendCode(request.email());
        return BaseResponse.onSuccess(null);
    }

    @Operation(summary = "인증 코드 확인", description = "이메일로 받은 코드를 입력하여 인증을 완료합니다.")
    @PostMapping("/verify")
    public BaseResponse<Void> verifyCode(@RequestBody @Valid EmailVerificationRequest request) {
        emailVerificationService.verifyCode(request.email(), request.code());
        return BaseResponse.onSuccess(null);
    }
}
