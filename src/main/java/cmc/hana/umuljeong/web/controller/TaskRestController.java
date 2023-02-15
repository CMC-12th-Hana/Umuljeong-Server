package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping("/company/client/business/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskList() {
        return null;
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
