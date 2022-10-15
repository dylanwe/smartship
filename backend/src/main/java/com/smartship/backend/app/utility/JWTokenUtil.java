package com.smartship.backend.app.utility;

import com.smartship.backend.app.models.User;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWTokenUtil {
    public static final String JWT_ATTRIBUTE_NAME = "attribute";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipAddress";

    private String callName;
    private Long userId;
    private User.ROLE role;
    private String ipAddress;

    public JWTokenUtil(String callname, Long userId, User.ROLE role) {
        this.callName = callname;
        this.userId = userId;
        this.role = role;
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public static JWTokenUtil decode(String token, String passphrase) throws ExpiredJwtException, MalformedJwtException {
        // Validate the token
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        Claims claims = jws.getBody();

        // Destruct token to something readable
        JWTokenUtil jwTokenUtil = new JWTokenUtil(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                User.ROLE.valueOf(claims.get(JWT_ROLE_CLAIM).toString())
        );
        jwTokenUtil.setIpAddress((String) claims.get(JWT_IPADDRESS_CLAIM));
        return jwTokenUtil;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Encode the data to a secure JWT token
     *
     * @param issuer Who it came from
     * @param passphrase The secret with which to encrypt this data with
     * @param expiration When the token becomes invalid again
     * @return A secure encoded JWT token
     */
    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);

        return Jwts.builder()
                .claim(JWT_CALLNAME_CLAIM, this.callName)
                .claim(JWT_USERID_CLAIM, this.userId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
