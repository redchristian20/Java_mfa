/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chrizzyy.authentication_w_mfa;

/**
 *
 * @author redch
 */
public class UserInfo {
    private String hashedPassword;
    private String totpSecret;

    public UserInfo(String hashedPassword, String totpSecret) {
        this.hashedPassword = hashedPassword;
        this.totpSecret = totpSecret;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getTotpSecret() {
        return totpSecret;
    }
}
