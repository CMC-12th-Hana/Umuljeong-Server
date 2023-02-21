package cmc.hana.umuljeong.domain.mapping;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setBusiness(Business business) {
        if (this.business != null) {
            this.business.getBusinessMemberList().remove(this);
        }
        this.business = business;
        business.getBusinessMemberList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
