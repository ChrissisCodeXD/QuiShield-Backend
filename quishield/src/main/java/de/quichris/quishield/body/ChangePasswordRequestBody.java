package de.quichris.quishield.body;

import de.quichris.quishield.entity.Password;
import lombok.Data;

@Data
public class ChangePasswordRequestBody {
    private String token;
    private Password password;

}
