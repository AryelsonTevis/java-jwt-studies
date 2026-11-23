package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.DTO.UserLoginRequest;
import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;

public class UserLoginCreator {
    public static UserLoginRequest createUserLoginRequest() {
        return UserLoginRequest.builder()
                .email(UserCreator.createUserToBeSaved().getEmail())
                .password(UserCreator.createUserToBeSaved().getPassword())
                .build();
    }
    public static UserLoginRequest createUserAdminLoginRequest() {
        return UserLoginRequest.builder()
                .email(UserCreator.createUserAdminToBeSaved().getEmail())
                .password(UserCreator.createUserAdminToBeSaved().getPassword())
                .build();
    }
}
