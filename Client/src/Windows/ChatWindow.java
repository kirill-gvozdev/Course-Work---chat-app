package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatWindow extends JFrame implements ActionListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private JTextField sendMessageField;
    private JTextArea logMessageArea;
    private JScrollPane logMessageAreaScroll;
    Client connection;
    Timer timer;

    public ChatWindow (Client connect) throws IOException {

        super("Messenger V.0.01");

        this.connection = connect;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // При закрытии окна отправляет управляющее слово quit в Client
                connection.finish();
            }
        });


        timer = new Timer(100, this);
        timer.setInitialDelay(190);

        sendMessageField = new JTextField();
        logMessageArea = new JTextArea();
        logMessageAreaScroll = new JScrollPane(logMessageArea);
        logMessageArea.setLineWrap(true);  // перенос слов
        logMessageArea.setWrapStyleWord(true);



        add(logMessageAreaScroll, BorderLayout.CENTER);
        add(sendMessageField, BorderLayout.SOUTH);

        setSize(WIDTH, HEIGHT); // установка размера окна
        setLocationRelativeTo(null);    // окно всегда в центре
        setAlwaysOnTop(true);   // окно поверх других окон

        setVisible(true);

        sendMessageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msg = sendMessageField.getText();
                sendMessageField.setText(null);
                try {
                    connection.sendMessage(msg + "\n");
                    logMessageArea.append(connect.getMessage() + "\n");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                timer.start();
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (connection.in.ready()) {
                String msg = connection.getMessage();
                printMsg(msg);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public synchronized void printMsg (String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                logMessageArea.append(msg + "\n");
                logMessageArea.setCaretPosition(logMessageArea.getDocument().getLength());

            }
        });
    }


}
