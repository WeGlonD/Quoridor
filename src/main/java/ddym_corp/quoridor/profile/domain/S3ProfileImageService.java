package ddym_corp.quoridor.profile.domain;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ProfileImageService implements ProfileImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    private final AmazonS3Client amazonS3Client;
    private final ProfileImageRepository profileImageRepository;

    public String upload(MultipartFile file, Long uid) {
        String preUrl = profileImageRepository.findOne(uid);
        log.info("S3ProfileImageService upload");
        log.info("preUrl: {}", preUrl);
        if(preUrl != null) {
            amazonS3Client.deleteObject(bucket + dir, extractFileName(preUrl));
        }

        try {
            String fileName = file.getOriginalFilename(); log.info("originalFileName: {}", fileName);
            String storeFileName = createStoreFileName(fileName); log.info("storeFileName: {}", storeFileName);

            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            log.info("metadata - ContentType: {}, ContentLength: {}", file.getContentType(), file.getSize());

            amazonS3Client.putObject(
                    new PutObjectRequest(bucket + dir, storeFileName, file.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            String url = URLDecoder.decode(amazonS3Client.getUrl(bucket + dir, storeFileName).toString(), StandardCharsets.UTF_8);
            profileImageRepository.save(uid, url);
            log.info("stored image: {}", extractFileName(url));

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String get(Long uid) {
        log.info("S3ProfileImageService get - uid: {}", uid);
        String url = profileImageRepository.findOne(uid);
        log.info("Service - url: {}", url);
        if(url != null) {
            return url;
        }
        return "null";
    }

    public String delete(Long uid) {
        log.info("S3ProfileImageService delete - uid: {}", uid);
        String url = profileImageRepository.findOne(uid);
        log.info("Service - url: {}", url);
        if(url != null){
            profileImageRepository.delete(uid);
            amazonS3Client.deleteObject(bucket + dir, extractFileName(url));
            return "OK";
        }
        else {
            return "NO_PROFILE_IMAGE";
        }
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private String extractFileName(String url) {
        int pos = url.lastIndexOf("/");
        String returnStr = url.substring(pos + 1);
        log.info("extractFileName: {}", returnStr);
        return returnStr;
    }
}
