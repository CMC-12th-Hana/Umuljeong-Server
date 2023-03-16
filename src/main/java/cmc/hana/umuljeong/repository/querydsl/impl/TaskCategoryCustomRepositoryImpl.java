package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.repository.querydsl.TaskCategoryCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cmc.hana.umuljeong.domain.QTaskCategory.taskCategory;

@Repository
@RequiredArgsConstructor
public class TaskCategoryCustomRepositoryImpl implements TaskCategoryCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskCategory> findByIds(List<Long> categoryIds) {
        return factory.selectFrom(taskCategory)
                .where(taskCategory.id.in(categoryIds))
                .fetch();
    }
}
