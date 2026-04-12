package net.example.tinyurl.util;

public class Base62Encoder {

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(Long id) {
        StringBuilder sb = new StringBuilder();

        while (id > 0) {
            int remainder = (int) (id % 62);
            sb.append(CHARSET.charAt(remainder));
            id /= 62;
        }

        return sb.reverse().toString();
    }
}