package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegistrationWindow extends JFrame  implements ActionListener{

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

    private Client connection;

    public RegistrationWindow(Client connection) {

        super("Messenger V.0.01");

        this.connection = connection;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        labelName = new JLabel();
        labelName.setText("User name: ");
        loginTextField = new JTextField();
        labelPassword = new JLabel();
        labelPassword.setText("Password: ");
        passwordTextField = new JFormattedTextField();
        registrationButton = new JButton("Submit");
        registrationButton.addActionListener(this);

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

    }

//        loginTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                userLogin = loginTextField.getText();
//            }
//        });
//        passwordTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                userPassword = passwordTextField.getText();
//            }
//        });
//        registrationButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("!!!!!!!!!!!!!!");
        e.getActionCommand();
        try {
            if (connection.registration(loginTextField.getText(), passwordTextField.getText())) {
                JOptionPane.showMessageDialog(null, connection.getMessage());
                dispose();
                ChatWindow chat = new ChatWindow(connection);
//                connection.setChat();
//                connection.getChat(chat);
            } else {
                JOptionPane.showMessageDialog(null, connection.getMessage());
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
//        });


//    }

}

