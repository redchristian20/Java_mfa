/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chrizzyy.authentication_w_mfa;

/**
 *
 * @author redch
 */
import java.security.GeneralSecurityException;
import java.util.Scanner;

public class Login {
	
    public static void main(String[] args) throws GeneralSecurityException {
        String correctUsername = "test";
        String correctPassword = "test";
        String secretKey = "abcdeabcdeabcdeabcde"; // Replace with actual secret key

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter OTP: ");
        String otp = scanner.nextLine();
        
        if (username.equals(correctUsername) && password.equals(correctPassword)
        		&& TOTPValidator.validateOTP(secretKey, otp)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed!");
        }

        scanner.close();
    }
} 
