package estudo.Jwt.jwtJava.controller;

import estudo.Jwt.jwtJava.DTO.UserLoginRequest;
import estudo.Jwt.jwtJava.DTO.UserLoginResponse;
import estudo.Jwt.jwtJava.DTO.UserRegisterRequest;
import estudo.Jwt.jwtJava.DTO.UserResponse;
import estudo.Jwt.jwtJava.domain.Users;
import estudo.Jwt.jwtJava.servicer.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/auth/register")
    @Operation(summary = "Create User")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userService.saveUser(userRegisterRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login User")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return new ResponseEntity<>(userService.login(userLoginRequest), HttpStatus.OK);
    }

    @GetMapping(path = "/me")
    @Operation(summary = "Get user information from token")
    public ResponseEntity<UserResponse> getInfos(@AuthenticationPrincipal Users user) {
        return new ResponseEntity<>(userService.getInfos(user), HttpStatus.OK);
    }

    @GetMapping(path = "/admin")
    @Operation(summary = "Get all user information with token admin")
    public ResponseEntity<List<UserResponse>> getAllUser(@AuthenticationPrincipal Users user) {
        return new ResponseEntity<>(userService.getAllUsers(user), HttpStatus.OK);
    }
}
