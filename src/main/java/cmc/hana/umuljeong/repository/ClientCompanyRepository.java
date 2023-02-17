package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {
    List<ClientCompany> findByCompany(Company company);
}