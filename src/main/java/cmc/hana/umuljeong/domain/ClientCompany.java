package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.embedded.SalesRepresentative;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter @Setter
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

    private String tel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_representative_id")
    private SalesRepresentative salesRepresentative;

    @OneToMany(mappedBy = "clientCompany", cascade = CascadeType.ALL)
    private List<Business> businessList;

    private Integer taskCount;

    private Integer businessCount;

    public void setCompany(Company company) {
        if (this.company != null) {
            this.company.getClientCompanyList().remove(this);
        }
        this.company = company;
        company.getClientCompanyList().add(this);
    }

    public void removeRelationshop() {
        this.company.getClientCompanyList().remove(this); this.company = null;
    }
}
