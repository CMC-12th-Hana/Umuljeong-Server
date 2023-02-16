package cmc.hana.umuljeong.web.dto;

import java.util.List;

public class TaskCategoryRequestDto {
    public static class CreateTaskCategoryDto {
        private String name;
    }

    public static class UpdateTaskCategoryDto {
        private Long taskCategoryId;
        private String name;
    }

    public static class UpdateTaskCategoryListDto {
        List<UpdateTaskCategoryDto> updateTaskCategoryDtoList;
    }

    public static class DeleteTaskCategoryListDto {
        List<Long> taskCategoryIdList;
    }
}
