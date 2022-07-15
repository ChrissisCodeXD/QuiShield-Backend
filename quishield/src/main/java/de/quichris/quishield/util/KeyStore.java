package de.quichris.quishield.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyStore {

    private Key k;

    public KeyStore() {
        this.k = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public Key getKey() {
        return k;
    }
}
