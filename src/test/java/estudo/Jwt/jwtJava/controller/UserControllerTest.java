package estudo.Jwt.jwtJava.controller;

import estudo.Jwt.jwtJava.DTO.UserLoginRequest;
import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;
import estudo.Jwt.jwtJava.DTO.UserResponse;
import estudo.Jwt.jwtJava.domain.Users;
import estudo.Jwt.jwtJava.secutity.SecurityConfig;
import estudo.Jwt.jwtJava.secutity.SecurityFilter;
import estudo.Jwt.jwtJava.secutity.TokenService;
import estudo.Jwt.jwtJava.servicer.UserService;
import estudo.Jwt.jwtJava.util.*;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@Log4j2
@DisplayName("Tests for User Controller")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {

        BDDMockito.when(userServiceMock.saveUser(ArgumentMatchers.any(UserRegisterRequest.class)))
                .thenReturn(String.valueOf("User registered successfully"));

        BDDMockito.when(userServiceMock.login(ArgumentMatchers.any(UserLoginRequest.class)))
                .thenReturn(UserLoginResponseCreator.createUserLoginResponse());

        BDDMockito.when(userServiceMock.getInfos(ArgumentMatchers.any(Users.class)))
                .thenReturn(UserResponseCreator.createUserResponse());

        BDDMockito.when(userServiceMock.getAllUsers(ArgumentMatchers.any(Users.class)))
                .thenReturn(UserResponseCreator.createListUserResponse());

        BDDMockito.when(userServiceMock.saveUserAdmin(ArgumentMatchers.any(UserRegisterRequest.class), ArgumentMatchers.anyString()))
                .thenReturn(String.valueOf("User Admin registered successfully"));
    }

    @Test
    @DisplayName("Return String when create successful")
    void createUser_ReturnString_WhenCreateSuccessful() {

        UserRegisterRequest userTobeSaved = UserRegisterCreator.createUserRegisterRequest();
        ResponseEntity<HttpStatus> status = userController.createUser(userTobeSaved);

        Assertions.assertThat(status.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(userServiceMock.saveUser(userTobeSaved).compareTo("User registered successfully"));
    }

    @Test
    @DisplayName("Return User Login Response when login successful")
    void createUser_ReturnUserLoginResponse_WhenLoginSuccessful() {

        UserLoginRequest userLoginRequest = UserLoginCreator.createUserLoginRequest();
        ResponseEntity<UserLoginResponse> loginResponse = userController.login(userLoginRequest);

        Assertions.assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(loginResponse.getBody().getToken()).isEqualTo(userLoginRequest.getEmail());
    }

    @Test
    @DisplayName("Return User Response when get infos successful")
    void createUser_ReturnUserResponse_WhenGetInfosSuccessful() {

        Users user = UserCreator.createUserToBeSaved();
        ResponseEntity<UserResponse> userInfos = userController.getInfos(user);

        Assertions.assertThat(userInfos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(userInfos.getBody().getEmail()).isEqualTo(user.getEmail());

        Assertions.assertThat(userInfos.getBody().getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("Return List User Response when get infos successful")
    void createUser_ReturnListUserResponse_WhenGetInfosSuccessful() {

        Users user = UserCreator.createUserAdminToBeSaved();
        ResponseEntity<List<UserResponse>> allUser = userController.getAllUser(user);

        Assertions.assertThat(allUser.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allUser.getBody()).isNotEmpty();

        Assertions.assertThat(allUser.getBody().getFirst().getName()).isEqualTo(UserCreator.createUserToBeSaved().getName());
        Assertions.assertThat(allUser.getBody().getFirst().getEmail()).isEqualTo(UserCreator.createUserToBeSaved().getEmail());

        Assertions.assertThat(allUser.getBody().getLast().getName()).isEqualTo(UserCreator.createOtherUserToBeSaved().getName());
        Assertions.assertThat(allUser.getBody().getLast().getEmail()).isEqualTo(UserCreator.createOtherUserToBeSaved().getEmail());
    }

    @Test
    @DisplayName("Return String when create User admin successful")
    void createUser_ReturnString_WhenCreateUserAdminSuccessful() {

        UserRegisterRequest userAdminToBeSaved = UserRegisterCreator.createUserRegisterRequestAdmin();
        ResponseEntity<HttpStatus> status = userController.createUserAdmin(userAdminToBeSaved, String.valueOf("Password"));

        Assertions.assertThat(status.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(userServiceMock.saveUserAdmin(userAdminToBeSaved,String.valueOf("Password")).compareTo("User Admin registered successfully"));
    }
}
