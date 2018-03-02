package com.shark.example.entity;

public class LoginEntity {

    private String id;

    private String account;

    private String password;

    private int role1;

    private int role2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole1() {
        return role1;
    }

    public void setRole1(int role1) {
        this.role1 = role1;
    }

    public int getRole2() {
        return role2;
    }

    public void setRole2(int role2) {
        this.role2 = role2;
    }
}
