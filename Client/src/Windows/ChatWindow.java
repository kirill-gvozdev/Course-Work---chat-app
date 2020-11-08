package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private JTextField fieldInput;
    private JTextArea log;

    public ChatWindow (ClientUser connection) {

        super("Messenger V.0.01");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        fieldInput = new JTextField();
        log = new JTextArea();

        add(fieldInput, BorderLayout.SOUTH);
        add(log, BorderLayout.CENTER);
//        add(buttonLogin, BorderLayout.WEST);
//        add(buttonRegistration, BorderLayout.EAST);

        setSize(WIDTH, HEIGHT); // установка размера окна
        setLocationRelativeTo(null);    // окно всегда в центре
        setAlwaysOnTop(true);   // окно поверх других окон

        setVisible(true);

        fieldInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = fieldInput.getText();  // у поля fieldInput через метод getText() получаем строчку, которую ввели
                if (msg.equals("")) return;         // если это было случайное нажатие. и строка пустая
                fieldInput.setText(null);           // очищаем поле для нового сообщения
                connection.sendMessage(msg);
                printMsg(msg);
            }
        });
    }

    private synchronized void printMsg (String msg) {
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

//    @Override
//    public void actionPerformed(ActionEvent e) { // метод вызывается при нажании на Enter
//        String msg = fieldInput.getText();  // у поля fieldInput через метод getText() получаем строчку, которую ввели
//        if (msg.equals("")) return;         // если это было случайное нажатие. и строка пустая
//        fieldInput.setText(null);           // очищаем поле для нового сообщения
//        //connection.sendMessage(msg);

}
