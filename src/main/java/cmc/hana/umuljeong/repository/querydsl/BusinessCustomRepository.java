package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BusinessCustomRepository {
    List<Business> findByCompanyAndNameAndStartAndFinishAndMember(Company company, String name, LocalDate start, LocalDate finish, Member member);

    List<Business> findByClientCompanyAndNameAndStartAndFinishAndMember(ClientCompany clientCompany, String name, LocalDate start, LocalDate finish, Member member);
}
