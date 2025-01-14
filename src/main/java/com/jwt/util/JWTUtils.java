
package com.jwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jwt.constants.JWTConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtils {

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
				.signWith(SignatureAlgorithm.HS256, JWTConstants.SECRET_KEY).compact();

	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(JWTConstants.SECRET_KEY).parseClaimsJws(token).getBody();

	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		String username = extractUserName(token);

		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String extractUserName(String token) {

		return extractClaim(token, Claims::getSubject);

	}

	public Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);

	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());

	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);

	}

}
