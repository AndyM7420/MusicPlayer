
import java.io.File;
import java.util.ArrayList;

public class Song {
    private String randomSong;

    public File[] getSong() {
        return song;
    }

    public void setSong(File[] song) {
        this.song = song;
    }
    public File eachSong;

     private File[] song;

    public Song(File[] songList,ArrayList<String>songLists) {

        song = songList;
        this.convertFile(songLists);
    }

    public void convertFile(ArrayList<String> songName) {
        for (File i : song) {
            if (i.canExecute()&& MusicPlayer.songList.size()!=song.length) {
                MusicPlayer.songList.add(i.getName());
            }
        }
    }

    public String chooseSong() {
        int random = (int) (Math.random() * (MusicPlayer.songList.size()));
        while (!MusicPlayer.songList.get(random).contains("wav")) {
            random=(int) (Math.random() * (MusicPlayer.songList.size()));
            System.out.println(MusicPlayer.songList.get(random));
        }
        randomSong= MusicPlayer.songList.get(random);
        return "D:frank\\"+ MusicPlayer.songList.get(random);
    }
}
