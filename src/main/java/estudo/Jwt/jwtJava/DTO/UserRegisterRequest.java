package estudo.Jwt.jwtJava.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "This is the user name ")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Schema(description = "This is the user email", example = "exemple@test.com")
    @Email(regexp = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$",message = "Invalid E-mail")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Schema(description = "This is the user password ")
    private String password;
}
