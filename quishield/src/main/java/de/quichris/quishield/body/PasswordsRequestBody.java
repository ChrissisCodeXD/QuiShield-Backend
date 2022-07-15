package de.quichris.quishield.body;


import lombok.Data;

@Data
public class PasswordsRequestBody {

    private String token;

    private int count = 0;

}
