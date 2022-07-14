package de.quichris.quishield.service;

import de.quichris.quishield.entity.Account;
import de.quichris.quishield.exceptions.AccountDuplicate;
import de.quichris.quishield.exceptions.AccountNotFound;
import de.quichris.quishield.exceptions.WrongPassword;
import de.quichris.quishield.repo.AccountRepo;
import de.quichris.quishield.repo.PasswordRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final PasswordRepo passwordRepo;

    @Override
    public Account saveAccount(Account account) throws AccountDuplicate {
        try {
            Account member1 = accountRepo.findByEmail(account.getEmail()).orElseThrow(AccountNotFound::new);
            throw new AccountDuplicate("Account with the email "+ account.getEmail()+" does already exist!");
        } catch (AccountNotFound e) {
            return accountRepo.save(account);
        }
    }

    @Override
    public Account accountLogin(String email, String password) throws AccountNotFound, WrongPassword {
        Account acc = accountRepo.findByEmail(email).orElseThrow(AccountNotFound::new);
        if (Objects.equals(acc.getPassword(), password)) {
            return acc;
        } else {
            throw new WrongPassword();
        }
    }
}
