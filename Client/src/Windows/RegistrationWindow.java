package Windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class RegistrationWindow extends JFrame  implements ActionListener{

    private static final int WIDTH = 450;
    private static final int HEIGHT = 200;

    private JPanel panel;
    private JLabel labelLogin, labelPassword, labelConfirmation;
    private JTextField loginField;
    private JFormattedTextField passwordField, passwordConfirmation;
    private JButton registrationButton;
    private Client connection;
    static JLabel imgLabel;

    public RegistrationWindow(Client connection) {
        super("Registration");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connection.finish();
            }
        });

        ImageIcon img = new ImageIcon("C:/Users/tipka/Desktop/IMG_small.png");
        setIconImage(img.getImage());

        panel = new JPanel(); //
        add(panel);
        panel.setLayout(null);

        // User label
        labelLogin = new JLabel("New login: ");
        labelLogin.setBounds(20,20,80,25);
        panel.add(labelLogin);

        // User text field
        loginField = new JTextField(20);
        loginField.setBounds(100,20,165,25);
        panel.add(loginField);

        // Password label
        labelPassword = new JLabel("Password: ");
        labelPassword.setBounds(20,50,80,25);
        panel.add(labelPassword);

        // Password text
        passwordField = new JFormattedTextField();
        passwordField.setBounds(100,50,165,25);
        panel.add(passwordField);

        // Confirmation label
        labelConfirmation = new JLabel("Confirm: ");
        labelConfirmation.setBounds(20,80,100,25);
        panel.add(labelConfirmation);

        // Password confirmation
        passwordConfirmation = new JFormattedTextField();
        passwordConfirmation.setBounds(100,80,165,25);
        panel.add(passwordConfirmation);

        registrationButton = new JButton("Submit");
        registrationButton.setBounds(165,120,100,25);

        registrationButton.addActionListener(this);
        panel.add(registrationButton);



        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);    // always in the center
        setAlwaysOnTop(true);   // on top of other windows

        ImageIcon mainLogo = new ImageIcon("C:/Users/tipka/Desktop/IMG_main.png");
        imgLabel = new JLabel(mainLogo);
        imgLabel.setBounds(255,5,200,150);
        panel.add(imgLabel);

        setVisible(true);

        this.connection = connection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (passwordField.getText().compareTo(passwordConfirmation.getText()) == 0) {
            try {
                if (connection.registration(loginField.getText(), passwordField.getText())) {
                    JOptionPane.showMessageDialog(panel, "Registration is completed.");
                    dispose();
                    new Chat (connection);
                } else {
                    JOptionPane.showMessageDialog(this, connection.getMessage());
                }
            } catch (IOException ioException) {
            ioException.printStackTrace();
            }
        } else {
            passwordField.setText("");
            passwordConfirmation.setText("");
            JOptionPane.showMessageDialog(this, "Passwords do not match.");

        }
    }
}

