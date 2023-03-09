package cmc.hana.umuljeong.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static cmc.hana.umuljeong.domain.common.QUuid.uuid1;

@Repository
@RequiredArgsConstructor
public class UuidCustomRepositoryImpl implements UuidCustomRepository {

    private final EntityManager em;
    private final JPAQueryFactory factory;


    @Override
    public Boolean exist(String uuid) {
        Integer fetchOne = factory.selectOne()
                .from(uuid1)
                .where(uuid1.uuid.eq(uuid))
                .fetchFirst();
        return fetchOne != null;
    }
}
