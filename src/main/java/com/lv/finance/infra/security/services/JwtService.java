package com.lv.finance.infra.security.services;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
    String extractUsername(String jwtToken);

    String generateToken(UserDetails userDetails);

    Boolean isTokenValid(String token, UserDetails userDetails);

}
