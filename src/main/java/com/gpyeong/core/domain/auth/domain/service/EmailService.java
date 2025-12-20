package com.gpyeong.core.domain.auth.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @org.springframework.beans.factory.annotation.Value("${email.from}")
    private String fromAddress;

    public void sendVerificationCode(String to, String verificationCode) {
        String subject = "[지평] 회원가입 이메일 인증";
        String html = ""
                + "<div style=\"font-family:Arial,sans-serif;color:#333;padding:20px;max-width:600px;margin:auto;\">"
                + "  <div style=\"text-align:center;margin-bottom:20px;\">"
                + "    <h1 style=\"margin:0;font-size:24px;color:#1A237E;\">G-Pyeong</h1>"
                + "  </div>"
                + "  <p style=\"font-size:16px;\">안녕하세요!</p>"
                + "  <p style=\"font-size:16px;\">회원가입 인증을 위해 아래 인증 코드를 입력해주세요.</p>"
                + "  <div style=\"background:#f5f5f5;padding:20px;text-align:center;margin:20px 0;border-radius:8px;\">"
                + "    <div style=\"font-size:32px;font-weight:bold;color:#1A237E;letter-spacing:4px;margin:10px 0;\">"
                + verificationCode
                + "    </div>"
                + "  </div>"
                + "  <p style=\"font-size:14px;color:#888;\">이 인증 코드는 5분 후 만료됩니다.</p>"
                + "  <p style=\"font-size:14px;\">요청하지 않으셨다면 고객지원으로 문의해주세요.</p>"
                + "  <hr style=\"border:none;border-top:1px solid #eee;margin:30px 0;\"/>"
                + "  <div style=\"font-size:12px;color:#aaa;text-align:center;\">G-Pyeong Team</div>"
                + "</div>";

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true); // true indicates HTML
            
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", to, e);
            throw new RuntimeException("이메일 전송에 실패했습니다.");
        }
    }
}
