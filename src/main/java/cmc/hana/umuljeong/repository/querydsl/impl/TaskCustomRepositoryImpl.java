package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.repository.querydsl.TaskCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static cmc.hana.umuljeong.domain.QTask.task;

@Repository
@RequiredArgsConstructor
public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<Task> findByBusinessAndDateAndTaskCategory(Business business, LocalDate date, TaskCategory taskCategory) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(task.business.eq(business));
        builder.and(task.date.eq(date));
        if(taskCategory != null) {
            builder.and(task.taskCategory.eq(taskCategory));
        }

        return factory.selectFrom(task)
                .where(builder)
                .orderBy(task.date.asc())
                .fetch();
    }

    @Override
    public List<Task> findByBusinessAndYearAndMonthAndTaskCategory(Business business, Integer year, Integer month, TaskCategory taskCategory) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(task.business.eq(business));
        builder.and(task.date.year().eq(year));
        builder.and(task.date.month().eq(month));
        if(taskCategory != null) {
            builder.and(task.taskCategory.eq(taskCategory));
        }

        return factory.selectFrom(task)
                .where(builder)
                .orderBy(task.date.asc())
                .fetch();
    }
}
