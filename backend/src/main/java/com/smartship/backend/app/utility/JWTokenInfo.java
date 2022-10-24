package com.smartship.backend.app.utility;

import com.smartship.backend.app.models.User;

import java.util.Date;

public record JWTokenInfo(
        String email,
        Long userId,
        User.ROLE role,
        Date issuedAt,
        Date expiration
) {
    public static final String KEY = "tokenInfo";
}
