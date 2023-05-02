import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;

public class Final{
    private JPanel panel1;
    private JPanel panel2;
    private Image backgroundImage;


    private JTextArea andySMusicPlayerTextArea;
    private JPasswordField passwordField1;
    private JButton listenButton;
    private JButton recommendButton;
    private JButton playArtistButton;

    public Final() {
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("App");
        frame.setContentPane(new Final().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(540,390);
    }


}
