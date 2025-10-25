/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chrizzyy.authentication_w_mfa;

/**
 *
 * @author redch
 */
import javax.swing.*;
import java.awt.*;
import java.security.GeneralSecurityException;

public class LoginUI {

    private final UserLoginVerifier verifier;

    public LoginUI() {
        this.verifier = new UserLoginVerifier();
    }

    public void showLoginDialog() throws GeneralSecurityException {
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2, 2));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, loginPanel, "User Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String passwordString = new String(passwordField.getPassword());

            if (verifier.verifyPassword(username, passwordString)) {
                // Password is correct, now prompt for OTP
                UserInfo userInfo = verifier.getUserInfo(username);
                String otpCode = JOptionPane.showInputDialog(null, "Enter OTP from your authenticator app:", "OTP Verification", JOptionPane.PLAIN_MESSAGE);
                if (otpCode != null && TOTPValidator.validateOTP(userInfo.getTotpSecret(), otpCode)) {
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid OTP. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginUI().showLoginDialog();
            } catch (GeneralSecurityException ex) {
                System.getLogger(LoginUI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }
}

