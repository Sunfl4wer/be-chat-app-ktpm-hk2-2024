package hcmut.group2.project.chatapp.usermanager.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.repositories.RefreshTokenRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Getter
    @Value("${JWT_SECRET_KEY:0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk}")
    private String secretKey;

    @Autowired
    private RefreshTokenRepository tokenRepository;

    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, ChatUser user) {
        String phoneNumber = extractPhoneNumber(token);

        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (phoneNumber.equals(user.getPhoneNumber())) && !isTokenExpired(token) && validToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String generateToken(ChatUser user) {
        String token = Jwts
                .builder()
                .subject(user.getPhoneNumber())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30*60*1000 ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
