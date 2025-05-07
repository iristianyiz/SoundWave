package musicapplication.model;

import java.sql.Timestamp;

/**
 * Represents a comment in the music application.
 * 
 * @author Krushna Sanjay Sharma
 */
public class Comments {
	private int commentId;
	private Timestamp created;
	private String content;
	private String userName;
	private String trackId;

	/**
	 * Constructs a new Comments object.
	 *
	 * @param commentId The unique identifier for the comment.
	 * @param created   The timestamp when the comment was created.
	 * @param content   The text content of the comment.
	 * @param userName  The username of the user who made the comment.
	 * @param trackId   The identifier of the track the comment is associated with.
	 */
	public Comments(int commentId, Timestamp created, String content, String userName, String trackId) {
		this.commentId = commentId;
		this.created = created;
		this.content = content;
		this.userName = userName;
		this.trackId = trackId;
	}

	/**
	 * Gets the comment ID.
	 *
	 * @return The comment ID.
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * Sets the comment ID.
	 *
	 * @param commentId The comment ID to set.
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * Gets the creation timestamp of the comment.
	 *
	 * @return The creation timestamp.
	 */
	public Timestamp getCreated() {
		return created;
	}

	/**
	 * Sets the creation timestamp of the comment.
	 *
	 * @param created The creation timestamp to set.
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	/**
	 * Gets the content of the comment.
	 *
	 * @return The comment content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content of the comment.
	 *
	 * @param content The comment content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the username of the user who made the comment.
	 *
	 * @return The username.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the username of the user who made the comment.
	 *
	 * @param userName The username to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the track ID associated with the comment.
	 *
	 * @return The track ID.
	 */
	public String getTrackId() {
		return trackId;
	}

	/**
	 * Sets the track ID associated with the comment.
	 *
	 * @param trackId The track ID to set.
	 */
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
}
