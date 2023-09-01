package ddym_corp.quoridor.user.bin;

import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;

//import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaUserRepository implements UserRepository {
    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Optional<User> findByName(String name) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findByuID(Long uID) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void clearAll() {

    }
//    private final EntityManager em;
//    public JpaUserRepository(EntityManager em) {
//        this.em = em;
//    }
//    @Override
//    public User save(User member) {
//        em.persist(member);
//        return member;
//    }
//    @Override
//    public Optional<User> findByuID(Long uID) {
//        User member = em.find(User.class, uID);
//        return Optional.ofNullable(member);
//    }
//    public List<User> findAll() {
//        return em.createQuery("select u from User u", User.class)
//                .getResultList();
//    }
//    @Override
//    public Optional<User> findByLoginId(String loginId) {
//        List<User> result = em.createQuery("select u from User u where u.loginId = :loginId", User.class)
//                .setParameter("loginId", loginId)
//                .getResultList();
//        return result.stream().findAny();
//    }
}