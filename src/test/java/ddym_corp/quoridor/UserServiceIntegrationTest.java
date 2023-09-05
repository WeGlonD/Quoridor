package ddym_corp.quoridor;

import ddym_corp.quoridor.user.auth.login.LoginServiceImpl;
import ddym_corp.quoridor.user.auth.web.dto.UserUpdateDto;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@Transactional //커밋 안해서 aftereach 안해도 db 롤백됨.
public class UserServiceIntegrationTest {

    @Autowired
    LoginServiceImpl loginService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        userRepository.clearAll();
    }

    @Test
    void 회원가입() {
        //given
        User user = new User("didwoah7","woah1234");
        user.setName("spring1234");
        user.setEmail("sss");
        user.setScore(0);

        //when
        Long saveId = loginService.join(user);

        //then
        User findMember = loginService.findOne(saveId).get();// ctrl alt v
        assertThat(user.getName()).isEqualTo(findMember.getName()); // shift enter
    }
    @Test
    public void 중복_회원_예외() {
        //given
        User member1 = new User("dirtfy", "alstjr1234");
        member1.setName("minseok");
        member1.setEmail("ssss");
        member1.setScore(0);

        User member2 = new User("dirtfy", "ehdrl1234"); // shift f6
        member2.setName("donggi");
        member2.setEmail("sssss");
        member2.setScore(0);

        //when
        loginService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> loginService.join(member2));// 해당 예외가 나오면 run성공, 안나오면 run실패
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 ID 입니다.");
    }

    @Test
    public void 회원_정보_수정() {
        User member1 = new User("oz9911", "ehdrl1234");
        member1.setName("donggi");
        member1.setEmail("sss");
        member1.setScore(0);

        Long uid = loginService.join(member1);
        loginService.login("oz9911", "ehdrl1234");
        User res = loginService.userUpdate(uid, new UserUpdateDto("ehdrl1234","oz9911@naver.com","김동기"));

        assertThat(res.getEmail()).isEqualTo("oz9911@naver.com");
    }
}
