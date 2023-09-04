package ddym_corp.quoridor.profile.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    String upload(MultipartFile file, Long uid);
    String get(Long uid);
    String delete(Long uid);
}
