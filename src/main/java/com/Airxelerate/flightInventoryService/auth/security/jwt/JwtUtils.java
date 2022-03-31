package com.Airxelerate.flightInventoryService.auth.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Airxelerate.flightInventoryService.auth.model.RefreshToken;
import com.Airxelerate.flightInventoryService.auth.model.Role;
import com.Airxelerate.flightInventoryService.auth.model.User;
import com.Airxelerate.flightInventoryService.auth.repository.RefreshTokenRepository;
import com.Airxelerate.flightInventoryService.auth.repository.UserRepository;
import com.Airxelerate.flightInventoryService.auth.service.UserDetailsImpl;
import com.Airxelerate.flightInventoryService.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Value("${jwt.jwtRefreshExpirationMs}")
	private int refreshTokenDurationMs;
	
	
	private final UserRepository userRepository;	
	private final RefreshTokenRepository refreshTokenRepository;

	public String generateJwtToken(UserDetailsImpl userPrincipal) {

		User userConnected = userRepository.findByUsername(userPrincipal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userPrincipal.getUsername()));
				
		Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
		List<String> roles = new ArrayList<String>();
		userConnected.getRoles().forEach(r -> roles.add(r.getName().name()));
	    claims.put("roles", roles);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String generateTokenFromUsernameAndRoles(String username, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("Scope", roles);
	    return Jwts.builder().setSubject(username).setIssuedAt(new Date())
	        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
	        .compact();
	  }

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			JwtUtils.logger.error("Expired or invalid JWT token : {}", e.getMessage());
			throw new CustomException("Expired or invalid JWT token");
		}

	}
	
	public Optional<RefreshToken> findByToken(String token) {
	    return refreshTokenRepository.findByToken(token);
	  }
	
	public RefreshToken createRefreshToken(UserDetailsImpl userPrincipal) {
		User userConnected = userRepository.findByUsername(userPrincipal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userPrincipal.getUsername()));
	    RefreshToken refreshToken = new RefreshToken();
	    refreshToken.setUser(userConnected);
	    refreshToken.setExpiryDate(new Date((new Date()).getTime() + this.jwtExpirationMs));
	    Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
	    String token =Jwts.builder()
							.setClaims(claims)
							.setIssuedAt(new Date())
							.setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
							.signWith(SignatureAlgorithm.HS512,jwtSecret)
							.compact();
	    refreshToken.setToken(token);
	    refreshToken = refreshTokenRepository.save(refreshToken);
	    return refreshToken;
	  }
	
	
	public RefreshToken verifyExpiration(RefreshToken token) {
	    if (token.getExpiryDate().compareTo(new Date()) < 0) {
	      refreshTokenRepository.delete(token);
	      throw new CustomException("Refresh token was expired. Please make a new signin request");
	    }
	    return token;
	  }
}
