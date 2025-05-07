package musicapplication.dal;

import musicapplication.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class to interact with the LikeAndDislike table in
 * the database.
 * 
 * @author Krushna Sanjay Sharma
 */
public class LikeAndDislikeDao {
	private static volatile LikeAndDislikeDao instance;
	private ConnectionManager connectionManager;

	// SQL statement constants
	private static final String INSERT_LIKEANDDISLIKE = "INSERT INTO LikeAndDislike(LikeOrDislike,TrackId,UserName) VALUES(?,?,?);";
	private static final String SELECT_LIKEANDDISLIKE = "SELECT ReviewId,Created,LikeOrDislike,TrackId,UserName FROM LikeAndDislike WHERE ReviewId=?;";
	private static final String SELECT_LIKEANDDISLIKE_BY_USER = "SELECT ReviewId,Created,LikeOrDislike,TrackId,UserName FROM LikeAndDislike WHERE UserName=?;";
	private static final String SELECT_LIKEANDDISLIKE_BY_TRACK = "SELECT ReviewId,Created,LikeOrDislike,TrackId,UserName FROM LikeAndDislike WHERE TrackId=?;";
	private static final String UPDATE_LIKEANDDISLIKE = "UPDATE LikeAndDislike SET LikeOrDislike=? WHERE ReviewId=?;";
	private static final String DELETE_LIKEANDDISLIKE = "DELETE FROM LikeAndDislike WHERE ReviewId=?;";

	// Column name constants
	private static final String COLUMN_REVIEW_ID = "ReviewId";
	private static final String COLUMN_CREATED = "Created";
	private static final String COLUMN_LIKE_OR_DISLIKE = "LikeOrDislike";
	private static final String COLUMN_TRACK_ID = "TrackId";
	private static final String COLUMN_USER_NAME = "UserName";

	/**
	 * Constructor for LikeAndDislikeDao.
	 */
	private LikeAndDislikeDao() {
		connectionManager = new ConnectionManager();
	}
	
	
	/**
	 * Get single instance for LikeAndDislikeDao
	 * @return the singleton instance
	 */
	public static LikeAndDislikeDao getInstance() {
        if (instance == null) {
            synchronized (LikeAndDislikeDao.class) {
                if (instance == null) {
                    instance = new LikeAndDislikeDao();
                }
            }
        }
        return instance;
    }

	/**
	 * Create a new like or dislike in the database.
	 * 
	 * @param likeAndDislike The like or dislike to be created.
	 * @return The created like or dislike with the auto-generated ID.
	 * @throws SQLException if a database access error occurs.
	 */
	public LikeAndDislike create(LikeAndDislike likeAndDislike) throws SQLException {
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(INSERT_LIKEANDDISLIKE, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setBoolean(1, likeAndDislike.isLikeOrDislike());
			insertStmt.setString(2, likeAndDislike.getTrackId());
			insertStmt.setString(3, likeAndDislike.getUserName());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if (resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			likeAndDislike.setReviewId(reviewId);
			return likeAndDislike;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
			if (resultKey != null) {
				resultKey.close();
			}
		}
	}

	/**
	 * Retrieve a like or dislike by its ID.
	 * 
	 * @param reviewId The ID of the like or dislike to retrieve.
	 * @return The retrieved like or dislike, or null if not found.
	 * @throws SQLException if a database access error occurs.
	 */
	public LikeAndDislike getLikeAndDislikeById(int reviewId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(SELECT_LIKEANDDISLIKE);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int resultReviewId = results.getInt(COLUMN_REVIEW_ID);
				Timestamp created = results.getTimestamp(COLUMN_CREATED);
				boolean likeOrDislike = results.getBoolean(COLUMN_LIKE_OR_DISLIKE);
				String trackId = results.getString(COLUMN_TRACK_ID);
				String userName = results.getString(COLUMN_USER_NAME);
				return new LikeAndDislike(resultReviewId, created, likeOrDislike, trackId, userName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * Retrieve all likes and dislikes by a specific user.
	 * 
	 * @param userName The username of the user whose likes and dislikes to
	 *                 retrieve.
	 * @return A list of likes and dislikes by the specified user.
	 * @throws SQLException if a database access error occurs.
	 */
	public List<LikeAndDislike> getLikeAndDislikeByUserName(String userName) throws SQLException {
		List<LikeAndDislike> likeAndDislikes = new ArrayList<>();
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(SELECT_LIKEANDDISLIKE_BY_USER);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int reviewId = results.getInt(COLUMN_REVIEW_ID);
				Timestamp created = results.getTimestamp(COLUMN_CREATED);
				boolean likeOrDislike = results.getBoolean(COLUMN_LIKE_OR_DISLIKE);
				String trackId = results.getString(COLUMN_TRACK_ID);
				LikeAndDislike likeAndDislike = new LikeAndDislike(reviewId, created, likeOrDislike, trackId, userName);
				likeAndDislikes.add(likeAndDislike);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return likeAndDislikes;
	}

	/**
	 * Retrieve all likes and dislikes for a specific track.
	 * 
	 * @param trackId The ID of the track whose likes and dislikes to retrieve.
	 * @return A list of likes and dislikes for the specified track.
	 * @throws SQLException if a database access error occurs.
	 */
	public List<LikeAndDislike> getLikeAndDislikeByTrackId(String trackId) throws SQLException {
		List<LikeAndDislike> likeAndDislikes = new ArrayList<>();
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(SELECT_LIKEANDDISLIKE_BY_TRACK);
			selectStmt.setString(1, trackId);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int reviewId = results.getInt(COLUMN_REVIEW_ID);
				Timestamp created = results.getTimestamp(COLUMN_CREATED);
				boolean likeOrDislike = results.getBoolean(COLUMN_LIKE_OR_DISLIKE);
				String userName = results.getString(COLUMN_USER_NAME);
				LikeAndDislike likeAndDislike = new LikeAndDislike(reviewId, created, likeOrDislike, trackId, userName);
				likeAndDislikes.add(likeAndDislike);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return likeAndDislikes;
	}

	/**
	 * Update an existing like or dislike.
	 * 
	 * @param likeAndDislike The like or dislike to be updated.
	 * @return The updated like or dislike.
	 * @throws SQLException if a database access error occurs.
	 */
	public LikeAndDislike updateLikeAndDislike(LikeAndDislike likeAndDislike) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(UPDATE_LIKEANDDISLIKE);
			updateStmt.setBoolean(1, likeAndDislike.isLikeOrDislike());
			updateStmt.setInt(2, likeAndDislike.getReviewId());
			updateStmt.executeUpdate();
			return likeAndDislike;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete a like or dislike from the database.
	 * 
	 * @param likeAndDislike The like or dislike to be deleted.
	 * @return null if the deletion was successful.
	 * @throws SQLException if a database access error occurs.
	 */
	public LikeAndDislike delete(LikeAndDislike likeAndDislike) throws SQLException {
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(DELETE_LIKEANDDISLIKE);
			deleteStmt.setInt(1, likeAndDislike.getReviewId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}