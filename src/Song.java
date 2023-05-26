import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Song {
    public File[] getSong() {
        return song;
    }

    public void setSong(File[] song) {
        this.song = song;
    }

    File[] song;
    public  Song(File[] songList){
        song=songList;
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
    public String chooseSong(){
        for(int i=0;i<Final.songList.size();i++){

        }
    }
}
