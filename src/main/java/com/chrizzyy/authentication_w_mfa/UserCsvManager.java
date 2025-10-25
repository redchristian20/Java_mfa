/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chrizzyy.authentication_w_mfa;

/**
 *
 * @author redch
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.mindrot.jbcrypt.BCrypt;

public class UserCsvManager {

    private static final String CSV_FILE_PATH = "users.csv";

    // Method to check if a username already exists
    public boolean isUsernameTaken(String username) {
        Set<String> existingUsernames = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    existingUsernames.add(parts[0]);
                }
            }
        } catch (IOException e) {
            // Handle file not found or other I/O errors gracefully
            return false; // If file doesn't exist, username is not taken
        }
        return existingUsernames.contains(username);
    }

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

