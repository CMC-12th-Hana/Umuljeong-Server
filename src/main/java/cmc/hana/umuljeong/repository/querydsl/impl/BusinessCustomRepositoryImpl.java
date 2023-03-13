package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import cmc.hana.umuljeong.repository.querydsl.BusinessCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static cmc.hana.umuljeong.domain.QBusiness.business;
import static cmc.hana.umuljeong.domain.mapping.QBusinessMember.businessMember;

@Repository
@RequiredArgsConstructor
public class BusinessCustomRepositoryImpl implements BusinessCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<Business> findByCompanyAndNameAndStartAndFinishAndMember(Company company, String name, LocalDate start, LocalDate finish, Member member) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(business.clientCompany.company.eq(company));
        builder.and(business.businessPeriod.start.between(start, finish));
        builder.and(business.businessPeriod.finish.between(start, finish));

        if(name != null) builder.and(business.name.contains(name));
        if(member.getMemberRole() == MemberRole.STAFF) {
            List<Long> businessIds = factory.selectFrom(businessMember)
                    .where(businessMember.member.eq(member))
                    .fetch()
                    .stream()
                    .map(businessMember1 -> businessMember1.getBusiness().getId())
                    .collect(Collectors.toList());
            builder.and(business.id.in(businessIds));
        }

        return factory.selectFrom(business)
                .where(builder)
                .orderBy(business.name.asc())
                .fetch();
    }
}
