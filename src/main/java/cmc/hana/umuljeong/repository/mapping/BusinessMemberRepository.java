package cmc.hana.umuljeong.repository.mapping;

import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessMemberRepository extends JpaRepository<BusinessMember, Long> {
}