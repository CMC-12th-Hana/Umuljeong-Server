package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class TaskResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TaskDto {
        private Long taskId;
        private String businessName;
        private String clientName;
        private String taskCategory;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TaskListDto {
        private List<TaskDto> taskDtoList;
        private Integer count;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateTaskDto {
        private Long taskId;
        private LocalDateTime createdAt;
    }
}
