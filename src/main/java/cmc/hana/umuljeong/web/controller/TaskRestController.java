package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.web.dto.TaskResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskRestController {

    @GetMapping("/company/client/business/tasks")
    public ResponseEntity<TaskResponseDto.TaskListDto> getTaskList() {
        return null;
    }

    @PostMapping("/company/client/business/task")
    public ResponseEntity<TaskResponseDto.CreateTaskDto> createTask() {
        return null;
    }
}
