package de.quichris.quishield.body;

import lombok.Data;

@Data
public class ChangeAccountPasswordRequestBody {
    private String new_password;
    private String old_password;
    private String token;
}
