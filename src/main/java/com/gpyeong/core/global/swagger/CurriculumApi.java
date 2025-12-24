package com.gpyeong.core.global.swagger;

import com.gpyeong.core.domain.curriculum.application.dto.response.SectionResponse;
import com.gpyeong.core.domain.curriculum.application.dto.response.SubjectResponse;
import com.gpyeong.core.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "커리큘럼 관리", description = "과목 및 개설 분반 조회 API")
public interface CurriculumApi {

        @Operation(summary = "전체 과목 조회", description = "시스템에 등록된 모든 과목 리스트를 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "조회 성공")
        })
        BaseResponse<List<SubjectResponse>> getAllSubjects();

        @Operation(summary = "과목별 분반 조회", description = "특정 과목에 개설된 모든 분반(Section) 정보를 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "조회 성공"),
                        @ApiResponse(responseCode = "404", description = "과목을 찾을 수 없음", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
        })
        BaseResponse<List<SectionResponse>> getSectionsBySubject(
                        @Parameter(description = "과목 ID", example = "60f7e... (MongoDB ObjectId)") String subjectId);
}