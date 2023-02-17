package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.TaskCategoryConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.service.TaskCategoryService;
import cmc.hana.umuljeong.util.MemberUtil;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;
import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskCategoryRestController {

    private final TaskCategoryService taskCategoryService;

    @GetMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.TaskCategoryDtoList> getTaskCategoryList(@AuthUser Member member) {
        List<TaskCategory> taskCategoryList = taskCategoryService.findByCompany(member.getCompany());
        return ResponseEntity.ok(TaskCategoryConverter.toTaskCategoryDtoList(taskCategoryList));
    }

    @PostMapping("/company/client/business/task/category")
    public ResponseEntity<TaskCategoryResponseDto.CreateTaskCategoryDto> createTaskCategory(@RequestBody TaskCategoryRequestDto.CreateTaskCategoryDto request, @AuthUser Member member) {
        // todo : 리더 권한 체크 후 해당 회사의 업무 카테고리로 추가
        TaskCategory taskCategory = taskCategoryService.create(member.getCompany(), request);
        return ResponseEntity.ok(TaskCategoryConverter.toCreateTaskCategoryDto(taskCategory));
    }

    @PatchMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.UpdateTaskCategoryListDto> updateTaskCategory(@RequestBody TaskCategoryRequestDto.UpdateTaskCategoryListDto request) {
        /*
            TODO : 멤버 권한 체크
        */
        List<TaskCategory> taskCategoryList = taskCategoryService.updateList(request);
        return ResponseEntity.ok(TaskCategoryConverter.toUpdateTaskCategoryListDto(taskCategoryList));
    }

    @DeleteMapping("/company/client/business/task/categories")
    public ResponseEntity<TaskCategoryResponseDto.DeleteTaskCategoryListDto> deleteTaskCategory(@RequestBody TaskCategoryRequestDto.DeleteTaskCategoryListDto request) {
        // todo : delete할 때 넘겨줘야할 정보들이 어떤 게 있을까?..
        taskCategoryService.deleteList(request);
        return ResponseEntity.ok(TaskCategoryConverter.toDeleteTaskCategoryListDto());
    }
}
