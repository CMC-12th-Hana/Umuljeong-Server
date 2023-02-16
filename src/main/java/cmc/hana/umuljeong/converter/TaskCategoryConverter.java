package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static TaskCategoryResponseDto.TaskCategoryDtoList toTaskCategoryDtoList(List<TaskCategory> taskCategoryList) {
        return TaskCategoryResponseDto.TaskCategoryDtoList.builder()
                .taskCategoryDtoList(new ArrayList<>())
                .count(taskCategoryList.size())
                .build();
    }
}
