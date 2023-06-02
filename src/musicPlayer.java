import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.TileObserver;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class musicPlayer extends JFrame implements ActionListener {
    public JPanel panel1;
    private JPanel panel2;
    private static Image backgroundImage;
    private boolean sliderSong;
    private Long clipTimePosition;
    private int duration;
    private Clip clip;
    private int count;
    private AudioInputStream audioInputStream;
    private JPasswordField passwordField1;
    private DataLine own;
    private JButton randomButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList<String> list1;
    private JSlider slider1;
    private JPanel shit;
    private JButton pauseButton;
    private JButton forwardButton;
    private JButton restart;
    private JLabel Title;
    public static ArrayList<String> songList=new ArrayList<String>();
    DefaultListModel<String> model = new DefaultListModel<>();
    JLabel label3 = new JLabel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();
    File songs;
    private actionSong first;
    private Song nose;
    public musicPlayer() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        restart.setSize(2, 3);
        JOptionPane.showMessageDialog(null, "Press listen to start song");
        nose=new Song(new File("src/frank").listFiles(),songList);
        nose.convertFile(songList);
        songs = new File(nose.chooseSong());
        Title.setSize(20,30);
        audioInputStream = AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
        ImageIcon restarts = new ImageIcon("src/download.png");
        ImageIcon pause = new ImageIcon("src/61180.png");
        ImageIcon forward = new ImageIcon("src/images.png");
        restarts.setImage(setImageButtons(restarts));
        pause.setImage(setImageButtons(pause));
        forward.setImage(setImageButtons(forward));
        pauseButton.setIcon(pause);
        forwardButton.setIcon(forward);
        restart.setIcon((restarts));
        list1.setModel(model);
        list1.setOpaque(true);
        if (!sliderSong) {
            slider1.setVisible(false);
        }
        first=new actionSong(songs);
        playArtistButton.addActionListener(this);
        restart.addActionListener(this);
        recommendButton.addActionListener(this);
        randomButton.addActionListener(this);
        pauseButton.addActionListener(this);


    }


    public String songName(String songName) {

        if (songName.contains("(")) {
            return songName.substring(0, songName.indexOf("("));
        }
        return songName.substring(0, songName.indexOf("."));
    }
    public Image setImageButtons(ImageIcon icons) {
        ImageIcon temp = icons;
        Image image = temp.getImage();
        return image.getScaledInstance(45, 45, Image.SCALE_FAST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playArtistButton) {
            if(Objects.equals(first.getTimeStamp(), "")){
                model.addElement(songName(first.getSong().getName()));
            }
            try {
                first.play();
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("wrong file dude!");
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            slider1.setVisible(true);
        } else if (e.getSource() == restart) {

        } else if (e.getSource() == recommendButton) {

        } else if (e.getSource() == randomButton) {
            try {
                first.random();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == pauseButton) {
            first.pause();
        }
    }
}