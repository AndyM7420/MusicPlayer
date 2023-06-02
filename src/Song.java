
import javax.print.attribute.standard.PrinterMakeAndModel;
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

    public Song(File[] songList,ArrayList<String>songLists) {

        song = songList;
        this.convertFile(songLists);
    }

    public void convertFile(ArrayList<String> songName) {
        for (File i : song) {
            if (i.canRead()) {
                musicPlayer.songList.add(i.getName());

            }
        }
    }

    public String chooseSong() {
        int random = (int) (Math.random() * (musicPlayer.songList.size()));
        while (!musicPlayer.songList.get(random).contains("wav")) {
             random = (int) (Math.random() * (musicPlayer.songList.size()));
        }
                return "src/frank/" + musicPlayer.songList.get(random);
    }
}