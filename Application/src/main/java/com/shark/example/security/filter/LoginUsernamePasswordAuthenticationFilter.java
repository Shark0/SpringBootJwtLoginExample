package com.shark.example.security.filter;

import com.shark.example.security.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class LoginUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public LoginUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        System.out.println("LoginUsernamePasswordAuthenticationFilter account: " + account + ", password: " + password);

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account, password, new ArrayList<>()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Config.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Config.SECRET.getBytes())
                .compact();
        response.addHeader(Config.HEADER, Config.TOKEN_PREFIX + token);
        chain.doFilter(request, response);
    }
}
