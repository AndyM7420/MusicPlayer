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
    private boolean sliderSong;
    Long currentFrame;
    int count;
    Clip clip;
    private JTextArea andySMusicPlayerTextArea;
    private AudioInputStream audioInputStream;
    private JPasswordField passwordField1;
    private JButton listenButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList<String> list1;
    private JSlider slider1;
    DefaultListModel<String> model=new DefaultListModel<>();
    JLabel label3=new JLabel();
    JPanel panel=new JPanel();
    JSplitPane splitPane=new JSplitPane();
    File songs;
    String timeStamp;


    public Final() throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        songs=new File("src/The Weeknd - Blinding Lights (Official Audio).wav");
        ImageIcon icon=new ImageIcon();
        audioInputStream= AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
        clip=AudioSystem.getClip();
        list1.setModel(model);
        list1.setOpaque(true);
        if(!sliderSong){
            slider1.setVisible(false);
        }

        playArtistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeStamp="playing";
                model.addElement(songName(songs.getName()));
                try {
                    if(count==0) {


                        clip.open(audioInputStream);
                        count++;
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    }else{
                        clip.open(audioInputStream);
                        slider1.setValue((int)clip.getMicrosecondPosition());
                    }
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                slider1.setVisible(true);
                slider1.setMinorTickSpacing(1);
                if(clip.isRunning()){
                    slider1.setValue(clip.getFramePosition());
                }

            }
        });
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clip.getMicrosecondPosition();
                clip.close();
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
        frame.setSize(840,690);
        frame.setLocationRelativeTo(null);

    }
    public void pause(){
        if(clip.isRunning()){
            clip.stop();
            clip.close();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public double geDesiredFrame(){
        int frame= slider1.getValue();
        double only= ((double)audioInputStream.getFormat().getFrameRate());
        return only;
    }
    public String songName(String songName){
        return songName.substring(0,songName.indexOf("("));
    }
}
