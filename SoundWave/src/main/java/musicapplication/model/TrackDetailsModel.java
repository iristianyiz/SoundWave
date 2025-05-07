package musicapplication.model;

import java.util.List;

public class TrackDetailsModel {
	private Tracks track;
	private String albumName;
	private String genreName;
	private List<MoodTag> moodTags;
	private int likeCount;
	private int dislikeCount;
	private List<Comments> comments;

	public TrackDetailsModel(Tracks track, String albumName, String genreName, List<MoodTag> moodTags, int likeCount,
			int dislikeCount, List<Comments> comments) {
		this.track = track;
		this.albumName = albumName;
		this.genreName = genreName;
		this.moodTags = moodTags;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
		this.comments = comments;
	}

	// Getters
	public Tracks getTrack() {
		return track;
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getGenreName() {
		return genreName;
	}

	public List<MoodTag> getMoodTags() {
		return moodTags;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public int getDislikeCount() {
		return dislikeCount;
	}

	public List<Comments> getComments() {
		return comments;
	}

	// Setters
	public void setTrack(Tracks track) {
		this.track = track;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public void setMoodTags(List<MoodTag> moodTags) {
		this.moodTags = moodTags;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
}