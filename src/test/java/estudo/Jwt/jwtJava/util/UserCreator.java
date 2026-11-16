package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.domain.UserRole;
import estudo.Jwt.jwtJava.domain.Users;

public class UserCreator {
    public static Users createUserToBeSaved() {
        return Users.builder()
                .name("Teste")
                .email("Teste@gmail.com")
                .password("Azirael")
                .role(UserRole.USER)
                .build();
    }
}
