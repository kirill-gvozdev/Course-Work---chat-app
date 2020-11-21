package Windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener {
    static Client connection = new Client();
    static JPasswordField passwordText;
    static JTextField userText;
    static JFrame frame;
    static JLabel imgLabel;

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        ImageIcon img = new ImageIcon("C:/Users/tipka/Desktop/IMG_small.png");
        frame = new JFrame("Login");
        frame.setIconImage(img.getImage());
        frame.setSize(450, 200);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // При закрытии окна отправляет управляющее слово quit в Client
                connection.finish();
            }
        });

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
        loginButton.setBounds(165, 85, 100, 25);
        loginButton.addActionListener(this);
        loginButton.setActionCommand("login");
        panel.add(loginButton);

        //Registration button
        JButton registrationButton = new JButton("registration");
        registrationButton.setBounds(165, 120, 100, 25);
        registrationButton.addActionListener(this);
        registrationButton.setActionCommand("registration");
        panel.add(registrationButton);

        ImageIcon mainLogo = new ImageIcon("C:/Users/tipka/Desktop/IMG_main.png");
        imgLabel = new JLabel(mainLogo);
        imgLabel.setBounds(255,5,200,150);
        panel.add(imgLabel);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "login":
                try {
                    if (connection.login(userText.getText(), passwordText.getText())) {
                        frame.dispose();
//                        dispose();
                        new ChatWindow(connection);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect login or password.");
                        userText.setText(null);
                        passwordText.setText(null);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case "registration":
//                dispose();
                frame.dispose();
                new RegistrationWindow(connection);
                break;
        }
    }
}
