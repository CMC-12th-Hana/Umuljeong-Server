package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskCategoryRestController {

    @GetMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.TaskCategoryList> getTaskCategoryList() {
        return null;
    }

    @PostMapping("/company/client/business/task/category")
    public ResponseEntity<TaskCategoryResponseDto.CreateTaskCategoryDto> createTaskCategory() {
        return null;
    }

    @PatchMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.UpdateTaskCategoryDto> updateTaskCategory() {
        return null;
    }

    @DeleteMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.DeleteTaskCategoryDto> deleteTaskCategory() {
        return null;
    }
}
