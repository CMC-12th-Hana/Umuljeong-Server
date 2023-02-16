package cmc.hana.umuljeong.web.controller;

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

    @GetMapping("/company/client/business/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskList(@RequestParam(name = "date") LocalDate date) {
        /*
            TODO : 페이징
         */
        List<Task> taskList = taskService.findByMemberAndDate(MemberUtil.mockMember(), date);
        return ResponseEntity.ok(TaskConverter.toTaskListDto(taskList));
    }

    @PostMapping("/company/client/business/task")
    public ResponseEntity<TaskResponseDto.CreateTaskDto> createTask(@RequestPart TaskRequestDto.CreateTaskDto request) {
        /*
            TODO : AuthMember, 이미지 로직 추가
        */
        Task task = taskService.create(request);
        return ResponseEntity.ok(TaskConverter.toCreateTaskDto(task));
    }
}
