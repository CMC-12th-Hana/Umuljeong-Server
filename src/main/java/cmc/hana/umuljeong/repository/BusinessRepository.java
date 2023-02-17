package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findByClientCompany(ClientCompany clientCompany);
}