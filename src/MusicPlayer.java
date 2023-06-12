import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MusicPlayer extends JFrame implements ActionListener, HierarchyListener, MouseListener, KeyListener {
    public JPanel panel1;
    private JPanel panel2;
    private boolean sliderSong;
    private Timer timeOfSong;
    private JPanel buttonPanel;
    private JButton randomButton;
    private JButton resume;
    private JList<String> listPlayedSongs;
    private   JSlider songSlider;
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
    DefaultListModel<String> transferredSongs = new DefaultListModel<>();
    DefaultListModel<String> songListToSearch = new DefaultListModel<>();
    private JLabel label3 = new JLabel();
    private JPanel panel = new JPanel();
    File song;
    private ActionSong songPlayer;

    private Song songLibrary;
    public MusicPlayer() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        JOptionPane.showMessageDialog(null, "Click the search box to search Catalog.\nClick Random to get random songs");
        songLibrary = new Song(new File("D:frank").listFiles(), songList);
        songLibrary.convertFile(songList);
        song = new File(songLibrary.chooseSong());
        Title.setSize(20, 30);
        ImageIcon restarts = new ImageIcon("src/download.png");
        ImageIcon pause = new ImageIcon("src/61180.png");
        ImageIcon forward = new ImageIcon("src/images.png");
        restarts.setImage(scaleImage(restarts));
        pause.setImage(scaleImage(pause));
        forward.setImage(scaleImage(forward));
        pauseButton.setIcon(pause);
        forwardButton.setIcon(forward);
        restart.setIcon((restarts));
        if (!sliderSong) {
            songSlider.setVisible(false);
        }
        listPlayedSongs.setModel(transferredSongs);
        listPlayedSongs.setOpaque(true);
        songPlayer = new ActionSong(song);
        songSlider.setPaintTicks(true);
        songSlider.setPaintTrack(true);
        songSlider.setPaintLabels(true);
        songSlider.setMaximum(songPlayer.getSongDurationInMinute());
        resume.addActionListener(this);
        restart.addActionListener(this);
        randomButton.addActionListener(this);
        pauseButton.addActionListener(this);
        forwardButton.addActionListener(this);
        panel.add(songSlider);
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
                songPlayer.setSong(new File("D:frank\\"+list2.getSelectedValue()));
                songPlayer.stop();
                songPlayer.play();
                transferredSongs.addElement(songName(list2.getSelectedValue()));
                GeniusAPIConfigure songData=new GeniusAPIConfigure(SongNameForAPI(list2.getSelectedValue()));
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
            JOptionPane.showMessageDialog(rootPane,list2.getSelectedValue()+"\nDuration:"+ songPlayer.getSongDuration(),"Selected Song:",JOptionPane.INFORMATION_MESSAGE);
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
        songSlider.setValue((int) songPlayer.getClipTimePosition());
        timeOfSong=new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== songSlider){
                }
            }
        });
        timeOfSong.start();
    }
    public void bindData(){
        String songName = "";
        for (String song : songList) {
            if (song.contains("wav")) {
                songListToSearch.addElement(song);

            }
        }
        list2.setModel(songListToSearch);
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
        songListToSearch =filteredSongs;
        list2.setModel(songListToSearch);
    }

    public String SongNameForAPI(String songName){
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

    public Image scaleImage(ImageIcon icons) {
        Image image = icons.getImage();
        return image.getScaledInstance(45, 45, Image.SCALE_FAST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resume) {
            if (Objects.equals(songPlayer.getTimeStamp(), "")) {
                transferredSongs.addElement(songName(songPlayer.getSong().getName()));
            }
            try {
                songPlayer.play();
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("wrong file dude!");
                throw new RuntimeException(ex);
            } catch (IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }

            songSlider.setVisible(true);
        } else if (e.getSource() == restart) {
                songPlayer.decreaseBy10();
        } else if (e.getSource() == randomButton) {
            try {
                songPlayer.random();
                transferredSongs.addElement(songName(songPlayer.getSong().getName()));
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == pauseButton) {
            songPlayer.pause();
        } else if (e.getSource() == forwardButton) {
            songPlayer.increaseBy10();

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
    public JSlider getSongSlider() {
        return songSlider;
    }

    public void changePause(){
        ImageIcon changeImage = new ImageIcon("src/117815-200-removebg-preview.png");
        changeImage.setImage(scaleImage(changeImage));
        pauseButton.setIcon(changeImage);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }
}