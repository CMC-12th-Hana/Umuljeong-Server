package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.TaskImage;
import cmc.hana.umuljeong.repository.querydsl.TaskImageCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cmc.hana.umuljeong.domain.QTaskImage.taskImage;

@Repository
@RequiredArgsConstructor
public class TaskImageCustomRepositoryImpl implements TaskImageCustomRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<TaskImage> findByIds(List<Long> deleteImageIds) {
        return factory.selectFrom(taskImage)
                .where(taskImage.id.in(deleteImageIds))
                .fetch();
    }
}
