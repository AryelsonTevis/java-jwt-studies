package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.domain.UserRole;
import estudo.Jwt.jwtJava.domain.Users;

public class UserCreator {
    public static Users createUserToBeSaved() {
        return Users.builder()
                .name("Teste")
                .email("Teste@gmail.com")
                .password("Azirael123@")
                .role(UserRole.USER)
                .build();
    }
    public static Users createUserAdminToBeSaved() {
        return Users.builder()
                .name("Dono")
                .email("Owner@gmail.com")
                .password("BaalZebu123@")
                .role(UserRole.ADMIN)
                .build();
    }
    public static Users createOtherUserToBeSaved() {
        return Users.builder()
                .name("Astaroth")
                .email("Robson@gmail.com")
                .password("Astaroth123@")
                .role(UserRole.USER)
                .build();
    }
}
