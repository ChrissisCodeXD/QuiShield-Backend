package de.quichris.quishield.body;


import lombok.Data;

@Data
public class LoginResponseBody {

    private String token;
    private String username;
    private String profilepicture;

    public LoginResponseBody(String token, String username, String profilepicture) {
        this.token = token;
        this.username = username;
        this.profilepicture = profilepicture;
    }

    public LoginResponseBody(String token, String username) {
        this.token = token;
        this.username = username;
    }

}
