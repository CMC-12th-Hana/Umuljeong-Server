package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.TaskImage;

import java.util.List;

public interface TaskImageCustomRepository {
    List<TaskImage> findByIds(List<Long> deleteImageIds);
}
