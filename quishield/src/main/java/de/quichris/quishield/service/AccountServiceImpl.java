package de.quichris.quishield.service;

import de.quichris.quishield.body.ChangeAccountPasswordRequestBody;
import de.quichris.quishield.body.ChangeAccountUsernameRequestBody;
import de.quichris.quishield.body.LoginRequestBody;
import de.quichris.quishield.entity.Account;
import de.quichris.quishield.entity.Password;
import de.quichris.quishield.exceptions.*;
import de.quichris.quishield.repo.AccountRepo;
import de.quichris.quishield.repo.PasswordRepo;
import de.quichris.quishield.util.KeyStore;
import de.quichris.quishield.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {


    private final AccountRepo accountRepo;
    private final PasswordRepo passwordRepo;

    private final Util utils = new Util();
    private final KeyStore keyStore = new KeyStore();

    private String getToken(Account acc) {
        String tkk = utils.genToken();
        try {
            Account member1 = accountRepo.findByToken(tkk).orElseThrow(AccountNotFound::new);
            return getToken(acc);
        } catch (AccountNotFound e) {
            return tkk;
        }
    }

    private void checkIfUsernameExist(String username) throws AccountDuplicate {
        try {
            Account member1 = accountRepo.findByUsername(username).orElseThrow(AccountNotFound::new);
            throw new AccountDuplicate("The username " + username + " already exists!");
        } catch (AccountNotFound e) {
        }
    }

    @Override
    public Account saveAccount(Account account) throws AccountDuplicate {
        String token = getToken(account);
        account.setToken(token);
        try {
            checkIfUsernameExist(account.getUsername());
            Account member1 = accountRepo.findByEmail(account.getEmail()).orElseThrow(AccountNotFound::new);
            throw new AccountDuplicate("Account with the email " + account.getEmail() + " does already exist!");
        } catch (AccountNotFound e) {
            account.setPassword(utils.sha256(account.getPassword()));
            return accountRepo.save(account);
        }
    }

    @Override
    public Account accountLogin(LoginRequestBody loginRequestBody) throws AccountNotFound, WrongPassword {
        try {
            Account acc = accountRepo.findByEmail(loginRequestBody.getEmail()).orElseThrow(() -> new AccountNotFound("Wrong Email or Password!"));
            if (Objects.equals(acc.getPassword(), loginRequestBody.getPassword())) {
                return acc;
            } else {
                throw new WrongPassword("Wrong Email or Password!");
            }
        } catch (AccountNotFound e) {
            Account acc = accountRepo.findByUsername(loginRequestBody.getUsername()).orElseThrow(() -> new AccountNotFound("Wrong Email or Password!"));
            if (Objects.equals(acc.getPassword(), loginRequestBody.getPassword())) {
                return acc;
            } else {
                throw new WrongPassword("Wrong Email or Password!");
            }
        }

    }

    @Override
    public Account updateAccountUsername(ChangeAccountUsernameRequestBody account, String token) throws AccountNotFound, WrongUsername {
        Account tmp = accountRepo.findByToken(token).orElseThrow(AccountNotFound::new);
        if (Objects.equals(tmp.getUsername(), account.getOld_username())) {
            tmp.setUsername(account.getUsername());
            accountRepo.save(tmp);
            return tmp;
        } else {
            throw new WrongUsername("The provided old Username was wrong");
        }
    }

    @Override
    public Account updateAccountPassword(ChangeAccountPasswordRequestBody account, String token) throws AccountNotFound, WrongPassword {
        Account tmp = accountRepo.findByToken(token).orElseThrow(AccountNotFound::new);

        if (Objects.equals(tmp.getPassword(), account.getOld_password())) {
            tmp.setPassword(account.getNew_password());
            String tmptoken = getToken(tmp);
            List<Password> passwords = passwordRepo.getPasswordsByOwner(tmp.getToken());
            passwords.forEach(password -> {
                password.setOwner(tmptoken);
                passwordRepo.save(password);
            });
            tmp.setToken(tmptoken);
            accountRepo.save(tmp);
            return tmp;
        } else {
            throw new WrongPassword("Wrong Password!");
        }

    }

    @Override
    public Key getKey() {
        return keyStore.getKey();
    }

    @Override
    public List<Password> getPasswords(String token) throws AccountNotFound {
        Account acc = getAccount(token);
        return passwordRepo.getPasswordsByOwner(token);
    }

    @Override
    public Account getAccount(String token) throws AccountNotFound {
        return accountRepo.findByToken(token).orElseThrow(AccountNotFound::new);
    }

    @Override
    public Password addPassword(Password password) {
        password.setPassword(utils.EncryptPassword(password.getPassword()));
        passwordRepo.save(password);
        return password;
    }

    @Override
    public Password getPassword(String token, String password_id) throws PasswordNotFound, NotAuthorized {
        Password tmp = passwordRepo.getPasswordById(password_id).orElseThrow(PasswordNotFound::new);
        if (Objects.equals(tmp.getOwner(), token)) {
            tmp.setPassword(utils.DecryptPassword(tmp.getPassword()));

            return tmp;
        } else {
            throw new NotAuthorized("Token is not Authorized");
        }
    }

    @Override
    public Password updatePassword(Password password, String token) throws PasswordNotFound, NotAuthorized {
        Password password1 = passwordRepo.getPasswordById(password.getId()).orElseThrow(PasswordNotFound::new);
        if (!Objects.equals(password1.getOwner(), token)) {
            throw new NotAuthorized("Token is not Authorized");
        }
        password1.setPassword(utils.EncryptPassword(password.getPassword()));
        password1.setEmail(password.getEmail());
        password1.setUsername(password.getUsername());
        password1.setName(password.getName());
        passwordRepo.save(password1);
        return password1;
    }
}
