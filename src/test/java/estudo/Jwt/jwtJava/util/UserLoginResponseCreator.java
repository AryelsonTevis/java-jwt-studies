package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserResponse;

public class UserLoginResponseCreator {
    public static UserLoginResponse createUserLoginResponse() {
        return UserLoginResponse.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email(UserCreator.createUserToBeSaved().getEmail())
                .token(UserCreator.createUserToBeSaved().getEmail())
                .build();
    }
}
