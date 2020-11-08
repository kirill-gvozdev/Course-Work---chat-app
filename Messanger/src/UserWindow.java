//import Windows.ClientUser;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.IOException;
//import java.awt.event.ActionListener;
//
//public class UserWindow extends JFrame implements ActionListener {
//
//    private static final int PORT = 8818;
//    private static final String HOST = "localhost";
//
//    private static final int WIDTH = 600;
//    private static final int HEIGHT = 400;
//
//    private ClientUser connection;
//
//    private static final boolean login = true;
//
//    // поле, куда пишется пересылаемый текст
//    private final JTextArea log = new JTextArea(); // класс JTextArea
//
//    // поле для никнейма, который потом остаётся на экране
//    public final JTextField fieldNickname = new JTextField("Enter user name");
//
//    public String userName = "";
//
//    // сюда пишется текст сообщения
//    private final JTextField fieldInput = new JTextField();
//
//
//
//    private UserWindow() throws IOException {
//
//        super("Messenger V.1");
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие окна Х
//        // Слушатель событий от нажатия на Х
//        this.addWindowListener (new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                // При закрытии окна отправляет управляющее слово quit в Client
//                //connection.sendQuit("quit");
//            }
//        });
//
//        setSize(WIDTH, HEIGHT); // установка размера окна
//        setLocationRelativeTo(null);    // окно всегда в центре
//        setAlwaysOnTop(true);   // окно поверх других окон
//
//        JButton button = new JButton("Login");
//        button.setBackground(Color.lightGray);
//        add (button, BorderLayout.CENTER);
//
//        add(fieldNickname, BorderLayout.NORTH);
//        fieldNickname.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                userName = fieldNickname.getText();
//            }
//        });
//
//
//        log.setEditable(false); // в окне недоступно редактирование (текст уже передался)
//        log.setLineWrap(true);  // автоматический перенос слов
//
//        add(log, BorderLayout.CENTER); // область переданного текста размещается в центре окна
//
//        add(fieldInput, BorderLayout.SOUTH);
//
//        fieldInput.addActionListener(this); // чтобы перехватить событие нажатие Enter на поле fieldInput методом addActionListener добавить самого себя
//
//        setVisible(true); // окно становится видимым
//
//        // создаётся новый клиент
//        try {
//           // connection = new ClientUser(this, HOST, PORT);
//     //   } catch (IOException e) {
//            System.out.println("Connection exception: " + e); // Если с самого начала не удалост установить соединение
//      //  }
//    }
//
//    public static void main(String[] args) {
//        // во всех графических интерфейсах есть ограничения по многпоточности: нельзя работать из разных потоков
//        // некоторые интерфейсы требуют, чтобы можно было работать только из главного main потока
//        // со swing можно работать только из потока EDT
//        // конструкция ниже застявляет строку new ClientWindow(); выполниться в потоке EDT
//        System.out.println();
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    new UserWindow();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) { // метод вызывается при нажании на Enter
//        String msg = fieldInput.getText();  // у поля fieldInput через метод getText() получаем строчку, которую ввели
//        if (msg.equals("")) return;         // если это было случайное нажатие. и строка пустая
//        fieldInput.setText(null);           // очищаем поле для нового сообщения
//        //connection.sendMessage(msg);
//
//
//
////            connection.sendMessage(fieldNickname.getText() + " " + msg); // sendMessage из Client-а
//
//    }
//
//    public void getMessageFromClient (ClientUser connection, String msg) {
//        printMsg(msg);
//    }
//
//    public void onException(ClientUser clientUser, Exception e) {
//        printMsg("Connection exception: " + e);
//    }
//
//    public void onDisconnect(ClientUser clientUser) {
//        printMsg("Connection closed");
//    }
//
//    private synchronized void printMsg (String msg) {
//        // т.к. он вызывается из разных потоков, нужно снова написать конструкцию:
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                // внутри Runnable можно работать с элементами управления окном
//                log.append(msg + "\n"); // добавляем присланную строку, не забывая про символ новой строки (он в строке сообщение отсутствует)
//                // есть ещё одная заморочка JTextArea - даже если поставить авто скролл в true, он не будет корректно работать
//                // чтобы заставить его гарантировано работать, и чтобы ткст автоматичсеки поднимался:
//                log.setCaretPosition(log.getDocument().getLength()); //  установки коретки в самый конец документа
//            }
//        });
//
//    }
//}
