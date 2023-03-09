
package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskImage> taskImageList = new ArrayList<>();

    private LocalDate date;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_category_id")
    private TaskCategory taskCategory;

    void setBusiness(Business business) {
        if (this.business != null) {
            this.business.getTaskList().remove(this);
        }
        this.business = business;
        business.getTaskList().add(this);
    }
}
