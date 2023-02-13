package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {
}