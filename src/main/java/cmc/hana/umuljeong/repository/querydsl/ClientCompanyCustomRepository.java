package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;

import java.util.List;

public interface ClientCompanyCustomRepository {
    List<ClientCompany> findByCompanyAndName(Company company, String name, String sort, String orde);
}
