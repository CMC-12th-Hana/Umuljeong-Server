package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import java.util.List;

public class TaskCategoryRequestDto {
    @Getter
    public static class CreateTaskCategoryDto {
        private String name;
    }

    @Getter
    public static class UpdateTaskCategoryDto {
        private Long taskCategoryId;
        private String name;
    }

    @Getter
    public static class UpdateTaskCategoryListDto {
        List<UpdateTaskCategoryDto> updateTaskCategoryDtoList;
    }

    @Getter
    public static class DeleteTaskCategoryListDto {
        List<Long> taskCategoryIdList;
    }
}
