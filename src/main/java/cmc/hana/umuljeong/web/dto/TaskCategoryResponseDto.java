package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TaskCategoryResponseDto {

    public static class TaskCategoryList {

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateTaskCategoryDto {
        private Long taskCategoryId;
        private LocalDateTime createdAt;
    }

    public static class UpdateTaskCategoryDto {
    }

    public static class DeleteTaskCategoryDto {
    }
}
