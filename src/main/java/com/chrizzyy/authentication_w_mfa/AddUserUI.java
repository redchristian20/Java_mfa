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
import java.awt.image.BufferedImage;

public class AddUserUI {

    private final UserCsvManager userManager;
    private final QRCodeGenerator QRCodeGenerator;
    private final SecretKeyGenerator SecretKeyGenerator;

    public AddUserUI() {
        this.userManager = new UserCsvManager();
        this.QRCodeGenerator = new QRCodeGenerator();
        this.SecretKeyGenerator= new SecretKeyGenerator();
    }

    public void showAddUserDialog() {
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JPanel addUserPanel = new JPanel();
        addUserPanel.setLayout(new GridLayout(2, 2));
        addUserPanel.add(new JLabel("Username:"));
        addUserPanel.add(usernameField);
        addUserPanel.add(new JLabel("Password:"));
        addUserPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
            null,
            addUserPanel,
            "Add New User",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if username is already taken
            if (userManager.isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(null, "The username '" + username + "' is already taken.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate TOTP secret and save user
            String secretKey = SecretKeyGenerator.generateSecretKey();
            String issuer = "MyApp";
            String userQR = username+"-qrcode.png";
            userManager.addUser(username, password, secretKey);

            // Generate QR code and show it to the user
            String otpAuthUrl = String.format(
            "otpauth://totp/%s:%s?secret=%s&issuer=%s",
            issuer, username, secretKey, issuer
            );
            try {
                QRCodeGenerator.generateQRCode(otpAuthUrl,userQR, 200, 200);
                JLabel qrCodeLabel = new JLabel(new ImageIcon(userQR));
                JOptionPane.showMessageDialog(null, qrCodeLabel, "Scan QR for 2FA Setup", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error generating QR code: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddUserUI().showAddUserDialog());
    }
}

