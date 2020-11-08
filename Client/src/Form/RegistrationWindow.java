package Form;

import javax.swing.*;

public class RegistrationWindow extends JFrame{
    private JButton registrationButton;
    private JLabel labelName;
    private JLabel labelPassword;
    private JTextField loginTextField;
    private JFormattedTextField passwordTextField;

    public RegistrationWindow() {
            super("Registration");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            add(new JLabel("Registration"));
            add(new JTextField());
            add(new JTextField());
            pack();
            setVisible(true);
    }
}

