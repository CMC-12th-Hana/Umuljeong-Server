package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.TaskCategoryConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.TaskCategoryException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.TaskCategoryService;
import cmc.hana.umuljeong.util.MemberUtil;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.validation.annotation.ExistTaskCategory;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;
import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "TaskCategory API", description = "업무 카테고리 조회, 추가, 수정, 삭제")
@Validated
@RestController
@RequiredArgsConstructor
public class TaskCategoryRestController {

    private final TaskCategoryService taskCategoryService;

    @Operation(summary = "[006_02 & 006_02.1]", description = "업무 카테고리 목록 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = TaskCategoryResponseDto.TaskCategoryListDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/{companyId}/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.TaskCategoryListDto> getTaskCategoryList(@PathVariable(name = "companyId") @ExistCompany Long companyId, @AuthUser Member member) {
        if(companyId != member.getCompany().getId()) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        List<TaskCategory> taskCategoryList = taskCategoryService.findByCompany(companyId);
        return ResponseEntity.ok(TaskCategoryConverter.toTaskCategoryListDto(taskCategoryList));
    }

    @Operation(summary = "[006_02.2]", description = "업무 카테고리 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = TaskCategoryResponseDto.CreateTaskCategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PostMapping("/company/{companyId}/client/business/task/category")
    public ResponseEntity<TaskCategoryResponseDto.CreateTaskCategoryDto> createTaskCategory(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestBody @Valid TaskCategoryRequestDto.CreateTaskCategoryDto request, @AuthUser Member member) {
        if(companyId != member.getCompany().getId()) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        TaskCategory taskCategory = taskCategoryService.create(companyId, request);
        return ResponseEntity.ok(TaskCategoryConverter.toCreateTaskCategoryDto(taskCategory));
    }

    @Operation(summary = "[006_02.3]", description = "업무 카테고리 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CREATED : 정상응답", content = @Content(schema = @Schema(implementation = TaskCategoryResponseDto.UpdateTaskCategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 업무카테고리가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : categoryId에 해당하는 업무 카테고리가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/client/business/task/category/{categoryId}")
    public ResponseEntity<TaskCategoryResponseDto.UpdateTaskCategoryDto> updateTaskCategory(@PathVariable(name = "categoryId") @ExistTaskCategory Long taskCategoryId, @RequestBody @Valid TaskCategoryRequestDto.UpdateTaskCategoryDto request, @AuthUser Member member) {
        TaskCategory taskCategory = taskCategoryService.update(taskCategoryId, request);
        if(!member.getCompany().getTaskCategoryList().contains(taskCategory)) throw new TaskCategoryException(ErrorCode.TASK_CATEGORY_ACCESS_DENIED);
        return ResponseEntity.ok(TaskCategoryConverter.toUpdateTaskCategory(taskCategory));
    }

    @Operation(summary = "[006_02.4]", description = "업무 카테고리 삭제")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CREATED : 정상응답", content = @Content(schema = @Schema(implementation = TaskCategoryResponseDto.DeleteTaskCategoryListDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 삭제 요청 id들 중 본인 회사의 업무카테고리가 아닌 것이 있는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @DeleteMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.DeleteTaskCategoryListDto> deleteTaskCategory(@RequestParam(name = "categoryIds") List<Long> categoryIds, @AuthUser Member member) {
        List<Long> taskCategoryIds = member.getCompany().getTaskCategoryList().stream()
                        .map(taskCategory -> taskCategory.getId())
                        .collect(Collectors.toList());

        for(Long taskCategoryId : categoryIds) {
            if(!taskCategoryIds.contains(taskCategoryId)) throw new TaskCategoryException(ErrorCode.TASK_CATEGORY_ACCESS_DENIED);
        }

        taskCategoryService.deleteList(categoryIds);
        return ResponseEntity.ok(TaskCategoryConverter.toDeleteTaskCategoryListDto());
    }
}
