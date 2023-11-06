package ddym_corp.quoridor.user.auth.web;

import ddym_corp.quoridor.user.auth.login.LoginServiceImpl;
import ddym_corp.quoridor.user.auth.web.dto.LoginDto;
import ddym_corp.quoridor.user.auth.web.dto.SignupDto;
import ddym_corp.quoridor.user.auth.web.dto.UserUpdateDto;
import ddym_corp.quoridor.user.auth.web.exception.UserNotFoundException;
import ddym_corp.quoridor.user.auth.oAuth2.callbackParams.KakaoCallbackDto;
import ddym_corp.quoridor.user.auth.oAuth2.service.OAuthLoginService;
import ddym_corp.quoridor.user.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import static ddym_corp.quoridor.user.auth.web.uitls.SessionConst.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final LoginServiceImpl loginService;
    private final OAuthLoginService oAuthLoginService;
    @PostMapping("/users/login")
    public User login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {

        User loginUser = loginService.login(loginDto.getLoginId(), loginDto.getPassword());
        log.info("login? {}",loginUser);

        //로그인 실패처리
        if (loginUser == null){
            // 400 예외처리
        }

        //로그인 성공처리
        HttpSession session = request.getSession();
        log.info("login id: {} session id: {}", loginUser.getLoginId(), session.getId());
        //세션 유지시간 1800초
        session.setMaxInactiveInterval(1800);
        session.setAttribute(USER_ID, loginUser.getUid());

        log.info("session time = {}", session.getMaxInactiveInterval());
        return loginUser;
    }

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest request){
        //세션을 삭제
        HttpSession session = request.getSession(false);
        if (session!=null){
            session.invalidate();
        }
        return "OK";
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody SignupDto signupDto, HttpServletResponse response) {
        User user = new User(signupDto.getLoginId(), signupDto.getPassword());
        user.setName(signupDto.getName()); user.setEmail(signupDto.getEmail());
        user.setScore(0); user.setTotalGames(0); user.setWinGames(0);

        Long result = loginService.join(user);
        if (result == -1L) { // 아이디 중복
            response.setStatus(460);
            return user;
        } else if (result == -2L) { // 이름 중복
            response.setStatus(461);
            return user;
        } else if (result == -3L) { // 이메일 중복
            response.setStatus(462);
            return user;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        user.setUid(result);
        return user;
    }

    @PostMapping(value = "/users/update")
    public User userInfoUpdate(@Valid @RequestBody UserUpdateDto userUpdateDto, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Long uid = (Long) session.getAttribute(USER_ID);
        return loginService.userUpdate(uid, userUpdateDto);
    }

    @GetMapping("/kakao/callback")
    public User loginKaKao(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response){
        KakaoCallbackDto kakaoCallbackDto = new KakaoCallbackDto();
        kakaoCallbackDto.setCode(code);

        User loginUser = oAuthLoginService.login(kakaoCallbackDto);
        log.info("kakao login? {}", loginUser);
        //로그인 실패처리
        if (loginUser.getUid() == null){
            //throw new UserNotFoundException(String.format("kakao email not found, please signup your email is %s", loginUser.getEmail()));
            response.setStatus(404);
            response.setHeader("email", loginUser.getEmail());
        }

        //로그인 성공처리
        HttpSession session = request.getSession();
        log.info("login id: {} session id: {}", loginUser.getLoginId(), session.getId());
        //세션 유지시간 1800초
        session.setMaxInactiveInterval(1800);
        session.setAttribute(USER_ID, loginUser.getUid());

        log.info("session time = {}", session.getMaxInactiveInterval());
        return loginUser;
    }

    @PostMapping("/users/help/pw")
    public String helpPassword(@Valid @RequestBody String loginId){
        try {
            String newPassword = loginService.resetPassWord(loginId);
            return newPassword;
        }catch (RuntimeException e){
            log.error("helpPassword",e);
            throw new UserNotFoundException("LoginId is not valid, plz check again");
        }
    }

//    @GetMapping(value = "/users")
//    public List<Long> usersNamed(@RequestParam("name") String name, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(1800);
//
//        return loginService.getUserIdNamed(name);
//    }
//    @GetMapping(value = "/users")
//    public User userInfo(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(1800);
//
//        return loginService.findOne((Long) session.getAttribute(USER_ID)).get();
//    }


}
