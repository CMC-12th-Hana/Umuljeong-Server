package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;
import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskCategoryConverter {

    public static TaskCategoryResponseDto.CreateTaskCategoryDto toCreateTaskCategoryDto(TaskCategory taskCategory) {
        return TaskCategoryResponseDto.CreateTaskCategoryDto.builder()
                .taskCategoryId(taskCategory.getId())
                .createdAt(taskCategory.getCreatedAt())
                .build();
    }

    public static TaskCategoryResponseDto.UpdateTaskCategoryListDto toUpdateTaskCategoryListDto(List<TaskCategory> taskCategoryList) {
        return TaskCategoryResponseDto.UpdateTaskCategoryListDto.builder()
                .count(taskCategoryList.size())
                .build();
    }

    public static TaskCategoryResponseDto.DeleteTaskCategoryListDto toDeleteTaskCategoryListDto() {
        return TaskCategoryResponseDto.DeleteTaskCategoryListDto.builder()
                .deletedAt(LocalDateTime.now())
                .build();
    }

    public static TaskCategoryResponseDto.TaskCategoryDto toTaskCategoryDto(TaskCategory taskCategory) {
        return TaskCategoryResponseDto.TaskCategoryDto.builder()
                .taskCategoryId(taskCategory.getId())
                .name(taskCategory.getName())
                .color(taskCategory.getColor())
                .build();
    }

    public static List<TaskCategoryResponseDto.TaskCategoryDto> toTaskCategoryDtoList(List<TaskCategory> taskCategoryList) {
        return taskCategoryList.stream()
                .map(taskCategory -> toTaskCategoryDto(taskCategory))
                .collect(Collectors.toList());
    }

    public static TaskCategoryResponseDto.TaskCategoryListDto toTaskCategoryListDto(List<TaskCategory> taskCategoryList) {
        return TaskCategoryResponseDto.TaskCategoryListDto.builder()
                .taskCategoryDtoList(toTaskCategoryDtoList(taskCategoryList))
                .count(taskCategoryList.size())
                .build();
    }

    public static TaskCategory toTaskCategory(Company company, TaskCategoryRequestDto.CreateTaskCategoryDto request) {
        return TaskCategory.builder()
                .company(company)
                .name(request.getName())
                .color(request.getColor())
                .build();
    }

    public static TaskCategoryResponseDto.UpdateTaskCategoryDto toUpdateTaskCategory(TaskCategory taskCategory) {
        return TaskCategoryResponseDto.UpdateTaskCategoryDto.builder()
                .taskCategoryId(taskCategory.getId())
                .updatedAt(taskCategory.getUpdatedAt())
                .build();
    }
}
