package com.lv.finance.infra.security.services.impl;

import com.lv.finance.infra.security.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final String SECRET_KEY;

    private final Long EXPIRATION_TIME;

    public JwtServiceImpl(@Value("${api.security.token.secret}") String secretKey,
                          @Value("${api.security.token.expiration.time}") Long expirationTime){
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME = expirationTime;
    }

    @Override
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaims(jwtToken));
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(getCurrentDate())
                .setExpiration(getExpirationDate())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(getCurrentDate());
    }

    private Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    private Date getExpirationDate(){
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * EXPIRATION_TIME);
    }


}
