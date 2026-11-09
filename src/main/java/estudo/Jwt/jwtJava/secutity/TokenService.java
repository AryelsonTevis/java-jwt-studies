package estudo.Jwt.jwtJava.secutity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import estudo.Jwt.jwtJava.domain.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${API_SECURITY_TOKEN_SECRET}")
    private String secret;

    public String generateToken(Users user){

        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("API SECURITY")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            return token;
        }
        catch (JWTCreationException exception){

            throw new RuntimeException("Error while generating JWT token", exception);
        }
    }

    public String validateToken(String token){

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("API SECURITY")
                    .build()
                    .verify(token)
                    .getSubject();

        }
        catch ( JWTVerificationException exception){

            return "";
        }
    }

    private Instant genExpirationDate(){
        return Instant.now().plus(10, ChronoUnit.DAYS);
    }
}
