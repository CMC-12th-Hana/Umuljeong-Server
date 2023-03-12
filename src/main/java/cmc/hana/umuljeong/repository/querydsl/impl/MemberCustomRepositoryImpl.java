package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.repository.querydsl.MemberCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cmc.hana.umuljeong.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<Member> findByCompanyAndName(Company company, String name) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.company.eq(company));
        if(name != null) {
            builder.and(member.name.contains(name));
        }

        return factory.selectFrom(member)
                .where(builder)
                .fetch();
    }
}
