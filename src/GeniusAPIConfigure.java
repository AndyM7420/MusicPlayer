
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeniusAPIConfigure {
    private HttpRequest request;
    private  String rapidAPIKey;
    private String createURI;
    private String searchSong;


    public SongInfo getMetadata() {
        return metadata;
    }

    public void setMetadata(SongInfo metadata) {
        this.metadata = metadata;
    }

    private SongInfo metadata;

    public String getRapidAPIKey() {
        return rapidAPIKey;
    }

    public void setRapidAPIKey(String rapidAPIKey) {
        this.rapidAPIKey = rapidAPIKey;
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }

    private HttpResponse<String> response;
    public GeniusAPIConfigure(String searchSong) throws IOException, InterruptedException {
        this.searchSong="q="+fillSpace(searchSong)+"&per_page=1&page=1";
        rapidAPIKey="fc46e8e060mshb4afaef40775858p12604fjsn24c0e6fb879f";
        request = HttpRequest.newBuilder().uri(URI.create("https://genius-song-lyrics1.p.rapidapi.com/search/?"+this.searchSong))
                .header("X-RapidAPI-Key", rapidAPIKey)
                .header("X-RapidAPI-Host", "genius-song-lyrics1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();
        createURI= request.uri().getQuery();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        request();
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getCreateURI() {
        return createURI;
    }
    public void request() throws IOException, InterruptedException {
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        metadata=new SongInfo(response);
    }
    public String fillSpace(String songWithSpace){

        String temp="";
        if(songWithSpace.contains(" ")) {
            while (songWithSpace.contains(" ")) {
                temp += songWithSpace.substring(0, songWithSpace.indexOf(" ")) + "%20";
                songWithSpace = songWithSpace.substring(songWithSpace.indexOf(" ") + 1);
            }
            temp += songWithSpace;
            return temp;
        }else{
            return songWithSpace;
        }
    }
}