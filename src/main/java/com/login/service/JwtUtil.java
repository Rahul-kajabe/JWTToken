package com.login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	private String SCRET_KEY = "screte";

	public String extractUserName(String Token) {
		return extractCliam(Token, Claims::getSubject);

	}

	public Date extractExpiration(String Token) {
		return extractCliam(Token, Claims::getExpiration);

	}

	public <T> T extractCliam(String token, Function<Claims, T> claimResolver) {
		final Claims claim = extractAllClaims(token);
		return claimResolver.apply(claim);
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(SCRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());

	}

	private String createToken(Map<String, Object> claims, String subject) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SCRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
