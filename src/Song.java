import com.sipgate.mp3wav.Converter;
import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Objects;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class Song {
    public File[] getSong() {
        return song;
    }

    public void setSong(File[] song) {
        this.song = song;
    }
    public File eachSong;

    File[] song;

    public Song(File[] songList) {
        song = songList;
    }

    public void convertFile(ArrayList<String> songName) {
        for (File i : song) {
            if (i.canRead()) {
                Final.songList.add(i.getName());

            } else {
                System.out.println("File" + i.getName());
            }
        }
    }

    public String chooseSong() {
        System.out.println(Final.songList.toString());
            int random = (int) (Math.random() * (Final.songList.size()));
            if (Final.songList.get(random).contains(".mp3")) {
                return "src/frank/"+Final.songList.get(random);
            } else {
                return "image";
            }
    }
    public String convertMP3ToWave(String chosenSong) throws IOException {
//        Converter s=new Converter(new File("src/frank/02 strawberry swing.mp3").toURL().openStream());
//        s.convertFrom(new File("src/frank/02 strawberry swing.mp3").toURL().openStream());
        Converter s=new Converter(new File("src/frank/02 strawberry swing.mp3").toURL().openStream());
        OutputStream out = new FileOutputStream("src/frank/test.wav");
        s.to(out);
        return "";

    }
}