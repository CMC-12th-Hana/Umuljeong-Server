package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.TaskCategoryException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.TaskCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskCategoryValidator {

    private final TaskCategoryRepository taskCategoryRepository;
    private static TaskCategoryRepository staticTaskCategoryRepository;

    @PostConstruct
    public void init() {
        this.staticTaskCategoryRepository = this.taskCategoryRepository;
    }

    public static boolean isAccessible(Member member, Long taskCategoryId) {
        return member.getCompany().getTaskCategoryList().stream().anyMatch(taskCategory -> taskCategory.getId().equals(taskCategoryId));
    }

    public static boolean isAccessible(Member member, List<Long> categoryIds) {
        List<Long> taskCategoryIds = member.getCompany().getTaskCategoryList().stream()
                .map(taskCategory -> taskCategory.getId())
                .collect(Collectors.toList());

        for(Long taskCategoryId : categoryIds) {
            if(!taskCategoryIds.contains(taskCategoryId)) return false;
        }

        return true;
    }

    public static boolean existsNameByCompany(String name, Long companyId) {
        return staticTaskCategoryRepository.existsByNameAndCompany_Id(name, companyId);
    }
}
