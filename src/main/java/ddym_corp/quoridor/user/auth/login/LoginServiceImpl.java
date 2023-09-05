package ddym_corp.quoridor.user.auth.login;

import ddym_corp.quoridor.user.auth.web.dto.UserUpdateDto;
import ddym_corp.quoridor.ranking.domain.service.RankingService;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        validateDuplicateMember(user);
        userRepository.save(user);
        rankingService.join(user.getUid());
        return user.getUid();
    }

    private void validateDuplicateMember(User user) {
        userRepository.findByLoginId(user.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 ID 입니다.");
                });
        userRepository.findByName(user.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이름 입니다.");
                });
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
            throw new IllegalStateException("유효하지 않은 회원입니다.");
        }
    }

    public Optional<User> getUserNamed(String name) {
        return userRepository.findByName(name);
    }
}
