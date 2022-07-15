package de.quichris.quishield.repo;

import de.quichris.quishield.entity.Account;
import de.quichris.quishield.exceptions.AccountNotFound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,String> {
    Optional<Account> findByEmail(String email) throws AccountNotFound;

    Optional<Account> findByToken(String token);

    Optional<Account> findByUsername(String username);

}
