package ddym_corp.quoridor.user.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String password;

    private String email;

    private String name;
}
