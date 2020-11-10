package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindowTwo extends JFrame implements ActionListener {

        JPanel panel;
        JLabel user_label, password_label, message;
        JTextField userName_text;
        JFormattedTextField password_text;
        JButton login, registration;
        static final int PORT = 8818;
        static final String HOST = "localhost";

        ClientUser connection;

    MainWindowTwo() {
            // Username Label
            user_label = new JLabel();
            user_label.setText("User Name :");
            userName_text = new JTextField();
            // Password Label
            password_label = new JLabel();
            password_label.setText("Password :");
            password_text = new JFormattedTextField();
            // Submit
            login = new JButton("Login");
            registration = new JButton("Registration");
            panel = new JPanel(new GridLayout(3, 1));
            panel.add(user_label);
            panel.add(userName_text);
            panel.add(password_label);
            panel.add(password_text);
            panel.add(login);
            panel.add(registration);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Adding the listeners to components..
            login.addActionListener(this);
            login.setActionCommand("login");
            registration.addActionListener(this);
            registration.setActionCommand("registration");
            add(panel, BorderLayout.CENTER);

            setTitle("Please Login Here !");
            setSize(450,210);
            setLocationRelativeTo(null);
            setVisible(true);

        try {
            connection = new ClientUser(HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void main(String[] args) {
            new MainWindowTwo();
        }
        @Override
        public void actionPerformed(ActionEvent e) {

        String userName = userName_text.getText();
        String password = password_text.getText();

        String cmd = e.getActionCommand();
        switch (cmd) {
            case "login":
                try {
                    if (connection.authorization(userName_text.getText() + " " + password_text.getText())) {
                        dispose();
                        new ChatWindow(connection);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect login or password.");
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case "registration" :
                dispose();
                new RegistrationWindow();
                break;
        }

    }
}

