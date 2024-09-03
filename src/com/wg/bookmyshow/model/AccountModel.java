package com.wg.bookmyshow.model;

import java.util.UUID;

public class AccountModel {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private int age;
    private boolean blocked;
    private static String accountId; // No need for static here
    private String organizerId;

    
    public enum Role {
        ADMIN, USER, ORGANIZER;
    }
    public AccountModel() {
        this.username = "";
        this.password = "";
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.role = "";
        this.age = 0;
        this.blocked = false; // Default value for blocked
        this.accountId = UUID.randomUUID().toString(); // Generate UUID
    }

    public String toString() {
        return "AccountModel{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", blocked=" + blocked +
                '}';
    }
    
    // Getters and setters for each field

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public static String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

	
}

