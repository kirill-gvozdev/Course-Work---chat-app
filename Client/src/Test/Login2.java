package Test;

import Windows.ChatWindow;
import Windows.Client;
import Windows.MainWindow;
import Windows.RegistrationWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login2 extends JFrame implements ActionListener {
    static Client connection = new Client();
    static JPasswordField passwordText;
    static JTextField userText;

    ChatWindow chat;

    public static void main(String[] args) {
        new Login2();
    }

    public Login2() {
        JFrame frame = new JFrame("Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        // User label
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(20,20,80,25);
        panel.add(userLabel);

        // User text field
        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20,50,80,25);
        panel.add(passwordLabel);

        // Password text
        passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        //Login button
        JButton loginButton = new JButton("login");
        loginButton.setBounds(100, 90, 100, 25);
        loginButton.addActionListener(this);
        loginButton.setActionCommand("login");
        panel.add(loginButton);

        //Registration button
        JButton registrationButton = new JButton("registration");
        registrationButton.setBounds(100, 120, 100, 25);
        panel.add(registrationButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "login":
                try {
                    if (connection.login(userText.getText(), passwordText.getText())) {
                        dispose();

                        chat = new ChatWindow(connection);
                        //connection.setChat();
//                        connection.getChat(chat);


                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect login or password.");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case "registration":
                //dispose();
                new RegistrationWindow(connection);
                break;
        }
    }
}
