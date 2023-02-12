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

    // TODO : 방문건수와 사업건수를 count 쿼리로 처리할지, 통계필드나 테이블을 따로두고 관리할지 고민하기
}
