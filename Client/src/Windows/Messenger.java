package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Messenger extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JTextField loginTextField = new JTextField();
    private String userLogin;

    private JFormattedTextField passwordTextField = new JFormattedTextField();
    private String userPassword;

    private JButton buttonLogin = new JButton();
    private JButton buttonRegistration = new JButton();

    private JPanel Messenger = new JPanel();

    private ClientUser connection;


    public Messenger() {
        super("Messenger V.0.01");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT); // установка размера окна
        setLocationRelativeTo(null);    // окно всегда в центре
        setAlwaysOnTop(true);   // окно поверх других окон

        //loginTextField.setSize(20,100);
        //loginTextField.setLocation(50,50);

        //loginTextField.setBounds(50,50,20, 100);

        add (loginTextField, FlowLayout.LEFT);

//        add (passwordTextField, BorderLayout.AFTER_LAST_LINE);
//        add (buttonLogin, BorderLayout.WEST);
//        add (buttonRegistration, BorderLayout.EAST);



        setVisible(true);


        // Слушатель событий от нажатия на Х
//        this.addWindowListener (new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                // При закрытии окна отправляет управляющее слово quit в Client
//                //connection.sendQuit("quit");
//            }
//        });

//        loginTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//
//                userLogin = loginTextField.getText();
//            }
//        });
//
//        passwordTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                userPassword = passwordTextField.getText();
//            }
//        });

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (connection.authorization(userLogin + " " + userPassword)) {
                dispose();
                new ChatWindow(connection);
//                    } else {
//                        JOptionPane.showMessageDialog(null,
//                                "Incorrect login or password.");
//                    }
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
            }
        });

        buttonRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistrationWindow ();
            }
        });



    }

    public static void main(String[] args) {
        // во всех графических интерфейсах есть ограничения по многпоточности: нельзя работать из разных потоков
        // некоторые интерфейсы требуют, чтобы можно было работать только из главного main потока
        // со swing можно работать только из потока EDT
        // конструкция ниже застявляет строку new ClientWindow(); выполниться в потоке EDT
        System.out.println();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Messenger();
            }
        });

    }
//
//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
}
