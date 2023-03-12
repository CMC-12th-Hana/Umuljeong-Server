package cmc.hana.umuljeong.repository.querydsl.impl;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.repository.querydsl.ClientCompanyCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

import static cmc.hana.umuljeong.domain.QClientCompany.clientCompany;

@Repository
@RequiredArgsConstructor
public class ClientCompanyCustomRepositoryImpl implements ClientCompanyCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<ClientCompany> findByCompanyAndName(Company company, String name, String sort, String order) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(clientCompany.company.eq(company));

        if(name != null) {
            builder.and(clientCompany.name.contains(name));
        }

        Order direction;
        switch (order.toUpperCase(Locale.ROOT)) {
            case "DESC":
                direction = Order.DESC;
                break;
            default:
                direction = Order.ASC;
        }

        PathBuilder pathBuilder = new PathBuilder(clientCompany.getType(), clientCompany.getMetadata());

        OrderSpecifier specifier = new OrderSpecifier(direction, pathBuilder.get(sort));


        return factory.selectFrom(clientCompany)
                .where(builder)
                .orderBy(specifier)
                .fetch();
    }
}
