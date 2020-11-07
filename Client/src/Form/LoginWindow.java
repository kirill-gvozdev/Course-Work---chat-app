package Form;

import javax.swing.*;

public class LoginWindow extends JFrame{

    private JTextField textField1;
    private JPanel panel1;

    public LoginWindow()
    {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Empty JFrame"));
        add(new JTextField());
        add(new JTextField());
        pack();
        setVisible(true);
    }
}



