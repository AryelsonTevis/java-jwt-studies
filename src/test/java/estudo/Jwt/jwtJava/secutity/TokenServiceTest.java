package estudo.Jwt.jwtJava.secutity;

import com.auth0.jwt.exceptions.JWTVerificationException;
import estudo.Jwt.jwtJava.domain.Users;
import estudo.Jwt.jwtJava.util.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

@Log4j2
@DisplayName("Tests for Token Service")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", "Test secret");
    }

    @Test
    @DisplayName("Generate token return string token when successfully")
    public void generateToken_ReturnStringToken_WhenSuccessfully() {

        Users userTest = UserCreator.createUserToBeSaved();

        String token = tokenService.generateToken(userTest);

        Assertions.assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("Validate token return Email when token is valid")
    public void validateToken_ReturnEmail_WhenTokenIsValid() {

        Users userTest = UserCreator.createUserToBeSaved();

        String token = tokenService.generateToken(userTest);

        String tokenReturnedByValidate = tokenService.validateToken(token);

        Assertions.assertThat(tokenReturnedByValidate).isNotBlank().isEqualTo(userTest.getEmail());
    }

    @Test
    @DisplayName("Validate token throw JWTVerificationException when token is not valid")
    public void validateToken_ThrowJWTVerificationException_WhenTokenIsNotValid() {

        Assertions.assertThatThrownBy(()->tokenService.validateToken("asndfuashbdioasnd")).isInstanceOf(JWTVerificationException.class).hasMessage("The token is not valid");

    }

    @Test
    @DisplayName("Validate token throw JWTVerificationException when token is expired")
    public void validateToken_Throw_WhenTokenIsExpired() throws InterruptedException {

        Users userTest = UserCreator.createUserToBeSaved();

        String token = tokenService.generateFastExpiringToken(userTest);

        Thread.sleep(3000);

        Assertions.assertThatThrownBy(()->tokenService.validateToken(token)).isInstanceOf(JWTVerificationException.class).hasMessage("The token has expired");
    }
}