package de.quichris.quishield.repo;

import de.quichris.quishield.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRepo extends JpaRepository<Password,String> {
    Optional<Password> findById(String id);

}
