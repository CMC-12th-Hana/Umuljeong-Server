package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.embedded.SalesRepresentative;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_representative_id")
    private SalesRepresentative salesRepresentative;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_summary_id")
    private ClientCompanySummary clientCompanySummary;
}
