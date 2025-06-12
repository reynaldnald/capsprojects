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
    
    public PendingAdmin(String name, String idNumber, String email, String password) {
        this.name = name;
        this.idNumber = idNumber;
        this.email = email;
        this.password = password;
    }
    
    public String getName() {return name;}
    public String getId() {return idNumber;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
}
