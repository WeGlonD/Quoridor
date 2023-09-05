package ddym_corp.quoridor.user.auth.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@Data
public class SignupDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;

    public SignupDto() {
    }
}
