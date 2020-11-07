package Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage extends JFrame implements ActionListener{
    private JButton loginButton;
    private JButton registrationButton;
    private JPanel panel1;

    public LandingPage() {
        super("Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setActionCommand("OpenLoginWindow");
        add(loginButton);
        pack();

        registrationButton = new JButton("Registration");
        registrationButton.addActionListener(this);
        registrationButton.setActionCommand("OpenRegistrationWindow");
        add(registrationButton);
        pack();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd.equals("OpenLoginWindow")) {
            dispose();
            new LoginWindow();
        } else if (cmd.equals("OpenRegistrationWindow")) {
            dispose();
            new RegistrationWindow();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                new LandingPage().setVisible(true);
            }
        });
    }
}




   /* public LandingPage(String title) {
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new LoginWindow();
                LandingPage.this.setVisible(false);
            }

        });
        setTitle("Form 1");
        getContentPane().add(loginButton, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        public static void main(String[] args) {
        JFrame frame = new LandingPage("Chat");
        frame.setVisible(true);
    }
    }
*/