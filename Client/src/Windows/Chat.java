package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Chat extends JFrame implements ActionListener {
    static JFrame frame;
    private JTextArea logMessageArea;
    private JScrollPane logMessageAreaScroll;
    private JTextField sendMessageField;
    private JPanel background;
    Client connection;
    Timer timer;

    public Chat (Client connection) throws IOException {
        this.connection = connection;

        timer = new Timer(100, this);
        timer.setInitialDelay(190);

        // new frame
        frame = new JFrame("ORB V.0.9");
        frame.setSize(650, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connection.finish();
            }
        });

        // add icon
        ImageIcon img = new ImageIcon("C:/Users/tipka/Desktop/IMG_small.png");
        frame.setIconImage(img.getImage());

//        background = new JPanel();
//        background.setBounds(5,5,640,440);
//        frame.add(background);

        final ImageIcon imageIcon = new ImageIcon("C:/Users/tipka/Desktop/IMG_background.png");
        // add log area
        logMessageArea = new JTextArea(){
            Image image = imageIcon.getImage();

            Image grayImage = GrayFilter.createDisabledImage(image);
            {
                setOpaque(false);
            }
            public void paint(Graphics g) {
                g.drawImage(grayImage, 0, 0, this);
                super.paint(g);
            }
        };
        logMessageArea.setFont(new Font("Consolas", Font.BOLD, 24));
        logMessageArea.setForeground(Color.DARK_GRAY);
        logMessageArea.setLineWrap(true);  // words wrap
        logMessageArea.setEditable(false);
        logMessageArea.setWrapStyleWord(true);  // whole words

        // add scrolling
        logMessageAreaScroll = new JScrollPane(logMessageArea);
        logMessageAreaScroll.setBounds(10,10,610,360);

        frame.add(logMessageAreaScroll);

        sendMessageField = new JTextField();
        sendMessageField.setBounds(10,380,610,25);
        sendMessageField.setBackground(Color.lightGray);
        sendMessageField.setFont(new Font("Calibri", Font.ITALIC, 18));
        sendMessageField.setForeground(Color.darkGray);
        frame.add(sendMessageField);
        sendMessageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msg = sendMessageField.getText();
                sendMessageField.setText(null);
                try {
                    connection.sendMessage(msg + "\n");
                    logMessageArea.append(connection.getMessage() + "\n");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                timer.start();
            }
        });

        frame.setVisible(true);
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

                logMessageArea.append(msg +"\n");
                logMessageArea.setCaretPosition(logMessageArea.getDocument().getLength());

            }
        });
    }
}
