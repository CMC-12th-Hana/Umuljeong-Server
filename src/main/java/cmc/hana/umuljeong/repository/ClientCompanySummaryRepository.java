package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.ClientCompanySummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCompanySummaryRepository extends JpaRepository<ClientCompanySummary, Long> {
}