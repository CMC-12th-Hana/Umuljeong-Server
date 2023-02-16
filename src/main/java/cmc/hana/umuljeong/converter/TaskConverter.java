package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

import java.util.List;

public class TaskConverter {

    public static TaskResponseDto.CreateTaskDto toCreateTaskDto(Task task) {
        return TaskResponseDto.CreateTaskDto.builder()
                .taskId(task.getId())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static TaskResponseDto.TaskListDto toTaskListDto(List<Task> taskList) {
        return new TaskResponseDto.TaskListDto();
    }
}
