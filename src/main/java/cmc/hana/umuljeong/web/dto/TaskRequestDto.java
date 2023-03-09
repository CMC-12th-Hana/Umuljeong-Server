package cmc.hana.umuljeong.web.dto;

import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.validation.annotation.ExistTaskCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRequestDto {
    @Getter @Setter
    public static class CreateTaskDto {
        @ExistBusiness
        private Long businessId;
        @ExistTaskCategory
        private Long taskCategoryId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate date;
        private String description;
        private List<MultipartFile> taskImageList = new ArrayList<>();
    }

    @Getter
    public static class UpdateTaskDto {
    }
}
