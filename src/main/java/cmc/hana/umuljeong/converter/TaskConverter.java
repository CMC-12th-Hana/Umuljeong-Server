package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class TaskConverter {

    public static TaskResponseDto.CreateTaskDto toCreateTaskDto(Task task) {
        return TaskResponseDto.CreateTaskDto.builder()
                .taskId(task.getId())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static TaskResponseDto.TaskDto toTaskDto(Task task) {
        return TaskResponseDto.TaskDto.builder()
                .taskId(task.getId())
                .taskCategory(task.getTaskCategory().getName())
                .clientName(task.getBusiness().getClientCompany().getName())
                .businessName(task.getBusiness().getName())
                .build();
    }

    public static List<TaskResponseDto.TaskDto> toTaskDtoList(List<Task> taskList) {
        return taskList.stream()
                .map(task -> toTaskDto(task))
                .collect(Collectors.toList());
    }

    public static TaskResponseDto.TaskListDto toTaskListDto(List<Task> taskList) {
        return TaskResponseDto.TaskListDto.builder()
                .taskDtoList(toTaskDtoList(taskList))
                .count(taskList.size())
                .build();
    }
}
