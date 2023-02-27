package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDate;
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
        private LocalDate date;
    }

    @Builder
    @Getter @Setter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberDto {
        private String name;
        private List<TaskDto> taskDtoList;
        private Integer count;
    }

    // @Builder
    @Getter @Setter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TaskListDto {
        private Integer count;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LeaderTaskListDto extends TaskListDto {
        private List<MemberDto> memberDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class StaffTaskListDto extends TaskListDto {
        private List<TaskDto> taskDtoList;
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
