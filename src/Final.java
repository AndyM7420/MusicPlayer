import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Final{
    private JPanel panel1;
    private JPanel panel2;
    private Image backgroundImage;
    MediaTracker S;
    Long currentFrame;
    Clip clip;



    private JTextArea andySMusicPlayerTextArea;
    private AudioInputStream audioInputStream;
    private JPasswordField passwordField1;
    private JButton listenButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList list1;


    public Final() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        audioInputStream= AudioSystem.getAudioInputStream(new File("src/The Weeknd - Blinding Lights (Official Audio).wav").getAbsoluteFile());
                clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip
    }


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFrame frame=new JFrame("App");
        frame.setContentPane(new Final().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBackground(Color.BLUE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(540,390);
    }
    public int actionListener(){
        if(listenButton.isSelected()){
            System.out.println("Muy bien");
        }
        return -1;
    }



}
