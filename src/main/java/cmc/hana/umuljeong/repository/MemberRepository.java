package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.util.ReactiveWrapperConverters;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhoneNumber(String phoneNumber);

    List<Member> findByCompany(Company company);
}