package cmc.hana.umuljeong.repository.mapping;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessMemberRepository extends JpaRepository<BusinessMember, Long> {
    List<BusinessMember> findByBusiness(Business business);
}