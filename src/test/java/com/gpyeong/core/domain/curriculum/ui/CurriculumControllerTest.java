package com.gpyeong.core.domain.curriculum.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpyeong.core.domain.common.domain.entity.SemesterEnum;
import com.gpyeong.core.domain.curriculum.application.dto.response.SectionResponse;
import com.gpyeong.core.domain.curriculum.application.dto.response.SubjectResponse;
import com.gpyeong.core.domain.curriculum.application.usecase.CurriculumUseCase;
import com.gpyeong.core.domain.curriculum.domain.entity.DayOfWeek;
import com.gpyeong.core.global.interceptor.JwtBlacklistInterceptor;
import com.gpyeong.core.global.security.ExcludeBlacklistPathProperties;
import com.gpyeong.core.global.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurriculumController.class)
class CurriculumControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CurriculumUseCase curriculumUseCase;

        @MockitoBean
        private TokenProvider tokenProvider;
        @MockitoBean
        private ExcludeBlacklistPathProperties excludeBlacklistPathProperties;
        @MockitoBean
        private JwtBlacklistInterceptor jwtBlacklistInterceptor;

        @Test
        @DisplayName("전체 과목 조회 API Test")
        @WithMockUser
        void getAllSubjects() throws Exception {
                // given
                given(jwtBlacklistInterceptor.preHandle(
                                org.mockito.ArgumentMatchers.any(),
                                org.mockito.ArgumentMatchers.any(),
                                org.mockito.ArgumentMatchers.any())).willReturn(true);

                given(excludeBlacklistPathProperties.getExcludeAuthPaths())
                                .willReturn(List.of("/api/curriculum/**"));

                List<SubjectResponse> responses = List.of(
                                new SubjectResponse("sub1", "Math", 3, "dept1", SemesterEnum.FIRST_SEMESTER, "cat1"));
                given(curriculumUseCase.getAllSubjects()).willReturn(responses);

                // when & then
                mockMvc.perform(get("/api/curriculum/subjects")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.result[0].subjectId").value("sub1"))
                                .andExpect(jsonPath("$.result[0].name").value("Math"));
        }

        @Test
        @DisplayName("과목별 분반 조회 API Test")
        @WithMockUser
        void getSectionsBySubject() throws Exception {
                // given
                given(jwtBlacklistInterceptor.preHandle(
                                org.mockito.ArgumentMatchers.any(),
                                org.mockito.ArgumentMatchers.any(),
                                org.mockito.ArgumentMatchers.any())).willReturn(true);

                given(excludeBlacklistPathProperties.getExcludeAuthPaths())
                                .willReturn(List.of("/api/curriculum/**"));

                String subjectId = "sub1";
                SectionResponse.MeetingTimeResponse meetingTime = new SectionResponse.MeetingTimeResponse(
                                DayOfWeek.MON, LocalTime.of(9, 0), LocalTime.of(10, 30));
                List<SectionResponse> responses = List.of(
                                new SectionResponse("sec1", subjectId, "001", "Prof. Kim", "2024",
                                                SemesterEnum.FIRST_SEMESTER,
                                                List.of(meetingTime)));
                given(curriculumUseCase.getSectionsBySubject(subjectId)).willReturn(responses);

                // when & then
                mockMvc.perform(get("/api/curriculum/subjects/{subjectId}/sections", subjectId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.result[0].id").value("sec1"))
                                .andExpect(jsonPath("$.result[0].meetingTimes[0].dayOfWeek").value("MON"));
        }
}
