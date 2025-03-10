package com.baibei.urls.components;

import org.springframework.stereotype.Component;

@Component
public class Base62 {
    private final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int BASE = CHARACTERS.length();

    public String encode(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(CHARACTERS.charAt((int) (number % BASE)));
            number /= BASE;
        }
        return sb.reverse().toString();
    }
}
