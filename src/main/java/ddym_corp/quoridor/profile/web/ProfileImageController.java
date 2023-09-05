package ddym_corp.quoridor.profile.web;

import ddym_corp.quoridor.user.auth.web.uitls.SessionConst;
import ddym_corp.quoridor.profile.domain.ProfileImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        log.info("ProfileImageController uploadProfile - uid : {}", uid);

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
