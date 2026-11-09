package estudo.Jwt.jwtJava.repository;

import estudo.Jwt.jwtJava.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
