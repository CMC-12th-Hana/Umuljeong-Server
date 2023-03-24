
package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import com.querydsl.core.annotations.QueryInit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @QueryInit("clientCompany.company")
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String exitMemberName;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskImage> taskImageList = new ArrayList<>();

    private String title;

    private LocalDate date;

    @Column(length = 300)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_category_id")
    private TaskCategory taskCategory;

    public void setBusiness(Business business) {
        if (this.business != null) {
            this.business.getTaskList().remove(this);
        }
        this.business = business;
        business.getTaskList().add(this);
    }

    public void setMember(Member member) {
        if(member == null) {
            this.member = null;
        }

        else if (this.member != null) {
            this.member.getTaskList().remove(this);
        }

        else {
            this.member = member;
            member.getTaskList().add(this);
        }
    }

    public void removeRelationship() {
        if(this.member != null) {
            this.member.getTaskList().remove(this); this.member = null;
        }
        if(this.business != null) {
            this.business.getTaskList().remove(this); this.business = null;
        }
    }
}
