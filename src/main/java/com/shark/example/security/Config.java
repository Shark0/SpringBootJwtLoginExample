package com.shark.example.security;

public class Config {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final String REGISTER_URL = "/register";
    public static final String LOGIN_URL = "/login";
}
