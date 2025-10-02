package org.urlshortener.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class SimpleHasher {
    private SimpleHasher() {}

    /**
     * SHA-256 hex tronqué à maxLen. Ajoute un saltIndex pour dériver un autre code en cas de collision.
     */
    public static String hashHex(String input, int saltIndex, int maxLen) {
        byte[] digest = sha256(input + "#" + saltIndex);
        String hex = toHex(digest);
        return hex.length() > maxLen ? hex.substring(0, maxLen) : hex;
    }

    private static byte[] sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }
}