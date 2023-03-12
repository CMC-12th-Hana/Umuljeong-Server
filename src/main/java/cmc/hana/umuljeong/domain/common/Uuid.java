package cmc.hana.umuljeong.domain.common;

import cmc.hana.umuljeong.domain.TaskImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Uuid extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String uuid;

    @OneToOne
    @JoinColumn(name = "task_image_id")
    private TaskImage taskImage;

    public void setTaskImage(TaskImage taskImage) {
        this.taskImage = taskImage;
        taskImage.setUuid(this);
    }
}
