package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.common.Uuid;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private String fileName;

    private String url;

    @OneToOne(mappedBy = "taskImage", cascade = CascadeType.ALL)
    private Uuid uuid;

    public void setUuid(Uuid uuid) {
        this.uuid = uuid;
    }

    public void setTask(Task task) {
        if (this.task != null) {
            this.task.getTaskImageList().remove(this);
        }
        this.task = task;
        task.getTaskImageList().add(this);
    }
}
