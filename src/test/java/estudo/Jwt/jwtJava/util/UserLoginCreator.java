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
}
