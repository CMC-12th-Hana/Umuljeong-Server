package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}