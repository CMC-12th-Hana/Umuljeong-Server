package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskCategoryResponseDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

public class TaskCategoryConverter {

    public static TaskCategoryResponseDto.CreateTaskCategoryDto toCreateTaskCategoryDto(TaskCategory taskCategory) {
        return TaskCategoryResponseDto.CreateTaskCategoryDto.builder()
                .taskCategoryId(taskCategory.getId())
                .createdAt(taskCategory.getCompany().getCreatedAt())
                .build();
    }
}
