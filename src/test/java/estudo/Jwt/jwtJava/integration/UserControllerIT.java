package estudo.Jwt.jwtJava.integration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import estudo.Jwt.jwtJava.DTO.UserLoginRequest;
import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;
import estudo.Jwt.jwtJava.DTO.UserResponse;
import estudo.Jwt.jwtJava.domain.Users;
import estudo.Jwt.jwtJava.repository.UserRepository;
import estudo.Jwt.jwtJava.secutity.SecurityConfig;
import estudo.Jwt.jwtJava.secutity.SecurityFilter;
import estudo.Jwt.jwtJava.secutity.TokenService;
import estudo.Jwt.jwtJava.util.UserLoginCreator;
import estudo.Jwt.jwtJava.util.UserRegisterCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@Log4j2
@DisplayName("Test user Controller integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private TokenService tokenService;

    @Autowired
    @Qualifier(value = "testRestTemplate")
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "permissionUserAdmin", "Test permission", String.class);
    }


    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplate")
        public TestRestTemplate testRestTemplate(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplate = new RestTemplateBuilder().baseUri("http://localhost:" + port);
            return new TestRestTemplate(restTemplate);
        }
    }


    @Test
    @DisplayName("Returns HttpsStatus created when save successful")
    void save_ReturnsHttpsStatusCreated_WhenSuccessful() {
        UserRegisterRequest userRegisterRequest = UserRegisterCreator.createUserRegisterRequest();

        ResponseEntity<String> stringResponseEntity = testRestTemplate.postForEntity("/users/auth/register", userRegisterRequest, String.class);

        Assertions.assertThat(stringResponseEntity).isNotNull();


        Assertions.assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Return User Login Response when login successful")
    void login_ReturnsUserLoginResponse_WhenSuccessful() {
        UserRegisterRequest userRegisterRequest = UserRegisterCreator.createUserRegisterRequest();

        testRestTemplate.postForEntity("/users/auth/register", userRegisterRequest, String.class);

        UserLoginRequest userLoginRequest = UserLoginCreator.createUserLoginRequest();

        ResponseEntity<UserLoginResponse> userLoginResponse = testRestTemplate.postForEntity("/users/auth/login", userLoginRequest, UserLoginResponse.class);

        Assertions.assertThat(userLoginResponse).isNotNull();

        Assertions.assertThat(userLoginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Return User Response when get info successful")
    void getInfos_ReturnsUserResponse_WhenSuccessful() {
        UserRegisterRequest userRegisterRequest = UserRegisterCreator.createUserRegisterRequest();

        testRestTemplate.postForEntity("/users/auth/register", userRegisterRequest, String.class);

        UserLoginRequest userLoginRequest = UserLoginCreator.createUserLoginRequest();

        ResponseEntity<UserLoginResponse> userLoginResponse = testRestTemplate.postForEntity("/users/auth/login", userLoginRequest, UserLoginResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLoginResponse.getBody().getToken());
        ResponseEntity<UserResponse> response = testRestTemplate.exchange("/users/me", HttpMethod.GET, new HttpEntity<>(headers), UserResponse.class);

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody().getName()).isEqualTo(userRegisterRequest.getName());
        Assertions.assertThat(response.getBody().getEmail()).isEqualTo(userRegisterRequest.getEmail());
    }

    @Test
    @DisplayName("Return HttpsStatus created when created User Admin successful")
    void createdUserAdmin_ReturnsHttpsStatusCreated_WhenSuccessful() {
        UserRegisterRequest userRegisterRequestAdmin = UserRegisterCreator.createUserRegisterRequestAdmin();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Permission", "Test permission");

        ResponseEntity<String> response = testRestTemplate.exchange("/users/auth/register/admin", HttpMethod.POST, new HttpEntity<>(userRegisterRequestAdmin, headers), String.class);

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Return List all of user response when get all user successful")
    void getAllUser_ReturnsListAllOfUserResponse_WhenSuccessful() {

        testRestTemplate.postForEntity("/users/auth/register", UserRegisterCreator.createUserRegisterRequest(), String.class);

        UserRegisterRequest userRegisterRequestAdmin = UserRegisterCreator.createUserRegisterRequestAdmin();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Permission", "Test permission");

        testRestTemplate.exchange("/users/auth/register/admin", HttpMethod.POST, new HttpEntity<>(userRegisterRequestAdmin, headers), String.class);

        UserLoginRequest login = UserLoginCreator.createUserAdminLoginRequest();

        ResponseEntity<UserLoginResponse> userLoginResponse = testRestTemplate.postForEntity("/users/auth/login", login, UserLoginResponse.class);

        headers.setBearerAuth(userLoginResponse.getBody().getToken());

        ResponseEntity<List<UserResponse>> listResponseAdmin = testRestTemplate.exchange("/users/admin", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<UserResponse>>() {
        });

        Assertions.assertThat(listResponseAdmin).isNotNull();

        Assertions.assertThat(listResponseAdmin.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse userToCompare = listResponseAdmin.getBody().getFirst();

        Assertions.assertThat(userToCompare.getName()).isEqualTo(UserRegisterCreator.createUserRegisterRequest().getName());

        Assertions.assertThat(userToCompare.getEmail()).isEqualTo(UserRegisterCreator.createUserRegisterRequest().getEmail());

        userToCompare = listResponseAdmin.getBody().getLast();

        Assertions.assertThat(userToCompare.getName()).isEqualTo(userRegisterRequestAdmin.getName());

        Assertions.assertThat(userToCompare.getEmail()).isEqualTo(userRegisterRequestAdmin.getEmail());

    }

    @Test
    @DisplayName("Returns HttpsStatus Bad Request when save is not successful")
    void save_ReturnsHttpsStatusBadRequest_WhenIsNotSuccessful() {

        UserRegisterRequest userRegisterRequest = UserRegisterCreator.createUserRegisterRequestWithInvalidEmail();

        ResponseEntity<IllegalArgumentException> responseEntity = testRestTemplate.postForEntity("/users/auth/register", userRegisterRequest, IllegalArgumentException.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Return HttpsStatus EXPECTATION_FAILED when login is not successful")
    void login_ReturnsHttpsStatusExpectationFailed_WhenIsNotSuccessful() {

        UserLoginRequest userLoginRequest = UserLoginCreator.createUserLoginRequest();

        ResponseEntity<IllegalArgumentException> responseEntity = testRestTemplate.postForEntity("/users/auth/login", userLoginRequest, IllegalArgumentException.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    @DisplayName("Return HttpsStatus Forbidden when get info is not successful")
    void getInfos_ReturnsHttpsStatusForbidden_WhenIsNotSuccessful() {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth("");

        ResponseEntity<String> response = testRestTemplate.exchange("/users/me", HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Return HttpsStatus Bad Request when created is not successful")
    void createdUserAdmin_ReturnsHttpsStatusBadRequest_WhenIsNSuccessful() {
        UserRegisterRequest userRegisterRequestAdmin = UserRegisterCreator.createUserRegisterRequestAdmin();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Permission", "");

        ResponseEntity<IllegalArgumentException> response = testRestTemplate.exchange("/users/auth/register/admin", HttpMethod.POST, new HttpEntity<>(userRegisterRequestAdmin, headers), IllegalArgumentException.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Return error when get all user  is notsuccessful")
    void getAllUser_ReturnsError_WhenSuccessful() {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth("lkjklklasd");

        ResponseEntity<String> response = testRestTemplate.exchange("/users/admin", HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
