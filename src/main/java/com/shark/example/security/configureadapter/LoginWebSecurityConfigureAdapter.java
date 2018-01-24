package com.shark.example.security.configureadapter;

import com.shark.example.entity.LoginEntity;
import com.shark.example.mapper.LoginMapper;
import com.shark.example.security.Config;
import com.shark.example.security.filter.LoginAuthorizationFilter;
import com.shark.example.security.filter.LoginUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;


@EnableWebSecurity
@Configuration
public class LoginWebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private LoginMapper loginMapper;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("LoginWebSecurityConfigureAdapter configure http security");
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, Config.REGISTER_URL).permitAll()
                .antMatchers("/role1").hasAuthority("role1")
                .antMatchers("/role2").hasAuthority("role2")
                .anyRequest().authenticated()
                .and()
                .addFilter(new LoginUsernamePasswordAuthenticationFilter(authenticationManager()))
                .addFilter(new LoginAuthorizationFilter(authenticationManager(), loginMapper))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        System.out.println("LoginWebSecurityConfigureAdapter configure auth");
        authenticationManagerBuilder
                .userDetailsService(new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
                        System.out.println("LoginWebSecurityConfigureAdapter configure auth account: " + account);
                        LoginEntity loginEntity = loginMapper.selectMember(account);
                        if(loginEntity == null) {
                            throw new UsernameNotFoundException("could not find the account: " + account);
                        }

                        System.out.println("LoginWebSecurityConfigureAdapter login entity account: " + loginEntity.getAccount());
                        System.out.println("LoginWebSecurityConfigureAdapter login entity password: " + loginEntity.getPassword());
                        System.out.println("LoginWebSecurityConfigureAdapter login entity role1: " + loginEntity.getRole1());
                        System.out.println("LoginWebSecurityConfigureAdapter login entity role2: " + loginEntity.getRole2());
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        if(loginEntity.getRole1() == 1) {
                            authorities.add(new SimpleGrantedAuthority("role1"));
                        }
                        if(loginEntity.getRole2() == 1) {
                            authorities.add(new SimpleGrantedAuthority("role2"));
                        }
                        User user = new User(loginEntity.getAccount(), bCryptPasswordEncoder.encode(loginEntity.getPassword()),
                                true, true, true, true,
                                authorities);
                        return user;
                    }
                })
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
