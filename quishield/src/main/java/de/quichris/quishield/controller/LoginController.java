package de.quichris.quishield.controller;


import de.quichris.quishield.body.*;
import de.quichris.quishield.entity.Account;
import de.quichris.quishield.exceptions.AccountDuplicate;
import de.quichris.quishield.exceptions.AccountNotFound;
import de.quichris.quishield.exceptions.WrongPassword;
import de.quichris.quishield.exceptions.WrongUsername;
import de.quichris.quishield.service.AccountService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AccountService accountService;


    @GetMapping("")
    public ResponseEntity<String> getGuilds() {
        return ResponseEntity.ok().body("oki doki");
    }


    @GetMapping("/login")
    public ResponseEntity<LoginResponseBody> login(@RequestBody LoginRequestBody lrb) throws AccountNotFound, WrongPassword {
        Account acc = accountService.accountLogin(lrb);
        Date expireDate = new Date(System.currentTimeMillis() + 30 * 60000);
        String token = Jwts.builder().setSubject(acc.getToken()).setExpiration(expireDate).signWith(accountService.getKey()).compact();
        LoginResponseBody responseBody = new LoginResponseBody(token, acc.getUsername());
        return ResponseEntity.ok(responseBody);
    }


    @PutMapping("/accounts/password")
    public ResponseEntity<Account> changePassword(@RequestBody ChangeAccountPasswordRequestBody body) throws AccountNotFound, WrongPassword {
        String tok = Jwts.parserBuilder().setSigningKey(accountService.getKey()).build().parseClaimsJws(body.getToken()).getBody().getSubject();
        Account tmp = accountService.updateAccountPassword(body, tok);
        return ResponseEntity.ok(tmp);
    }

    @PutMapping("/accounts/username")
    public ResponseEntity<Account> changeUsername(@RequestBody ChangeAccountUsernameRequestBody body) throws AccountNotFound, WrongUsername {
        String tok = Jwts.parserBuilder().setSigningKey(accountService.getKey()).build().parseClaimsJws(body.getToken()).getBody().getSubject();
        Account tmp = accountService.updateAccountUsername(body, tok);
        return ResponseEntity.ok(tmp);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> create_account(@RequestBody CreateAccountRequestBody carb) throws AccountDuplicate {
        Account acc = new Account();
        acc.setEmail(carb.getEmail());
        acc.setPassword(carb.getPassword());
        acc.setUsername(carb.getUsername());
        if (carb.getProfilepicture() != null) {
            acc.setProfilpicture(carb.getProfilepicture());
        }
        Account acc2 = accountService.saveAccount(acc);
        return ResponseEntity.ok(acc2);
    }

}
