package kakao99.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @Email
    private String email;

    @Min(8)
    private String password;

    @NotBlank
    private String position;

}
