package ddym_corp.quoridor.auth.web;

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
