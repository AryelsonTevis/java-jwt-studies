package estudo.Jwt.jwtJava.handler;

import estudo.Jwt.jwtJava.exception.IllegalArgumentExceptionDetails;
import estudo.Jwt.jwtJava.exception.UsernameNotFoundExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentExceptionDetails> handleIllegalArgumentException(IllegalArgumentException iae){
        return new ResponseEntity<>(IllegalArgumentExceptionDetails.builder()
                .title("Illegal Argument Exception")
                .status(HttpStatus.BAD_REQUEST.value())
                .details(iae.getMessage())
                .developerMessage("Email already registered")
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<UsernameNotFoundExceptionDetails> handleUsernameNotFoundException(UsernameNotFoundException unfe){
        return new ResponseEntity<>(UsernameNotFoundExceptionDetails.builder()
                .title("Username Not Found")
                .status(HttpStatus.EXPECTATION_FAILED.value())
                .details(unfe.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.EXPECTATION_FAILED);
    }
}
