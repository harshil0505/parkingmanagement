package com.Online.ParkigManagement.Security.JWT;

import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.Online.ParkigManagement.Security.ServiceSecurity.UserDetaileImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${spring.app.jwtCookieName}")
    private String jwtCookie;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExparionMs;
 
    public String getJwtFromCookies(HttpServletRequest Request){
        Cookie cookie =WebUtils.getCookie(Request, jwtCookie);
        if(cookie != null){
            return cookie.getValue();
        }else{
            return null;
        }
        }

    public ResponseCookie ganrateJwtCookie(UserDetaileImpl userPrincipal)  {
        String jwt=ganrateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie=ResponseCookie.from(jwtCookie, jwt)
        .path("/api")
        .maxAge(24*60*60)
        .httpOnly(false)
        .build();
        return cookie;
    }
    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie=ResponseCookie.from(jwtCookie)
        .path("/api")
        .build();
        return cookie;
    }   

    public String  ganrateTokenFromUsername(String username){
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime()+jwtExparionMs))
        .signWith(key())
        .compact();
    }

    public String  getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder()
        .setSigningKey(key())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
      
    }
    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    } 

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(authToken);
            return true;
         } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
       
    }


