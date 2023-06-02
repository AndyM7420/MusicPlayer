import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class actionSong {
    private Clip clip;
    private boolean firstAttempt;

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    public void setAudioInputStream(AudioInputStream audioInputStream) {
        this.audioInputStream = audioInputStream;
    }

    public long getClipTimePosition() {
        return clipTimePosition;
    }

    public void setClipTimePosition(long clipTimePosition) {
        this.clipTimePosition = clipTimePosition;
    }

    public File getSong() {
        return song;
    }

    public void setSong(File song) {
        this.song = song;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private AudioInputStream audioInputStream;
    private long clipTimePosition;
    private File song;
    private String timeStamp = "";

    public actionSong(File song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.song = song;
        audioInputStream = AudioSystem.getAudioInputStream(song.getAbsoluteFile());
        clip = AudioSystem.getClip();
    }

    public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        timeStamp = "playing";
        audioInputStream = AudioSystem.getAudioInputStream(song.getAbsoluteFile());
        clip.start();
        if (timeStamp.equals("playing") && !firstAttempt) {
            clip.open(audioInputStream);
            firstAttempt = true;
        }

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void pause() {
        if (clip.isRunning() && timeStamp.equals("playing")) {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
            timeStamp = "paused";
        }
    }

    public void restartAudioStream(File chosenSong) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        song = chosenSong;
        audioInputStream = AudioSystem.getAudioInputStream(song.getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void resume() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (timeStamp != null && timeStamp.equals("paused")) {
            clip.close();
            restartAudioStream(song);
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
        } else {
            JOptionPane.showMessageDialog(null, "HAVEN'T PLAYED SONG");
        }
    }
    public void restart() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        clip.close();
        clip.stop();
        clip.setFramePosition(0);
        play();
    }


    public void random() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (!timeStamp.equals("random")) {
            clip.close();
            clip.stop();
            Song newSong = new Song(new File("src/frank").listFiles(), musicPlayer.songList);
            new actionSong(new File(newSong.chooseSong()));
            restartAudioStream(new File(newSong.chooseSong()));
            System.out.println(newSong.chooseSong());
            play();
            timeStamp="random";

        }else{
            clip.stop();
            Song newSong = new Song(new File("src/frank").listFiles(), musicPlayer.songList);
            new actionSong(new File(newSong.chooseSong()));
            restartAudioStream(new File(newSong.chooseSong()));
            System.out.println(newSong.chooseSong());
            play();
        }
    }
    public void forward() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        clip.setFramePosition(clip.getFramePosition()+20);
        play();
    }
}