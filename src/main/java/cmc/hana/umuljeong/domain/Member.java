package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.domain.enums.MemberRole;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String name;

    @Column(unique = true)
    private String phoneNumber;

    private String password;

    private String staffRank;

    private String staffNumber; // 사원번호

    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private JoinCompanyStatus joinCompanyStatus;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessMember> businessMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Task> taskList = new ArrayList<>();

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void removeRelationship() {
        if(this.company != null) {
            this.company.getMemberList().remove(this);
            this.company = null;
        }

        this.taskList.stream().forEach(task -> {
            task.setExitMemberName(this.getName() + "(탈퇴)");
            task.setMember(null);
        });
    }


}