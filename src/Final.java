import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Final extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private static Image backgroundImage;
    Long currentFrame;
    Clip clip;
    private JTextArea andySMusicPlayerTextArea;
    private AudioInputStream audioInputStream;
    private JPasswordField passwordField1;
    private JButton listenButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList<String> list1;
    DefaultListModel<String> model=new DefaultListModel<>();
    JLabel label3=new JLabel();
    JPanel panel=new JPanel();
    JSplitPane splitPane=new JSplitPane();
    File songs;
    String timeStamp;


    public Final() throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        songs=new File("src/The Weeknd - Blinding Lights (Official Audio).wav");
        audioInputStream= AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
        clip=AudioSystem.getClip();
        list1.setModel(model);

        playArtistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeStamp="playing";
                model.addElement(songs.getName());
                try {
                    clip.open(audioInputStream);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
        });
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

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
    public void pause(){
        if(timeStamp.equals("playing")){
            clip.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
