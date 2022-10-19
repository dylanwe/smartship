package com.smartship.backend.app.response;

import com.smartship.backend.app.models.User;

public record LoginResponse(
        String jwtToken,
        String type,
        String refreshToken,
        User user
) {
}
