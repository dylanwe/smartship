package com.smartship.backend.app.utility;

import com.smartship.backend.app.config.GlobalConfig;
import com.smartship.backend.app.models.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTokenUtil {
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";

    private final GlobalConfig globalConfig;

    @Autowired
    public JWTokenUtil(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public JWTokenInfo decode(String encodedToken, String passphrase) throws ExpiredJwtException, MalformedJwtException {
        // Validate the token
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(encodedToken);
        Claims claims = jws.getBody();

        // Destruct token to something readable
        return generateTokenInfo(claims);
    }

    /**
     * Encode the data to a secure JWT token
     *
     * @param callname Who it came from
     * @param userId   The id of th user
     * @param role     What role the user has
     * @return A secure encoded JWT token with this information withing
     */
    public String encode(String callname, Long userId, User.ROLE role) {
        Key key = getKey(globalConfig.getPassphrase());

        return Jwts.builder()
                .claim(JWT_CALLNAME_CLAIM, callname)
                .claim(JWT_USERID_CLAIM, userId.toString())
                .claim(JWT_ROLE_CLAIM, role.toString())
                .setIssuer(globalConfig.issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + globalConfig.tokenDurationOfValidity))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private JWTokenInfo generateTokenInfo(Claims claims) {
        return new JWTokenInfo(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                User.ROLE.valueOf(claims.get(JWT_ROLE_CLAIM).toString()),
                claims.getIssuedAt(),
                claims.getExpiration()
        );
    }
}
