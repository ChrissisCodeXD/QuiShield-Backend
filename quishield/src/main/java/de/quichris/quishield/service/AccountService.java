package de.quichris.quishield.service;

import de.quichris.quishield.entity.Account;
import de.quichris.quishield.exceptions.AccountDuplicate;
import de.quichris.quishield.exceptions.AccountNotFound;
import de.quichris.quishield.exceptions.WrongPassword;

public interface AccountService {

    Account saveAccount(Account account) throws AccountNotFound, AccountDuplicate;

    Account accountLogin(String email, String Password) throws AccountNotFound, WrongPassword;

}
