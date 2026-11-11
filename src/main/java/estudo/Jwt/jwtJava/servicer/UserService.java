package estudo.Jwt.jwtJava.servicer;

import estudo.Jwt.jwtJava.DTO.UserLoginRequest;
import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;
import estudo.Jwt.jwtJava.DTO.UserResponse;
import estudo.Jwt.jwtJava.domain.UserRole;
import estudo.Jwt.jwtJava.domain.Users;
import estudo.Jwt.jwtJava.repository.UserRepository;
import estudo.Jwt.jwtJava.secutity.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public String saveUser(UserRegisterRequest userCreatPost) {

        Users userToBeSaved = Users.builder()
                .name(userCreatPost.getName())
                .email(userCreatPost.getEmail())
                .password(passwordEncoder.encode(userCreatPost.getPassword()))
                .role(UserRole.USER)
                .build();

        try {

            userRepository.save(userToBeSaved);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error to save user");
        }

        return "User registered successfully";
    }

    public UserLoginResponse login(UserLoginRequest userLoginPost) {

        var login = new UsernamePasswordAuthenticationToken(userLoginPost.getEmail(), userLoginPost.getPassword());

        var auth = authenticationManager.authenticate(login);

        Users user = (Users) auth.getPrincipal();

        var token = tokenService.generateToken(user);

        return UserLoginResponse.builder().name(user.getName()).email(user.getEmail()).token(token).build();

    }

    public UserResponse getInfos(Users user) {
        return UserResponse.builder().name(user.getName()).email(user.getEmail()).build();
    }

    public List<UserResponse> getAllUsers(Users user) {
        return userRepository.findAll().stream()
                .map(users ->  UserResponse.builder().name(users.getName()).email(users.getEmail()).build()).toList();
    }
}
