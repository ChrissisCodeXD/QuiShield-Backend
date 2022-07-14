package de.quichris.quishield.controller;


import de.quichris.quishield.entity.Account;
import de.quichris.quishield.exceptions.AccountNotFound;
import de.quichris.quishield.exceptions.WrongPassword;
import de.quichris.quishield.service.AccountService;
import de.quichris.quishield.util.loginRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;


    @GetMapping("")
    public ResponseEntity<String> getGuilds() {
        return ResponseEntity.ok().body("oki doki");
    }


    @GetMapping("/login")
    public ResponseEntity<Account> login(@RequestBody loginRequestBody lrb) throws AccountNotFound, WrongPassword {
        String email = lrb.getEmail();
        String password = lrb.getPassword();
        
        Account acc = accountService.accountLogin(email, password);
        return ResponseEntity.ok(acc);
    }


}
