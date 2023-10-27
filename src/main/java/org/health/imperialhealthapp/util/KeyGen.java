package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import org.health.imperialhealthapp.exceptions.InternalServerException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@UtilityClass
public class KeyGen {

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                    "RSA"
            );
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new InternalServerException("Error in generating/validating key pair");
        }
    }

}
