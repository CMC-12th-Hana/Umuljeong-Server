package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class TaskCategoryResponseDto {

    public static class TaskCategoryDto {
        private Long taskCategoryId;
        private String name;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TaskCategoryDtoList {
        List<TaskCategoryDto> taskCategoryDtoList;
        private Integer count;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateTaskCategoryDto {
        private Long taskCategoryId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateTaskCategoryDto {
        private Long taskId;
        private String name;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteTaskCategoryListDto {
       private LocalDateTime deletedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateTaskCategoryListDto {
        private List<UpdateTaskCategoryDto> updatedTaskCategoryDtoList;
        private Integer count;
        private LocalDateTime updatedAt;
    }


}
