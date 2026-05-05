package com.kitchenhack.apikitchen.securities;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtTokenUtil {

    private static final String SECRET = "kitchenhack-kitchenhack-kitchenhack-kitchenhack-123456";
    private static final long EXPIRATION_MS = 24L * 60 * 60 * 1000;

    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        long expiry = now + EXPIRATION_MS;
        String payload = username + ":" + now + ":" + expiry;
        String signature = sign(payload);
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString((payload + ":" + signature).getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);
            byte[] raw = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(raw);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el token", e);
        }
    }
}


