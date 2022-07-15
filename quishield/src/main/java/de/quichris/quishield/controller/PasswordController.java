package de.quichris.quishield.controller;


import de.quichris.quishield.body.*;
import de.quichris.quishield.entity.Account;
import de.quichris.quishield.entity.Password;
import de.quichris.quishield.exceptions.AccountNotFound;
import de.quichris.quishield.exceptions.NotAuthorized;
import de.quichris.quishield.exceptions.PasswordNotFound;
import de.quichris.quishield.service.AccountService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class PasswordController {
    private final AccountService accountService;


    @GetMapping("/passwords")
    public ResponseEntity<PasswordsResponseBody> getPasswords(@RequestBody PasswordsRequestBody prb) throws NotAuthorized, AccountNotFound {
        try {
            String token = Jwts.parserBuilder().setSigningKey(accountService.getKey()).build().parseClaimsJws(prb.getToken()).getBody().getSubject();
            List<Password> pswds = accountService.getPasswords(token);
            PasswordsResponseBody ret = new PasswordsResponseBody();
            ret.setPasswords(pswds);
            return ResponseEntity.ok(ret);
        } catch (JwtException e) {
            throw new NotAuthorized("Token is not Authorized");
        }
    }


    @PostMapping("/passwords")
    public ResponseEntity<Password> getPasswords(@RequestBody PasswordCreateRequestBody prb) throws NotAuthorized {
        try {
            String token = Jwts.parserBuilder().setSigningKey(accountService.getKey()).build().parseClaimsJws(prb.getToken()).getBody().getSubject();
            Password pswd = prb.getPassword();
            Account owner = accountService.getAccount(token);
            pswd.setOwner(token);
            accountService.addPassword(pswd);
            return ResponseEntity.ok(pswd);
        } catch (JwtException e) {
            e.printStackTrace();
            throw new NotAuthorized("Token is not Authorized");
        } catch (AccountNotFound e) {
            throw new NotAuthorized("Token is not Authorized/invalid");
        }
    }

    @GetMapping("/passwords/{password_id}")
    public ResponseEntity<Password> getDecryptedPassword(@RequestBody GetDecryptedPasswordRequestBody body, @PathVariable String password_id) throws PasswordNotFound, NotAuthorized {
        try {
            String token = Jwts.parserBuilder().setSigningKey(accountService.getKey()).build().parseClaimsJws(body.getToken()).getBody().getSubject();
            Password password = accountService.getPassword(token, password_id);
            return ResponseEntity.ok(password);
        } catch (JwtException e) {
            throw new NotAuthorized("Token is not Authorized");
        }
    }

    @PutMapping("/passwords/{password_id}")
    public ResponseEntity<Password> changePassword(@RequestBody ChangePasswordRequestBody body, @PathVariable String password_id) throws PasswordNotFound, NotAuthorized {
        try {
            String token = Jwts.parserBuilder().setSigningKey(accountService.getKey()).build().parseClaimsJws(body.getToken()).getBody().getSubject();
            Password tmp = body.getPassword();
            tmp.setId(password_id);
            Password password = accountService.updatePassword(tmp, token);
            return ResponseEntity.ok(password);
        } catch (JwtException e) {
            throw new NotAuthorized("Token is not Authorized");
        }
    }


}
