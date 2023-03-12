package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;

import java.util.List;

public interface MemberCustomRepository {
    List<Member> findByCompanyAndName(Company company, String name);
}
