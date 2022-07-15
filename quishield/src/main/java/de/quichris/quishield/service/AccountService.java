package de.quichris.quishield.service;

import de.quichris.quishield.body.ChangeAccountPasswordRequestBody;
import de.quichris.quishield.body.LoginRequestBody;
import de.quichris.quishield.entity.Account;
import de.quichris.quishield.entity.Password;
import de.quichris.quishield.exceptions.*;

import java.security.Key;
import java.util.List;

public interface AccountService {

    Account saveAccount(Account account) throws AccountDuplicate;

    Account accountLogin(LoginRequestBody loginRequestBody) throws AccountNotFound, WrongPassword;


    Account getAccount(String token) throws AccountNotFound;

    Account updateAccountPassword(ChangeAccountPasswordRequestBody account, String token) throws AccountNotFound, WrongPassword;

    Key getKey();

    List<Password> getPasswords(String token) throws AccountNotFound;

    Password addPassword(Password password);

    Password getPassword(String token, String password_id) throws PasswordNotFound, NotAuthorized;


    Password updatePassword(Password password, String token) throws PasswordNotFound, NotAuthorized;
}
