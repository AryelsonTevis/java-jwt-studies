package estudo.Jwt.jwtJava.util;

import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;

public class UserRegisterCreator {
    public static UserRegisterRequest createUserRegisterRequest() {
        return UserRegisterRequest.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email(UserCreator.createUserToBeSaved().getEmail())
                .password(UserCreator.createUserToBeSaved().getPassword())
                .build();
    }
    public static UserRegisterRequest createUserRegisterRequestWithInvalidEmail() {
        return UserRegisterRequest.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email("teste")
                .password(UserCreator.createUserToBeSaved().getPassword())
                .build();
    }

    public static UserRegisterRequest createUserRegisterRequestAdmin() {
        return UserRegisterRequest.builder()
                .name(UserCreator.createUserAdminToBeSaved().getName())
                .email(UserCreator.createUserAdminToBeSaved().getEmail())
                .password(UserCreator.createUserAdminToBeSaved().getPassword())
                .build();
    }
}
