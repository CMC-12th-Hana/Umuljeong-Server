package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.util.ReactiveWrapperConverters;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhoneNumber(String phoneNumber);
}