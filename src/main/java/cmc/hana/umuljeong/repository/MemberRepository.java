package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.util.ReactiveWrapperConverters;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"businessMemberList", "taskList"})
    Optional<Member> findByPhoneNumber(String phoneNumber);

    @EntityGraph(attributePaths = {"businessMemberList", "taskList"})
    Optional<Member> findById(Long aLong);

    List<Member> findByCompany(Company company);

    boolean existsByJoinCompanyStatusAndPhoneNumber(JoinCompanyStatus joinCompanyStatus, String phoneNumber);
}