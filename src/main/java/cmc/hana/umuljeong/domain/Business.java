package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.embedded.BusinessPeriod;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import com.querydsl.core.annotations.QueryInit;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<BusinessMember> businessMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Task> taskList = new ArrayList<>();

    private String name;

    @Embedded
    private BusinessPeriod businessPeriod;

    private Long revenue;

    @Column(length = 300)
    private String description;

    public void setBusinessMemberList(List<BusinessMember> businessMemberList) {
        this.businessMemberList = businessMemberList;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        if (this.clientCompany != null) {
            this.clientCompany.getBusinessList().remove(this);
        }
        this.clientCompany = clientCompany;
        clientCompany.getBusinessList().add(this);
    }

    public void removeRelationship() {
        if(this.clientCompany != null) {
            this.clientCompany.getBusinessList().remove(this);
            this.clientCompany = null;
        }
    }
}
