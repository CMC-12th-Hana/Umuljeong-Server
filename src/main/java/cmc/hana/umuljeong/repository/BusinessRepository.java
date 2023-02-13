package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}