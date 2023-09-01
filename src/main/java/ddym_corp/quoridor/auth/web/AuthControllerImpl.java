package ddym_corp.quoridor.auth.web;

import ddym_corp.quoridor.auth.domain.login.LoginServiceImpl;
import ddym_corp.quoridor.user.User;
import lombok.RequiredArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import static ddym_corp.quoridor.auth.web.SessionConst.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final LoginServiceImpl loginService;
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
    public User create(@Valid @RequestBody SignupDto signupDto) {
        User user = new User(signupDto.getLoginId(), signupDto.getPassword());
        user.setName(signupDto.getName()); user.setEmail(signupDto.getEmail());
        user.setScore(0); user.setTotalGames(0); user.setWinGames(0);

        Long result = loginService.join(user);
        user.setUid(result);
        return user;
    }

    @PostMapping(value = "/users/update")
    public User userInfoUpdate(@Valid @RequestBody UserUpdateDto userUpdateDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long uid = (Long) session.getAttribute(USER_ID);
        return loginService.userUpdate(uid, userUpdateDto);
    }

//    @GetMapping(value = "/users")
//    public List<Long> usersNamed(@RequestParam("name") String name, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(1800);
//
//        return loginService.getUserIdNamed(name);
//    }

}
