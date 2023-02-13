package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}