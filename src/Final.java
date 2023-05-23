import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Final extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private static Image backgroundImage;
    private boolean sliderSong;
    private Long clipTimePosition;
    private int duration;
    private int count;
    Clip clip;
    private JTextArea andySMusicPlayerTextArea;
    private AudioInputStream audioInputStream;
    private JPasswordField passwordField1;
    private DataLine own;
    private JButton listenButton;
    private JButton recommendButton;
    private JButton playArtistButton;
    private JList<String> list1;
    private JSlider slider1;
    private JPanel shit;
    private JButton pause;
    private JButton resume;
    private JButton play;
    DefaultListModel<String> model=new DefaultListModel<>();
    JLabel label3=new JLabel();
    JPanel panel=new JPanel();
    JSplitPane splitPane=new JSplitPane();
    File songs;
    String timeStamp;


    public Final() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        play.setSize(2,3);
        JOptionPane.showMessageDialog(null,"Press play to start song");
        songs=new File("src/03 novacane.wav");
        ImageIcon icon=new ImageIcon("src/117815-200-removebg-preview.png");
        Image image=icon.getImage();
        Image newimg=image.getScaledInstance(120,120, Image.SCALE_FAST);
        audioInputStream= AudioSystem.getAudioInputStream(songs.getAbsoluteFile());
        play.setIcon((newimg);
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



                        clip.open(audioInputStream);
                        clip.start();
                        count++;
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                slider1.setVisible(true);


            }
        });
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resume();
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        listenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resume();
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
        if(clip.isRunning()&&timeStamp.equals("playing")){
            clipTimePosition=clip.getMicrosecondPosition();
            clip.stop();
            clip.close();
            timeStamp="paused";
        }
    }
    public void resume() throws LineUnavailableException, IOException {

            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public double geDesiredFrame(){
        int frame= slider1.getValue();
        double only= ((double)audioInputStream.getFormat().getFrameRate());
        return only;
    }
    public String songName(String songName) {

        if (songName.contains("(")) {
            return songName.substring(0, songName.indexOf("("));
        }
        return songName.substring(0,songName.indexOf("."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
