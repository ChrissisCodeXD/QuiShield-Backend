package de.quichris.quishield.body;


import de.quichris.quishield.entity.Password;
import lombok.Data;

import java.util.List;

@Data
public class PasswordsResponseBody {

    private List<Password> passwords;


}
