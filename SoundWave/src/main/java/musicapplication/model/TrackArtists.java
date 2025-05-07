package musicapplication.model;

public class TrackArtists {
    private String trackId;   // The ID of the track (foreign key to Tracks table)
    private int artistId;     // The ID of the artist (foreign key to Artists table)

    // Constructor
    public TrackArtists(String trackId, int artistId) {
        this.trackId = trackId;
        this.artistId = artistId;
    }

    // Getter and Setter methods
    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    // Method to display the TrackArtist details track artists
    public void display() {
        System.out.println("Track ID: " + trackId);
        System.out.println("Artist ID: " + artistId);
    }
  
}
