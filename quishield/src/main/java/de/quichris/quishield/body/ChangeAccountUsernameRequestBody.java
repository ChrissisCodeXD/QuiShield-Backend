package de.quichris.quishield.body;

import lombok.Data;

@Data
public class ChangeAccountUsernameRequestBody {
    private String old_username;
    private String username;
    private String token;
}
