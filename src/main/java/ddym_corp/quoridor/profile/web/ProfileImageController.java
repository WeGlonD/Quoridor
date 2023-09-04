package ddym_corp.quoridor.profile.web;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import ddym_corp.quoridor.auth.web.SessionConst;
import ddym_corp.quoridor.profile.domain.ProfileImageRepository;
import ddym_corp.quoridor.profile.domain.ProfileImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @PostMapping
    public ResponseEntity<String> uploadProfile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);
        Long uid = (Long) session.getAttribute(SessionConst.USER_ID);

        String url = profileImageService.upload(file, uid);
        if(url != null) {
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping
    public String getProfileUrl(@RequestParam Long uid, HttpServletRequest request) {
        request.getSession(false).setMaxInactiveInterval(1800);

        return profileImageService.get(uid);
    }

    @GetMapping("/delete")
    public String deleteProfile(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);
        Long uid = (Long) session.getAttribute(SessionConst.USER_ID);

        return profileImageService.delete(uid);
    }
}
