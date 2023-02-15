package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.converter.TaskCategoryConverter;
import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.service.TaskCategoryService;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;
import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskCategoryRestController {

    private final TaskCategoryService taskCategoryService;

    @GetMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.TaskCategoryList> getTaskCategoryList() {
        return null;
    }

    @PostMapping("/company/client/business/task/category")
    public ResponseEntity<TaskCategoryResponseDto.CreateTaskCategoryDto> createTaskCategory(@RequestBody TaskCategoryRequestDto.CreateTaskCategoryDto request) {
        TaskCategory taskCategory = taskCategoryService.create(request);
        return ResponseEntity.ok(TaskCategoryConverter.toCreateTaskCategoryDto(taskCategory));
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
