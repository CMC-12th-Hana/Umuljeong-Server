package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

public class TaskConverter {

    public static TaskResponseDto.CreateTaskDto toCreateTaskDto(TaskCategory taskCategory) {
        return TaskResponseDto.CreateTaskDto.builder()
                .taskId(taskCategory.getId())
                .createdAt(taskCategory.getCreatedAt())
                .build();
    }
}
