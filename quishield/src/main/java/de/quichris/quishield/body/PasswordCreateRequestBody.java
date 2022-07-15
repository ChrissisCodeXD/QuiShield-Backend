package de.quichris.quishield.body;

import de.quichris.quishield.entity.Password;
import lombok.Data;

@Data
public class PasswordCreateRequestBody {

    private String token;

    private Password password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
