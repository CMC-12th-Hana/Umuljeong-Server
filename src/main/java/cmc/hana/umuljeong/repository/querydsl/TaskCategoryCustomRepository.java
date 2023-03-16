package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.TaskCategory;

import java.util.List;

public interface TaskCategoryCustomRepository {
    List<TaskCategory> findByIds(List<Long> categoryIds);
}
