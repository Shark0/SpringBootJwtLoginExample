package com.shark.example.security.filter;

import com.shark.example.entity.LoginEntity;
import com.shark.example.mapper.LoginMapper;
import com.shark.example.security.Config;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginAuthorizationFilter extends BasicAuthenticationFilter {

    private LoginMapper loginMapper;

    public LoginAuthorizationFilter(AuthenticationManager authenticationManager, LoginMapper loginMapper) {
        super(authenticationManager);
        this.loginMapper = loginMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(Config.HEADER);
        System.out.println("LoginAuthorizationFilter doFilterInternal header: " + header);
        if(header == null || !header.startsWith(Config.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Config.HEADER);
        System.out.println("LoginAuthorizationFilter getAuthentication token: " + token);
        if (token != null) {
            //parser token
            String account = Jwts.parser()
                    .setSigningKey(Config.SECRET.getBytes())
                    .parseClaimsJws(token.replace(Config.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (account != null) {
                System.out.println("LoginAuthorizationFilter getAuthentication account: " + account);
                LoginEntity loginEntity = loginMapper.selectMember(account);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if(loginEntity.getRole1() == 1) {
                    authorities.add(new SimpleGrantedAuthority("role1"));
                }
                if(loginEntity.getRole2() == 1) {
                    authorities.add(new SimpleGrantedAuthority("role2"));
                }
                String id = loginEntity.getId();
                System.out.println("LoginAuthorizationFilter getAuthentication id: " + id);
                return new UsernamePasswordAuthenticationToken(id, null, authorities);
            }
            return null;
        }
        return null;
    }
}
