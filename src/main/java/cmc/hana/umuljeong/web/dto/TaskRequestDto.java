package cmc.hana.umuljeong.web.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRequestDto {
    @Getter
    public static class CreateTaskDto {
        private Long clientCompanyId;
        private Long taskCategoryId;
        private LocalDate date;
        private String description;
        private List<MultipartFile> taskImageList = new ArrayList<>();
    }

    @Getter
    public static class UpdateTaskDto {
    }
}
