package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.TaskCategoryException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

public class TaskCategoryValidator {

    public static boolean isAccessible(Member member, Long taskCategoryId) {
        return member.getCompany().getTaskCategoryList().stream().anyMatch(taskCategory -> taskCategory.getId() == taskCategoryId);
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
}
