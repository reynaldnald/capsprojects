/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.capsprojects;

/**
 *
 * @author TUF GAMING
 */
public class PendingAdmin {

    private String name;
    private String idNumber;
    private String email;
    private String password;
    private String otp;
    private String apiUrl;

    public PendingAdmin(String name, String idNumber, String email, String password, String apiUrl) {
        this(name, idNumber, email, password, null, apiUrl);
    }

    public PendingAdmin(String name, String idNumber, String email, String password, String otp, String apiUrl) {
        this.name = name;
        this.idNumber = idNumber;
        this.email = email;
        this.password = password;
        this.otp = otp;
        this.apiUrl = apiUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getOtp() {
        return otp;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
