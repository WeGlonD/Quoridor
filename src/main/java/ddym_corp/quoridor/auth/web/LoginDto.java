package ddym_corp.quoridor.auth.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class LoginDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public LoginDto() {
    }
}
