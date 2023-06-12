import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;

public class Split {
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



    URL songPic;
    URL artistPic;
    String[] split;

    public Split(HttpResponse bodies) throws MalformedURLException {
        split = bodies.body().toString().split(",");
        for (String s : split) {
            System.out.println(s);
        }

        for (int i = 0; i < split.length; i++) {
             if (split[i].contains("artist_names")) {
                artist_names = split[i].substring(split[i].indexOf(":\"") + 2, split[i].length() - 1);
            } else if (split[i].contains("\"id\"")) {
                id = Integer.parseInt(split[i].substring(split[i].indexOf(":") + 1));
            } else if (split[i].contains("\"header_image_thumbnail_url\"") && i == 8) {
                artistPic = new URL(ridOfSlashURl(split[i].substring(split[i].indexOf(":") + 2, split[i].length() - 1)));
            } else if (split[i].contains("release_date_for_display")) {
                albumReleaseDate = split[i].substring(split[i].indexOf(":") + 2) + split[i + 1].substring(0, split[i + 1].length() - 1);
            } else if (split[i].contains("song_art_image_thumbnail_url")) {
                songPic = new URL(ridOfSlashURl(split[i].substring(split[i].indexOf(":") + 2, split[i].length() - 1)));
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

    public String[] getSplit() {
        return split;
    }

    public void setSplit(String[] split) {
        this.split = split;
    }
}


