package Windows;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JFormattedTextField password_text;
    JButton login, registration;

    Client connection;
    ChatWindow chat;
    RegistrationWindow registrationWindow;

    MainWindow() {
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
        setSize(450, 210);
        setLocationRelativeTo(null);
        setVisible(true);

        connection = new Client();

//            chatWindow = new ChatWindow(connection);
//            registrationWindow = new RegistrationWindow();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();
        switch (cmd) {
            case "login":
                try {
                    if (connection.login(userName_text.getText(), password_text.getText())) {
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
                dispose();
                new RegistrationWindow(connection);
                break;
        }
    }

    public void getMessageFromClient(String msg) {
//    public void getMessageFromClient (ClientUser connection, String msg) {


    }


    public static void main(String[] args) {

        new MainWindow();

    }

}
