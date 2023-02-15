package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TaskResponseDto {

    public static class TaskListDto {

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
