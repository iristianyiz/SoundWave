package musicapplication.model;

import java.sql.Timestamp;

/**
 * Represents a like or dislike action in the music application.
 * 
 * @author Krushna Sanjay Sharma
 */
public class LikeAndDislike {
	private int reviewId;
	private Timestamp created;
	private boolean likeOrDislike;
	private String trackId;
	private String userName;

	/**
	 * Constructs a new LikeAndDislike object.
	 *
	 * @param reviewId      The unique identifier for the review.
	 * @param created       The timestamp when the like or dislike was created.
	 * @param likeOrDislike True if it's a like, false if it's a dislike.
	 * @param trackId       The identifier of the track associated with this like or
	 *                      dislike.
	 * @param userName      The username of the user who made the like or dislike.
	 */
	public LikeAndDislike(int reviewId, Timestamp created, boolean likeOrDislike, String trackId, String userName) {
		this.reviewId = reviewId;
		this.created = created;
		this.likeOrDislike = likeOrDislike;
		this.trackId = trackId;
		this.userName = userName;
	}

	/**
	 * Gets the review ID.
	 *
	 * @return The review ID.
	 */
	public int getReviewId() {
		return reviewId;
	}

	/**
	 * Sets the review ID.
	 *
	 * @param reviewId The review ID to set.
	 */
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	/**
	 * Gets the creation timestamp of the like or dislike.
	 *
	 * @return The creation timestamp.
	 */
	public Timestamp getCreated() {
		return created;
	}

	/**
	 * Sets the creation timestamp of the like or dislike.
	 *
	 * @param created The creation timestamp to set.
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	/**
	 * Checks if this is a like or dislike.
	 *
	 * @return True if it's a like, false if it's a dislike.
	 */
	public boolean isLikeOrDislike() {
		return likeOrDislike;
	}

	/**
	 * Sets whether this is a like or dislike.
	 *
	 * @param likeOrDislike True for like, false for dislike.
	 */
	public void setLikeOrDislike(boolean likeOrDislike) {
		this.likeOrDislike = likeOrDislike;
	}

	/**
	 * Gets the track ID associated with this like or dislike.
	 *
	 * @return The track ID.
	 */
	public String getTrackId() {
		return trackId;
	}

	/**
	 * Sets the track ID associated with this like or dislike.
	 *
	 * @param trackId The track ID to set.
	 */
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	/**
	 * Gets the username of the user who made the like or dislike.
	 *
	 * @return The username.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the username of the user who made the like or dislike.
	 *
	 * @param userName The username to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
