package apex.ingagers.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.ExpiredJwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import apex.ingagers.ecommerce.model.Users;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    private final Logger logger = LoggerFactory
            .getLogger(JWTUtil.class);

    /**
     * Create a new token.
     */
    public String create(Users user) {

        // The JWT signature algorithm used to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //  sign JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //  set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                                 .setId(String.valueOf(user.getId()))
                                 .setIssuedAt(now)
                                 .setSubject(user.getEmail())
                                 .claim("name", user.getName())
                                 .claim("rol", user.getRoleName())
                                 .claim("photoUrl", user.getphotoUrl())
                                 .setIssuer(issuer)
                                 .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public String getUserId(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }

    public String getKey(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getId();
    }

    public Date getExpirationDate(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}