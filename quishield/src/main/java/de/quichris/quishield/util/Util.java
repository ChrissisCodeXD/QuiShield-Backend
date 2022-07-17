package de.quichris.quishield.util;

import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class Util {
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final String secretString = "nt6b3PBFx5t5I6S5qbMa2a53L5IbbGmseC1vmH73mS736lluYg468F0xola8487mpy6TdRdoaES53q99ea99E8Ih53k5mU9XH9XkU1XmC2XsB23MMbM961TyY51eDu6g";

    public String genToken() {
        byte[] randomBytes = new byte[30];
        random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public String EncryptPassword(String password) {
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setSubject(password).signWith(key).compact();
    }


    public String DecryptPassword(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String sha256(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
