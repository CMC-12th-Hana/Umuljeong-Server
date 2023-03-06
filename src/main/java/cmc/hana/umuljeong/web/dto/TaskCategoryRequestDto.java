package cmc.hana.umuljeong.web.dto;

import cmc.hana.umuljeong.validation.annotation.ExistTaskCategory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class TaskCategoryRequestDto {
    @Getter
    public static class CreateTaskCategoryDto {
        @NotBlank
        private String name;
        @NotBlank
        @Size(min = 6, max = 6)
        private String color;
    }

    @Getter
    public static class UpdateTaskCategoryDto {
        @NotBlank
        private String name;
        @NotBlank
        @Size(min = 6, max = 6)
        private String color;
    }

    @Getter
    public static class UpdateTaskCategoryListDto {
        List<@Valid UpdateTaskCategoryDto> updateTaskCategoryDtoList;
    }

    @Getter
    public static class DeleteTaskCategoryListDto {
        List<Long> taskCategoryIdList;
    }
}
