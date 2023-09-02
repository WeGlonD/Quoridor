package ddym_corp.quoridor.user;

import lombok.*;

//import javax.persistence.*;


@Data
//@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String loginId;
    private String email;
    private String name;
    private String password;
    private Integer score;
    private Integer totalGames;
    private Integer winGames;
    public User(String id, String pw) {
        this.loginId = id;
        this.password = pw;
    }

    public User(String email) {
        this.email = email;
    }
}
