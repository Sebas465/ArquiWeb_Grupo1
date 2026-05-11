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

    // Extrae el username del token decodificando Base64 y tomando la primera parte
    public String extractUsername(String token) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            // Formato: username:timestamp:expiry:signature
            return decoded.split(":")[0];
        } catch (Exception e) {
            return null;
        }
    }

    // Valida que el token no haya expirado y que la firma sea correcta
    public boolean validateToken(String token) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            // parts[0]=username, parts[1]=issuedAt, parts[2]=expiry, parts[3]=signature
            if (parts.length < 4) return false;

            String username = parts[0];
            long expiry = Long.parseLong(parts[2]);
            String receivedSig = parts[3];

            // Verificar que el token no haya expirado
            if (System.currentTimeMillis() > expiry) return false;

            // Verificar que la firma coincida (evita tokens manipulados)
            String expectedSig = sign(username + ":" + parts[1] + ":" + expiry);
            return expectedSig.equals(receivedSig);
        } catch (Exception e) {
            return false;
        }
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


