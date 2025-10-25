package com.chrizzyy.authentication_w_mfa;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author redch
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import org.mindrot.jbcrypt.BCrypt;

public class UserCsvManagerNoUsernameCheck {

    private static final String CSV_FILE_PATH = "users.csv";

    public void addUser(String username, String password, String totpSecret) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH, true))) {
            File file = new File(CSV_FILE_PATH);
            if (file.length() == 0) {
                writer.println("username,hashedPassword,totpSecret");
            }
            
            writer.println(username + "," + hashedPassword + "," + totpSecret);
            System.out.println("User " + username + " added successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
