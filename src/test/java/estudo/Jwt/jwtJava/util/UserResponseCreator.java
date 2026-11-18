package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserResponse;

import java.util.List;

public class UserResponseCreator {
    public static UserResponse createUserResponse() {
        return UserResponse.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email(UserCreator.createUserToBeSaved().getEmail())
                .build();
    }
    public static List<UserResponse> createListUserResponse() {
        List<UserResponse> listUser = List.of(UserResponse.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email(UserCreator.createUserToBeSaved().getEmail())
                .build(),
        UserResponse.builder()
                 .name(UserCreator.createOtherUserToBeSaved().getName())
                 .email(UserCreator.createOtherUserToBeSaved().getEmail())
                 .build());

        return listUser;
    }
}
