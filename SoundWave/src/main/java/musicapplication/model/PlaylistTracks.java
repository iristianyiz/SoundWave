package musicapplication.model;


/**
 * Represents a user in the music application.
 * 
 * @author Jia Ying Li
 */
public class PlaylistTracks {
	protected int RecordId;
	protected int PlayListId;
	protected String Track_Id;
	protected int Position;
	public PlaylistTracks(int recordId, int playListId, String track_Id, int position) {
		RecordId = recordId;
		PlayListId = playListId;
		Track_Id = track_Id;
		Position = position;
	}
	public int getRecordId() {
		return RecordId;
	}
	public void setRecordId(int recordId) {
		RecordId = recordId;
	}
	public int getPlayListId() {
		return PlayListId;
	}
	public void setPlayListId(int playListId) {
		PlayListId = playListId;
	}
	public String getTrack_Id() {
		return Track_Id;
	}
	public void setTrack_Id(String track_Id) {
		Track_Id = track_Id;
	}
	public int getPosition() {
		return Position;
	}
	public void setPosition(int position) {
		Position = position;
	}
	
	
}