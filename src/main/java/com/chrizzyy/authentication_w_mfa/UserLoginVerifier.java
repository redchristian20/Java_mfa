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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

public class UserLoginVerifier {

    private static final String CSV_FILE_PATH = "users.csv";
    private Map<String, UserInfo> userSecrets;

    public UserLoginVerifier() {
        this.userSecrets = new HashMap<>();
        loadUserSecrets();
    }

    private void loadUserSecrets() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    userSecrets.put(parts[0], new UserInfo(parts[1], parts[2]));
                }
            }
            System.out.println("User data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public UserInfo getUserInfo(String username) {
        return userSecrets.get(username);
    }

    public boolean verifyPassword(String username, String plainTextPassword) {
        UserInfo userInfo = getUserInfo(username);
        if (userInfo == null) {
            return false;
        }
        return BCrypt.checkpw(plainTextPassword, userInfo.getHashedPassword());
    }
}