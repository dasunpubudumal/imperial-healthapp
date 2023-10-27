package org.health.imperialhealthapp.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@Getter
@Setter
public class KeyProperties {

    private final RSAPublicKey pub;
    private final RSAPrivateKey pri;

    public KeyProperties() {
        KeyPair keyPair = KeyGen.generateKeyPair();
        this.pub = (RSAPublicKey) keyPair.getPublic();
        this.pri = (RSAPrivateKey) keyPair.getPrivate();
    }

}
