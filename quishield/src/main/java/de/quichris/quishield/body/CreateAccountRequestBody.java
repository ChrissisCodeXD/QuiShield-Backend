package de.quichris.quishield.body;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CreateAccountRequestBody {
    private String email;
    private String password;


    @Nullable
    private String profilepicture = null;
    private String username;
}
