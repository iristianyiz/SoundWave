package musicapplication.model;

import java.sql.Timestamp;

/**
 * Represents a listening history record in the music application.
 */
public class ListeningHistory {
    private int historyId;
    private Timestamp created;
    private int timesListened;
    private int duration;
    private String trackId;
    private String userName;
    private String trackName;

    /**
     * Constructs a new ListeningHistory object.
     *
     * @param historyId   The unique identifier for the listening history.
     * @param created     The timestamp when the listening history was created.
     * @param timesListened The number of times the track has been listened to.
     * @param duration    The total duration of time the track has been listened to (in seconds).
     * @param trackId     The identifier of the track associated with this listening history.
     * @param userName    The username of the user who listened to the track.
     */
    public ListeningHistory(int historyId, Timestamp created, int timesListened, int duration, String trackId, String userName) {
        this.historyId = historyId;
        this.created = created;
        this.timesListened = timesListened;
        this.duration = duration;
        this.trackId = trackId;
        this.userName = userName;
    }

    // Getters and setters
    
    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getTimesListened() {
        return timesListened;
    }

    public void setTimesListened(int timesListened) {
        this.timesListened = timesListened;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
