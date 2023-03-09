package cmc.hana.umuljeong.aws.s3;

import cmc.hana.umuljeong.domain.common.Uuid;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TaskImagePackageMetaData extends FilePackageMeta {

    private Long businessId;
    private Uuid uuidEntity;


    @Builder
    public TaskImagePackageMetaData(String uuid, Long businessId, Uuid uuidEntity) {
        super(uuid);
        this.businessId = businessId;
        this.uuidEntity = uuidEntity;
    }

    public TaskImagePackageCommand createCommand() {
        return new TaskImagePackageCommand(this);
    }
}
