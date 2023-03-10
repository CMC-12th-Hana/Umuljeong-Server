package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.aws.s3.AmazonS3PackageCommand;
import cmc.hana.umuljeong.aws.s3.FilePackageMeta;
import cmc.hana.umuljeong.aws.s3.FileService;
import cmc.hana.umuljeong.domain.common.Uuid;
import cmc.hana.umuljeong.repository.UuidCustomRepositoryImpl;
import cmc.hana.umuljeong.repository.UuidRepository;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public abstract class FileProcessServiceImpl<T extends FilePackageMeta> {
    private FileService amazonS3Service;

    private final UuidCustomRepositoryImpl uuidCustomRepository;
    private final UuidRepository uuidRepository;

    public FileProcessServiceImpl(FileService amazonS3Service, UuidCustomRepositoryImpl uuidCustomRepository, UuidRepository uuidRepository) {
        this.amazonS3Service = amazonS3Service;
        this.uuidCustomRepository = uuidCustomRepository;
        this.uuidRepository = uuidRepository;
    }

    public String uploadImage(MultipartFile file, T imagePackage) {
        String filePath = this.getFilePath(file, imagePackage);
        ObjectMetadata objectMetadata = generateObjectMetadata(file);
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Service.uploadFile(inputStream, objectMetadata, filePath);
        } catch (IOException ioe) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생했습니다 (%s)", file.getOriginalFilename()));
        }

        return amazonS3Service.getFileUrl(filePath);
    }


    public String uploadImage(InputStream inputStream, ObjectMetadata objectMetadata, String filePath, String fileName) {
        amazonS3Service.uploadFile(inputStream, objectMetadata, filePath);
        return amazonS3Service.getFileUrl(filePath);
    }

    public String getFilePath(MultipartFile file, T imagePackage) {
        AmazonS3PackageCommand command = imagePackage.createCommand();
        String uuid = imagePackage.getUuid();
        String filePath = amazonS3Service.getFileFolder(command) + createFileName(uuid, file.getOriginalFilename());
        return filePath;
    }

    public String getDeleteFilePath(T imagePackage, String uuid, String originFilename) {
        return amazonS3Service.getFileFolder(imagePackage.createCommand()) + createFileName(uuid, originFilename);
    }

    protected ObjectMetadata generateObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    protected String createFileName(String uuid, String originalFileName) {
        return uuid.concat(getFileExtension(originalFileName));
    }

    protected String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Uuid createUUID() {
        Uuid savedUuid = null;
        String candidate = UUID.randomUUID().toString();
        if (uuidCustomRepository.exist(candidate)) {
            savedUuid = createUUID();
        }
        savedUuid = Uuid.builder().uuid(candidate).build();
        return savedUuid;
    }

    protected String createValidOriginFileName(String originalFileName) {
        String trimedString = originalFileName.trim();
        String result = trimedString.replaceAll(" ", "-");
        return result;
    }

    public void deleteImage(String url) {
        amazonS3Service.deleteFile(getFileName(url));
    }

    // ex) task-image/24/b9328363-882f-4708-95cb-5d6b694bca34.png
    private String getFileName(String url) {
        String[] paths = url.split("/");
        return paths[3] + "/" + paths[4] + "/" + paths[5];
    }

//    public void deleteImage(String url) {
//        amazonS3Service.deleteFile(getFileName(url));
//    }
//
//    private String getFileName(String url) {
//        String[] paths = url.split("/");
//        return paths[paths.length - 2] + "/" + paths[paths.length - 1];
//    }
}
