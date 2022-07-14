package de.quichris.quishield.controller;


import de.quichris.quishield.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;


    @GetMapping("")
    public ResponseEntity<String> getGuilds() {
        return ResponseEntity.ok().body("oki doki");
    }





}
