import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.TileObserver;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class musicPlayer extends JFrame implements ActionListener, HierarchyListener {
    public JPanel panel1;
    private JPanel panel2;
    private static Image backgroundImage;
    private boolean sliderSong;
    private Timer timeOfSong;
    private Long clipTimePosition;
    private int duration;
    private Clip clip;
    private int count;
    private AudioInputStream audioInputStream;
    private DataLine own;
    private JButton randomButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList<String> list1;

    private   JSlider slider1;
    private JPanel shit;
    private JButton pauseButton;
    private JButton forwardButton;
    private JButton restart;
    private JLabel Title;
    private JTextField textField1;
    private JTextField list2;
    public static ArrayList<String> songList=new ArrayList<String>();
    DefaultListModel<String> model = new DefaultListModel<>();
    JLabel label3 = new JLabel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();
    File songs;
    private actionSong first;
    private Song nose;
    public musicPlayer() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        slider1.setSize(1, 1);
        restart.setSize(2, 3);
        JOptionPane.showMessageDialog(null, "Press listen to start song");
        nose = new Song(new File("D:frank").listFiles(), songList);
        nose.convertFile(songList);
        songs = new File(nose.chooseSong());
        Title.setSize(20, 30);
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
        first = new actionSong(songs);
        playArtistButton.addActionListener(this);
        restart.addActionListener(this);
        recommendButton.addActionListener(this);
        randomButton.addActionListener(this);
        pauseButton.addActionListener(this);
        forwardButton.addActionListener(this);


        list2.addHierarchyListener(this);
        list2.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {

            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });
    }
    public void moveSlider(){
        slider1.setValue((int) first.getClipTimePosition());
        System.out.println(first.getAudioInputStream().getFrameLength());
        timeOfSong=new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==slider1){

                }
            }
        });
        timeOfSong.start();
    }
    public void update(){

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
                ImageIcon changeImage = new ImageIcon("src/117815-200-removebg-preview.png");
                changeImage.setImage(setImageButtons(changeImage));
                pauseButton.setIcon(changeImage);
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("wrong file dude!");
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            slider1.setSize(5,5);
            moveSlider();
            slider1.setVisible(true);
        } else if (e.getSource() == restart) {
            try {
                first.restart();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == recommendButton) {

        } else if (e.getSource() == randomButton) {
            try {
                first.random();
                model.addElement(songName(first.getSong().getName()));
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == pauseButton) {
            first.pause();
        }else if(e.getSource()==forwardButton){
            try {
                first.forward();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {

    }
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
    public JSlider getSlider1() {
        return slider1;
    }
    public int getDesiredFrame(){
        int progress = slider1.getValue();
        double frame = ((double) first.getAudioInputStream().getFrameLength() * ((double) progress / 100.0));
        return (int) frame;
    }
}