package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.util.MemberUtil;
import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.validation.annotation.ExistTask;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Task API", description = "업무 조회, 추가")
@Validated
@RestController
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @Operation(summary = "[002_05_5]", description = "업무 조회")
    @GetMapping("/company/client/business/task/{taskId}")
    public ResponseEntity<TaskResponseDto.TaskDto> getTask(@PathVariable(name = "taskId") @ExistTask Long taskId) {
        Task task = taskService.findById(taskId);
        return ResponseEntity.ok(TaskConverter.toTaskDto(task));
    }

    @Operation(summary = "[002_02, 002_03]", description = "업무 목록 조회")
    @GetMapping("/company/{companyId}/client/business/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskList(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestParam(name = "date") LocalDate date, @AuthUser Member member) {
        List<Task> taskList;
        if(member.getMemberRole() == MemberRole.LEADER) {
            taskList = taskService.findByCompanyAndDate(companyId, date);
            return ResponseEntity.ok(TaskConverter.toLeaderTaskListDto(taskList));
        }

        taskList = taskService.findByMemberAndDate(member, date);
        return ResponseEntity.ok(TaskConverter.toStaffTaskListDto(taskList));
    }

    @GetMapping("/company/client/business/{businessId}/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskListByBusiness(@PathVariable(name = "businessId") @ExistBusiness Long businessId, @RequestParam(name = "date") LocalDate date, @AuthUser Member member) {
        List<Task> taskList = taskService.findByBusinessAndMemberAndDate(businessId, member, date);
        return ResponseEntity.ok(TaskConverter.toLeaderTaskListDto(taskList)); // todo : 요구사항에 따라 변경
    }

    @Operation(summary = "[002_05]", description = "업무 추가")
    // TODO : 이 API 구현 자체를 후순위로 미루기
    @PostMapping("/company/client/business/{businessId}/task")
    public ResponseEntity<TaskResponseDto.CreateTaskDto> createTask(@PathVariable(name = "businessId") @ExistBusiness Long businessId, @RequestPart @Valid TaskRequestDto.CreateTaskDto request, @AuthUser Member member) {
        /*
            TODO : AuthMember, 이미지 로직 추가

        */
        Task task = taskService.create(request);
        return ResponseEntity.ok(TaskConverter.toCreateTaskDto(task));
    }

    @Operation(summary = "[002_05_5.1]", description = "업무 수정")
    @PatchMapping("/company/client/business/task/{taskId}")
    public ResponseEntity<TaskResponseDto.UpdateTaskDto> updateTask(@PathVariable(name = "taskId") @ExistTask Long taskId, @RequestPart @Valid TaskRequestDto.UpdateTaskDto request, @AuthUser Member member) {
        Task task = taskService.update(request);
        return ResponseEntity.ok(TaskConverter.toUpdateTaskDto(task));
    }
}
