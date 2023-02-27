package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.embedded.BusinessPeriod;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Business extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<BusinessMember> businessMemberList;

    private String name;

    @Embedded
    private BusinessPeriod businessPeriod;

    private Integer revenue;

    private String description;

    public void setBusinessMemberList(List<BusinessMember> businessMemberList) {
        this.businessMemberList = businessMemberList;
    }
}
