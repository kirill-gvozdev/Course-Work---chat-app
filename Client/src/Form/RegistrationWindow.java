package Form;

import javax.swing.*;

public class RegistrationWindow extends JFrame{
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

