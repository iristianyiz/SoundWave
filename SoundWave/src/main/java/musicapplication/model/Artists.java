package musicapplication.model;

public class Artists {
    private int artistId;
    private String artistName;

    // Constructor
    public Artists(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    // Getter and Setter methods
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    
 // Method to display the artist details
    public void display() {
        System.out.println("Artist ID: " + artistId);
        System.out.println("Artist Name: " + artistName);
    }

}
