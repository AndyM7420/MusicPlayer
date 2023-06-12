import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class musicPlayer extends JFrame implements ActionListener, HierarchyListener, MouseListener, KeyListener {
    public JPanel panel1;
    private JPanel panel2;
    private static Image backgroundImage;
    private boolean sliderSong;
    private Timer timeOfSong;
    private Long clipTimePosition;
    private int duration;
    private Clip clip;
    private int count;
    private JPanel buttonPanel;
    private AudioInputStream audioInputStream;
    private DataLine own;
    private JButton randomButton;
    private JButton recommendButton;
    private JButton Resume;
    private JList<String> list1;

    private   JSlider slider1;
    private JButton pauseButton;
    private JButton forwardButton;
    private JButton restart;
    private JLabel Title;
    private JTextField textField1;
    private JList<String> list2;
    private JPanel albumCoverPanel;
    private JButton coverAlbumButton;
    private JLabel picLabel;
    public static ArrayList<String> songList=new ArrayList<String>();
    DefaultListModel<String> model = new DefaultListModel<>();
    DefaultListModel<String> defaultListModel = new DefaultListModel<>();


    JLabel label3 = new JLabel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();
    File songs;
    private actionSong first;

    private Song nose;
    public musicPlayer() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        restart.setSize(2, 3);
        JOptionPane.showMessageDialog(null, "Click the search box to search Catalog.\nClick Random to get recent songs");
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
        if (!sliderSong) {
            slider1.setVisible(false);
        }
        list1.setModel(model);
        list1.setOpaque(true);
        first = new actionSong(songs);
        slider1.setFont(new Font("MV Boli", BOLD, 10));
        slider1.setPaintTicks(true);
        slider1.setPaintTrack(true);
        slider1.setPaintLabels(true);
        slider1.setMaximum(first.getSongDurationInMinute());
        Resume.addActionListener(this);
        restart.addActionListener(this);
        randomButton.addActionListener(this);
        pauseButton.addActionListener(this);
        forwardButton.addActionListener(this);
        panel.add(slider1);
        list2.addHierarchyListener(this);
       list2.addMouseListener(this);
       textField1.addMouseListener(this);
       textField1.addKeyListener(this);

    }
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==textField1){
            bindData();
        }
        if(e.getSource()==list2){
            try {
                first.setSong(new File("D:frank\\"+list2.getSelectedValue()));
                first.stop();
                first.play();
                model.addElement(songName(list2.getSelectedValue()));
                GeniusAPIConfigure songData=new GeniusAPIConfigure(onlySongName(list2.getSelectedValue()));
                BufferedImage albumCover= ImageIO.read(songData.getMetadata().getSongPic());
                coverAlbumButton.setIcon(new ImageIcon(albumCover));
               System.out.println(songData.getMetadata().getSongTitle());

            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(rootPane,list2.getSelectedValue()+"\nDuration:"+first.getSongDuration(),"Selected Song:",JOptionPane.INFORMATION_MESSAGE);
    }}

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void moveSlider(){
        slider1.setValue((int) first.getClipTimePosition());
        timeOfSong=new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==slider1){
                }
            }
        });
        timeOfSong.start();
    }
    public void bindData(){
        String songName = "";
        for (String s : songList) {
            if (s.contains("wav")) {
                defaultListModel.addElement(s);

            }
        }
        list2.setModel(defaultListModel);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private void searchFilter(String searchTerm){
        DefaultListModel filteredSongs=new DefaultListModel();
        ArrayList<String> filteredSongList=songList;
        for(int i=0;i<songList.size();i++){
            String songName=songList.get(i).toLowerCase();
            if(songName.contains(searchTerm.toLowerCase())){
                filteredSongs.addElement(songList.get(i));
            }
        }
        defaultListModel=filteredSongs;
        list2.setModel(defaultListModel);
    }

    public String onlySongName(String songName){
        if(songName.contains(" - ")){
            songName=songName.substring(songName.indexOf("- ")+2,songName.indexOf(".wav"));
        }
        return songName;
    }
    public String songName(String songName) {
        if(songName.contains("wav")){
            return songName.substring(0, songName.indexOf("wav")-1);
        }
        return songName;
    }

    public Image setImageButtons(ImageIcon icons) {
        Image image = icons.getImage();
        return image.getScaledInstance(45, 45, Image.SCALE_FAST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Resume) {
            if (Objects.equals(first.getTimeStamp(), "")) {
                model.addElement(songName(first.getSong().getName()));
            }
            try {
                first.play();
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("wrong file dude!");
                throw new RuntimeException(ex);
            } catch (IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }

            slider1.setVisible(true);
        } else if (e.getSource() == restart) {
                first.decreaseBy10();
        } else if (e.getSource() == randomButton) {
            try {
                first.random();
                model.addElement(songName(first.getSong().getName()));
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == pauseButton) {
            first.pause();
        } else if (e.getSource() == forwardButton) {
            first.increaseBy10();

        }
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
    public void changePause(){
        ImageIcon changeImage = new ImageIcon("src/117815-200-removebg-preview.png");
        changeImage.setImage(setImageButtons(changeImage));
        pauseButton.setIcon(changeImage);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource()==textField1){
            searchFilter(textField1.getText());
        }

    }


    @Override
    public void hierarchyChanged(HierarchyEvent e) {

    }
    public void keyReleased(KeyEvent e) {
    }
}