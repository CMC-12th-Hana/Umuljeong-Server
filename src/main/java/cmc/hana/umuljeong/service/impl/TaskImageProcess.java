package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.aws.s3.FileService;
import cmc.hana.umuljeong.aws.s3.TaskImagePackageMetaData;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskImage;
import cmc.hana.umuljeong.domain.common.Uuid;
import cmc.hana.umuljeong.exception.TaskException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.UuidCustomRepositoryImpl;
import cmc.hana.umuljeong.repository.UuidRepository;
import cmc.hana.umuljeong.service.FileProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class TaskImageProcess extends FileProcessServiceImpl<TaskImagePackageMetaData> {

    @Autowired
    public TaskImageProcess(FileService amazonS3Service, UuidCustomRepositoryImpl uuidCustomRepository, UuidRepository uuidRepository) {
        super(amazonS3Service, uuidCustomRepository, uuidRepository);
    }

    /*
    @todo
    save Image asynchronously
    ref: https://www.code4copy.com/java/spring-boot-upload-s3/
     */
    //setImage to entity
    public Task uploadImageAndMapToReview(MultipartFile file, TaskImagePackageMetaData taskImagePackageMeta, Task task) {
        Optional.ofNullable(task).orElseThrow(() -> new TaskException(ErrorCode.TASK_NOT_FOUND));
        String url = this.uploadImage(file, taskImagePackageMeta);

        TaskImage taskImage = TaskImage.builder()
                    .url(url)
                    .fileName(file.getOriginalFilename())
                    .build();

        Uuid uuid = taskImagePackageMeta.getUuidEntity();
        uuid.setTaskImage(taskImage);

        taskImage.setTask(task);
        return task;
    }
}
