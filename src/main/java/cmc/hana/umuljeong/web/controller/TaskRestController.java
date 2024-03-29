package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import cmc.hana.umuljeong.exception.*;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.util.MemberUtil;
import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.validation.annotation.ExistTask;
import cmc.hana.umuljeong.validation.annotation.ExistTaskCategory;
import cmc.hana.umuljeong.validation.validator.*;
import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Task API", description = "업무 조회, 추가")
@Validated
@RestController
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    // 빈 리스트가 요청으로 넘어올 때 빈 문자열로 인식되어 Cannot convert value of type 'java.lang.String' to required type 'org.springframework.web.multipart.MultipartFile' 발생
    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(List.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                setValue(null);
            }

        });
    }

    @Operation(summary = "[002_05_5]", description = "업무 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/client/business/task/{taskId}")
    public ResponseEntity<TaskResponseDto.TaskDto> getTask(@PathVariable(name = "taskId") @ExistTask Long taskId, @AuthUser Member member) {

        if(!TaskValidator.isAccessible(member, taskId)) throw new TaskException(ErrorCode.TASK_ACCESS_DENIED);

        Task task = taskService.findById(taskId);
        return ResponseEntity.ok(TaskConverter.toTaskDto(task));
    }

    @Operation(summary = "[002_02, 002_03]", description = "업무 목록 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true),
            @Parameter(name = "type", description = "MEMBER | TASK")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200 (리더)", description = "OK : 정상응답 (구성원별)", content = @Content(schema = @Schema(implementation = TaskResponseDto.LeaderTaskListDto.class))),
            @ApiResponse(responseCode = "200 (사원)", description = "OK : 정상응답 (업무별)", content = @Content(schema = @Schema(implementation = TaskResponseDto.StaffTaskListDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/{companyId}/client/business/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskListByCompany(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(name = "type") String type, @AuthUser Member member) {
        if(!CompanyValidator.isAccessible(member, companyId)) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);

        List<Task> taskList;
        if(type.equals("MEMBER")) {
            taskList = taskService.findByCompanyAndDate(companyId, date);
            return ResponseEntity.ok(TaskConverter.toLeaderTaskListDto(taskList)); // 구성원별
        }

        taskList = taskService.findByMemberAndDate(companyId, member, date);
        return ResponseEntity.ok(TaskConverter.toStaffTaskListDto(taskList)); // 업무별
    }

    @Operation(summary = "[003_04.5]", description = "날짜별 업무 현황")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = TaskResponseDto.StaffTaskListDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : businessId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/client/business/{businessId}/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskListByBusiness(@PathVariable(name = "businessId") @ExistBusiness Long businessId,
                                                                             @RequestParam(name = "year") Integer year,
                                                                             @RequestParam(name = "month") Integer month,
                                                                             @RequestParam(name = "day", required = false) Integer day,
                                                                             @RequestParam(name = "categoryId", required = false) Long categoryId,
                                                                             @AuthUser Member member) {
        if(!BusinessValidator.isAccessible(member, businessId)) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);
        if(categoryId != null && !TaskCategoryValidator.isAccessible(member, categoryId)) throw new TaskCategoryException(ErrorCode.TASK_CATEGORY_ACCESS_DENIED);

        List<Task> taskList = taskService.findByBusinessAndDateAndTaskCategory(businessId, year, month, day, categoryId);
        return ResponseEntity.ok(TaskConverter.toStaffTaskListDto(taskList)); // 업무별
    }

    @Operation(summary = "[003_03_3]", description = "누적 업무 건수 그래프 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/client/{clientId}/business/task/statistic")
    public ResponseEntity<TaskResponseDto.TaskStatisticListDto> taskStatisticByClientCompany(@PathVariable(name = "clientId") Long clientCompanyId, @AuthUser Member member) {
        if(!ClientCompanyValidator.isAccessible(member, clientCompanyId))
            throw new ClientCompanyException(ErrorCode.CLIENT_COMPANY_ACCESS_DENIED);

        Map<String, Pair<String, Integer>> taskStatistic = taskService.getStatistic(member.getCompany(), clientCompanyId);
        return ResponseEntity.ok(TaskConverter.toTaskStatisticListDto(taskStatistic));
    }

    @Operation(summary = "[003_03_3]", description = "누적 업무 건수 그래프 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/client/business/{businessId}/task/statistic")
    public ResponseEntity<TaskResponseDto.TaskStatisticListDto> taskStatisticByBusiness(@PathVariable(name = "businessId") Long businessId, @AuthUser Member member) {
        if(!BusinessValidator.isAccessible(member, businessId))
            throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        Map<String, Pair<String, Integer>> taskStatistic = taskService.getStatisticByBusiness(member.getCompany(), businessId);
        return ResponseEntity.ok(TaskConverter.toTaskStatisticListDto(taskStatistic));
    }

    @Operation(summary = "[002_05]", description = "업무 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200)", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = TaskResponseDto.CreateTaskDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 사업이나 업무카테고리가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping(value = "/company/client/business/task", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<TaskResponseDto.CreateTaskDto> createTask(@ModelAttribute @Valid TaskRequestDto.CreateTaskDto request, @AuthUser Member member) {
        if(!BusinessValidator.isAccessible(member, request.getBusinessId())) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        Task task = taskService.create(request, member);
        return ResponseEntity.ok(TaskConverter.toCreateTaskDto(task));
    }

    @Operation(summary = "[002_05_5.1]", description = "업무 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200)", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = TaskResponseDto.UpdateTaskDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 업무가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : taskId에 해당하는 업무가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping(value = "/company/client/business/task/{taskId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<TaskResponseDto.UpdateTaskDto> updateTask(@PathVariable(name = "taskId") @ExistTask Long taskId,
                                                                    @ModelAttribute @Valid TaskRequestDto.UpdateTaskDto request,
                                                                    @AuthUser Member member) {
        if(!TaskValidator.isAccessible(member, taskId)) throw new TaskException(ErrorCode.TASK_UPDATE_ACCESS_DENIED);

        Task task = taskService.update(taskId, request);
        return ResponseEntity.ok(TaskConverter.toUpdateTaskDto(task));
    }

    @Operation(summary = "[002_03]", description = "업무 삭제")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200)", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = TaskResponseDto.DeleteTaskDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 업무가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : taskId에 해당하는 업무가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @DeleteMapping("/company/client/business/task/{taskId}")
    public ResponseEntity<TaskResponseDto.DeleteTaskDto> deleteTask(@PathVariable(name = "taskId") @ExistTask Long taskId, @AuthUser Member member) {
        if(!TaskValidator.isAccessible(member, taskId)) throw new TaskException(ErrorCode.TASK_ACCESS_DENIED);

        taskService.delete(taskId);

        return ResponseEntity.ok(TaskConverter.toDeleteTaskDto());
    }


}
