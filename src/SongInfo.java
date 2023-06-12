import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;

public class SongInfo {
    String albumReleaseDate;
    String artist_names;
    String songTitle;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URL getSongPic() {
        return songPic;
    }

    public void setSongPic(URL songPic) {
        this.songPic = songPic;
    }

    public URL getArtistPic() {
        return artistPic;
    }

    public void setArtistPic(URL artistPic) {
        this.artistPic = artistPic;
    }



    private URL songPic;
    private URL artistPic;
    private String[] songInfoKeys;

    public SongInfo(HttpResponse bodies) throws MalformedURLException {
        songInfoKeys = bodies.body().toString().split(",");
        for (int i = 0; i < songInfoKeys.length; i++) {
             if (songInfoKeys[i].contains("artist_names")) {
                artist_names = songInfoKeys[i].substring(songInfoKeys[i].indexOf(":\"") + 2, songInfoKeys[i].length() - 1);
            } else if (songInfoKeys[i].contains("\"id\"")) {
                id = Integer.parseInt(songInfoKeys[i].substring(songInfoKeys[i].indexOf(":") + 1));
            } else if (songInfoKeys[i].contains("\"header_image_thumbnail_url\"") && i == 8) {
                artistPic = new URL(ridOfSlashURl(songInfoKeys[i].substring(songInfoKeys[i].indexOf(":") + 2, songInfoKeys[i].length() - 1)));
            } else if (songInfoKeys[i].contains("release_date_for_display")) {
                albumReleaseDate = songInfoKeys[i].substring(songInfoKeys[i].indexOf(":") + 2) + songInfoKeys[i + 1].substring(0, songInfoKeys[i + 1].length() - 1);
            } else if (songInfoKeys[i].contains("song_art_image_thumbnail_url")) {
                songPic = new URL(ridOfSlashURl(songInfoKeys[i].substring(songInfoKeys[i].indexOf(":") + 2, songInfoKeys[i].length() - 1)));
            }
        }
        System.out.println(albumReleaseDate);
        System.out.println(songPic);
        System.out.println(id);
        System.out.println(artistPic);
        System.out.println(artist_names);
    }

    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(String albumReleaseDate) {
        this.albumReleaseDate = albumReleaseDate;
    }

    public String getArtist_names() {
        return artist_names;
    }

    public String ridOfSlashURl(String url) {
        String ridOfSlash = "";
        while (url.contains("\\")) {
            ridOfSlash += url.substring(0, url.indexOf("\\"));
            url = url.substring(url.indexOf("\\") + 1);
        }
        ridOfSlash += url;
        return ridOfSlash;
    }

    public void setArtist_names(String artist_names) {
        this.artist_names = artist_names;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String[] getSongInfoKeys() {
        return songInfoKeys;
    }

    public void setSongInfoKeys(String[] songInfoKeys) {
        this.songInfoKeys = songInfoKeys;
    }
}


