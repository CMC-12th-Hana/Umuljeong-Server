package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.repository.querydsl.ClientCompanyCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cmc.hana.umuljeong.domain.QClientCompany.clientCompany;

@Repository
@RequiredArgsConstructor
public class ClientCompanyCustomRepositoryImpl implements ClientCompanyCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<ClientCompany> findByCompanyAndName(Company company, String name) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(clientCompany.company.eq(company));

        if(name != null) {
            builder.and(clientCompany.name.contains(name));
        }

        return factory.selectFrom(clientCompany)
                .where(builder)
                .fetch();
    }
}
