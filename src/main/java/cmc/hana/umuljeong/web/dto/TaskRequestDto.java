package cmc.hana.umuljeong.web.dto;

import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.validation.annotation.ExistTaskCategory;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRequestDto {
    @Getter
    public static class CreateTaskDto {
        @ExistClientCompany
        private Long clientCompanyId;
        @ExistTaskCategory
        private Long taskCategoryId;
        private LocalDate date;
        private String description;
        private List<MultipartFile> taskImageList = new ArrayList<>();
    }

    @Getter
    public static class UpdateTaskDto {
    }
}
