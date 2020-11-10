package WindowsAnotherAttempt.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegistrationWindow extends JFrame {

    private static final int WIDTH = 450;
    private static final int HEIGHT = 210;

    JPanel panel;

    private JLabel labelName, message;
    private JTextField loginTextField;
    private String userLogin;

    private JLabel labelPassword;
    private JFormattedTextField passwordTextField;
    private String userPassword;

    private JButton registrationButton;

    private ClientUser connection;

    public RegistrationWindow() {

        super("Messenger V.0.01");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        labelName = new JLabel();
        labelName.setText("User name: ");
        loginTextField = new JTextField();
        labelPassword = new JLabel();
        labelPassword.setText("Password: ");
        passwordTextField = new JFormattedTextField();
        registrationButton = new JButton("Submit");

        setSize(WIDTH, HEIGHT); // установка размера окна
        setLocationRelativeTo(null);    // окно всегда в центре
        setAlwaysOnTop(true);   // окно поверх других окон

        panel = new JPanel(new GridLayout(3, 1));

        panel.add(labelName);
        panel.add(loginTextField);
        panel.add(labelPassword);
        panel.add(passwordTextField);
        message = new JLabel();
        panel.add(message);
        panel.add(registrationButton);
        add(panel, BorderLayout.CENTER);

        setVisible(true);

        loginTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userLogin = loginTextField.getText();
            }
        });
        passwordTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPassword = passwordTextField.getText();
            }
        });
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String serverAnswer = connection.registration(userLogin, userPassword);
                    if (!"Registration failed".equals(serverAnswer)) {
                        JOptionPane.showMessageDialog(null,
                                serverAnswer);
                        dispose();
                        new ChatWindow(connection);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                serverAnswer);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
//
//
//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
}

