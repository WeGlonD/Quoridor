package ddym_corp.quoridor.user.auth.login;

import ddym_corp.quoridor.user.auth.web.dto.UserUpdateDto;
import ddym_corp.quoridor.ranking.domain.service.RankingService;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl {
    private final UserRepository userRepository;
    private final RankingService rankingService;

    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Long join(User user){
        // 같은 이름이 있는 중복 희원X
        int redundant = validateDuplicateMember(user);
        if (redundant < 0) return (long) redundant;
        userRepository.save(user);
        rankingService.join(user.getUid());
        return user.getUid();
    }

    private int validateDuplicateMember(User user) {
        Optional<User> byLoginId = userRepository.findByLoginId(user.getLoginId());
        if(byLoginId.isPresent()) return -1;

        Optional<User> byName = userRepository.findByName(user.getName());
        if(byName.isPresent()) return -2;

        //이메일도 추가
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail.isEmpty()) return -3;

        return 1;
    }

    public Optional<User> findOne(Long uid) {
        return userRepository.findByuID(uid);
    }

    public String getName(Long uid){ return findOne(uid).get().getName();}

    public Integer getScore(Long uid){ return findOne(uid).get().getScore();}

    public User userUpdate(Long uid, UserUpdateDto userUpdateDto){
        Optional<User> optUser = findOne(uid);
        if(optUser.isPresent()) {
            User preUser = optUser.get();
            if (userUpdateDto.getEmail() != null)
                preUser.setEmail(userUpdateDto.getEmail());
            if (userUpdateDto.getName() != null) {
                userRepository.findByName(userUpdateDto.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 이름 입니다.");
                        });
                preUser.setName(userUpdateDto.getName());
            }
            if (userUpdateDto.getPassword() != null)
                preUser.setPassword(userUpdateDto.getPassword());
            return userRepository.update(preUser);
        }
        else {
            throw new RuntimeException("유효하지 않은 회원입니다.");
        }
    }

    public Optional<User> getUserNamed(String name) {
        return userRepository.findByName(name);
    }

    public String resetPassWord(String loginId){
        Optional<User> byuID = userRepository.findByLoginId(loginId);
        if(byuID.isPresent()){
            User user = byuID.get();
            String newPassword = UUID.randomUUID().toString().substring(0,10);
            user.setPassword(newPassword);
            userRepository.update(user);
            return newPassword;
        }else{
            throw new RuntimeException("not valid userid from resetPassword");
        }
    }
}
