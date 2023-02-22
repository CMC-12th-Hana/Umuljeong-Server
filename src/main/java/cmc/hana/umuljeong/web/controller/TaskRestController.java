package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.util.MemberUtil;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping("/company/client/business/{businessId}/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskList(@PathVariable(name = "businessId") Long businessId, @RequestParam(name = "date") LocalDate date, @AuthUser Member member) {
        List<Task> taskList = taskService.findByBusinessAndMemberAndDate(businessId, member, date);
        return ResponseEntity.ok(TaskConverter.toTaskListDto(taskList));
    }

    // TODO : 이 API 구현 자체를 후순위로 미루기
    @PostMapping("/company/client/business/{businessId}/task")
    public ResponseEntity<TaskResponseDto.CreateTaskDto> createTask(@PathVariable(name = "businessId") Long businessId, @RequestPart TaskRequestDto.CreateTaskDto request, @AuthUser Member member) {
        /*
            TODO : AuthMember, 이미지 로직 추가

        */
        Task task = taskService.create(request);
        return ResponseEntity.ok(TaskConverter.toCreateTaskDto(task));
    }
}
