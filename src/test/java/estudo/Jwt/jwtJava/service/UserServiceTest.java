package estudo.Jwt.jwtJava.service;

import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;
import estudo.Jwt.jwtJava.domain.Users;
import estudo.Jwt.jwtJava.repository.UserRepository;
import estudo.Jwt.jwtJava.servicer.UserService;
import estudo.Jwt.jwtJava.util.UserCreator;
import estudo.Jwt.jwtJava.util.UserRegisterCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@DisplayName("Tests for User Service")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.save(UserCreator.createUserToBeSaved())).thenReturn(UserCreator.createUserToBeSaved());

    }

    @Test
    @DisplayName("Save Return User registered successfully when Successful")
    public void saveUser_ReturnUserRegisteredSuccessfully_WhenSuccessful() {

        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();

        String response = userService.saveUser(userToBeSaved);

        Assertions.assertThat(response).isNotEmpty();

        Assertions.assertThat(response).isEqualTo("User registered successfully");
    }

    @Test
    @DisplayName("Save return IllegalArgumentException when user email is blank")
    public void save_ReturnIllegalArgumentException_WhenUserEmailIsBlank() {

        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();
        userToBeSaved.setEmail(null);

        Assertions.assertThatThrownBy(()->userService.saveUser(userToBeSaved)).isInstanceOf(IllegalArgumentException.class).hasMessage("User email cannot be null or blank");

    }

    @Test
    @DisplayName("Save throw IllegalArgumentException when user email invalid")
    public void saveUser_ReturnIllegalArgumentException_WhenUserEmailIsInvalid() {

        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();
        userToBeSaved.setEmail("teste");

        Assertions.assertThatThrownBy(()->userService.saveUser(userToBeSaved)).isInstanceOf(IllegalArgumentException.class).hasMessage("User email is invalid");

    }

    @Test
    @DisplayName("Save throw IllegalArgumentException when user password is invalid")
    public void saveUser_ReturnIllegalArgumentException_WhenUserPasswordIsInvalid() {

        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();
        userToBeSaved.setPassword("teste");

        Assertions.assertThatThrownBy(()->userService.saveUser(userToBeSaved)).isInstanceOf(IllegalArgumentException.class).hasMessage("User password is not strong. The password must be 8 character, include one special character, and one uppercase letter");

    }

    @Test
    @DisplayName("Save throw IllegalArgumentException when user password is null")
    public void saveUser_ReturnIllegalArgumentException_WhenUserPasswordIsNull() {

        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();
        userToBeSaved.setPassword(null);

        Assertions.assertThatThrownBy(()->userService.saveUser(userToBeSaved)).isInstanceOf(IllegalArgumentException.class).hasMessage("User password cannot be null or empty");

    }

    @Test
    @DisplayName("Save throw IllegalArgumentException when user password is blank")
    public void saveUser_ReturnIllegalArgumentException_WhenUserPasswordIsBlank() {

        UserRegisterRequest userToBeSaved = UserRegisterCreator.createUserRegisterRequest();
        userToBeSaved.setPassword("");

        Assertions.assertThatThrownBy(()->userService.saveUser(userToBeSaved)).isInstanceOf(IllegalArgumentException.class).hasMessage("User password cannot be null or empty");

    }
}
