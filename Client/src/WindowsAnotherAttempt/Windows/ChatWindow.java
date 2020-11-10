package WindowsAnotherAttempt.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatWindow extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private JTextField fieldInput;
    private JTextArea log;

    public ChatWindow (ClientUser connection) {

        super("Messenger V.0.01");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // При закрытии окна вызывается sendQuit из Client
                try {
                    connection.sendQuit();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        setSize(WIDTH, HEIGHT); // установка размера окна
        setLocationRelativeTo(null);    // окно всегда в центре
        setAlwaysOnTop(true);   // окно поверх других окон

        fieldInput = new JTextField();
        log = new JTextArea();

        add(fieldInput, BorderLayout.SOUTH);
        add(log, BorderLayout.CENTER);
//        add(buttonLogin, BorderLayout.WEST);
//        add(buttonRegistration, BorderLayout.EAST);

        connection.setLog(this);

        setVisible(true);

        fieldInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msg = fieldInput.getText();  // у поля fieldInput через метод getText() получаем строчку, которую ввели
                if (msg.equals("")) return;         // если это было случайное нажатие. и строка пустая
                fieldInput.setText(null);           // очищаем поле для нового сообщения
                try {
                    connection.sendMessage(msg);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                printMsg(msg);
            }
        });
    }

    public synchronized void printMsg (String msg) {
        // т.к. он вызывается из разных потоков, нужно снова написать конструкцию:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // внутри Runnable можно работать с элементами управления окном
                log.append(msg + "\n"); // добавляем присланную строку, не забывая про символ новой строки (он в строке сообщение отсутствует)
                // есть ещё одная заморочка JTextArea - даже если поставить авто скролл в true, он не будет корректно работать
                // чтобы заставить его гарантировано работать, и чтобы ткст автоматичсеки поднимался:
                log.setCaretPosition(log.getDocument().getLength()); //  установки коретки в самый конец документа
            }
        });

    }
}
