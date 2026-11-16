package estudo.Jwt.jwtJava.controller;

import estudo.Jwt.jwtJava.DTO.UserLoginRequest;
import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;
import estudo.Jwt.jwtJava.servicer.UserService;
import estudo.Jwt.jwtJava.util.UserLoginCreator;
import estudo.Jwt.jwtJava.util.UserRegisterCreator;
import estudo.Jwt.jwtJava.util.UserResponseCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Log4j2
@DisplayName("Test for User Controller")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(userServiceMock.saveUser(ArgumentMatchers.any(UserRegisterRequest.class))).thenReturn(null);
    }

    @Test
    @DisplayName("Return Http Status ok when create successful")
    void create_ReturnHttpStatusOk_WhenCreateSuccessful() {
        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();
        HttpStatusCode statusCode = userController.createUser(userToBeSaved).getStatusCode();

        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.CREATED);
    }
}
