package Windows;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatWindow extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private JTextField sendMessageField;
    private JTextArea logMessageArea;
    Client connection;

    public ChatWindow (Client connect) throws IOException {

        super("Messenger V.0.01");

        this.connection = connect;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sendMessageField = new JTextField();
        logMessageArea = new JTextArea();

        add(sendMessageField, BorderLayout.SOUTH);
        add(logMessageArea, BorderLayout.CENTER);

        setSize(WIDTH, HEIGHT); // установка размера окна
        setLocationRelativeTo(null);    // окно всегда в центре
        setAlwaysOnTop(true);   // окно поверх других окон

        setVisible(true);


//
//        // Listen for changes in the text
//        logMessageArea.getDocument().addDocumentListener(new DocumentListener() {
//            String changedValueOfServerAnswer = log.getText();;
//            public void changedUpdate(DocumentEvent e) {
//                updateChatLog(changedValueOfServerAnswer);
//            }
//            public void removeUpdate(DocumentEvent e) {
//                updateChatLog(changedValueOfServerAnswer);
//            }
//            public void insertUpdate(DocumentEvent e) {
//                updateChatLog(changedValueOfServerAnswer);
//            }
//
//            private String serverAnswer = connection.getMessage();
//
//            public void updateChatLog(String changedValueOfServerAnswer) {
//                if (!changedValueOfServerAnswer.equals(serverAnswer)){
//                  performUpdateChatLog(serverAnswer);
//                  serverAnswer = changedValueOfServerAnswer;
//                }
//            }
//            public void performUpdateChatLog(String serverAnswer) {
//                logMessageArea.append(serverAnswer + "\n");
//            }
//        });

        sendMessageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = sendMessageField.getText();  // у поля fieldInput через метод getText() получаем строчку, которую ввели
                //if (msg.equals("")) return;         // если это было случайное нажатие. и строка пустая
                sendMessageField.setText(null);           // очищаем поле для нового сообщения
                //logMsg(msg);
                try {
                    connection.sendMessage(msg);
                    logMessageArea.append(connect.getMessage() + "\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public void logMsg (String msg) {
        try {
            logMessageArea.append(connection.getMessage() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printMsg (String msg) {
        // т.к. он вызывается из разных потоков, нужно снова написать конструкцию:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // внутри Runnable можно работать с элементами управления окном
                logMessageArea.append(msg + "\n"); // добавляем присланную строку, не забывая про символ новой строки (он в строке сообщение отсутствует)
                // есть ещё одная заморочка JTextArea - даже если поставить авто скролл в true, он не будет корректно работать
                // чтобы заставить его гарантировано работать, и чтобы ткст автоматичсеки поднимался:
                logMessageArea.setCaretPosition(logMessageArea.getDocument().getLength()); //  установки коретки в самый конец документа

            }
        });
    }

}
