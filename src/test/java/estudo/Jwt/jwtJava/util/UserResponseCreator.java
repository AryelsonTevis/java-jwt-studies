package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserResponse;

public class UserResponseCreator {
    public static UserResponse createUserResponse() {
        return UserResponse.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email(UserCreator.createUserToBeSaved().getEmail())
                .build();
    }
}
