package cmc.hana.umuljeong.aws.s3;


import cmc.hana.umuljeong.util.PropertyUtil;

public class TaskImagePackageCommand implements AmazonS3PackageCommand {

    private TaskImagePackageMetaData taskImagePackageMeta;

    public TaskImagePackageCommand(TaskImagePackageMetaData taskImagePackageMeta) {
        this.taskImagePackageMeta = taskImagePackageMeta;
    }

    public void setTaskImagePackage(TaskImagePackageMetaData taskImagePackageMeta) {
        this.taskImagePackageMeta = taskImagePackageMeta;
    }

    @Override
    public String getFolder() {
        return getFolderInternal(this.taskImagePackageMeta);
    }

    private String getFolderInternal(TaskImagePackageMetaData taskImagePackageMeta) {

        String rootPackage = PropertyUtil.getProperty("cloud.aws.s3.folder.task-image");
        // task-image/buildingId/task/${taskImage file name} νμμΌλ‘
        return rootPackage + "/" + taskImagePackageMeta.getBusinessId() + "/";
    }
}
