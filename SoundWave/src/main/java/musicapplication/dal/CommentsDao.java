package musicapplication.dal;

import musicapplication.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class to interact with the Comments table in the
 * database.
 * 
 * @author Krushna Sanjay Sharma
 */
public class CommentsDao {
	private static volatile CommentsDao instance;
	private ConnectionManager connectionManager;

	// SQL statement constants
	private static final String SELECT_COMMENTS_BY_USER = "SELECT CommentId,Created,Content,UserName,TrackId FROM Comments WHERE UserName=?;";
	private static final String UPDATE_COMMENT = "UPDATE Comments SET Content=? WHERE CommentId=?;";
	private static final String DELETE_COMMENT = "DELETE FROM Comments WHERE CommentId=?;";
	private static final String INSERT_COMMENT = "INSERT INTO Comments(Content,UserName,TrackId) VALUES(?,?,?);";
    private static final String SELECT_COMMENT = "SELECT CommentId,Created,Content,UserName,TrackId FROM Comments WHERE CommentId=?;";
    private static final String SELECT_COMMENTS_BY_TRACK = "SELECT CommentId,Created,Content,UserName,TrackId FROM Comments WHERE TrackId=?;";
    private static final String SELECT_RANDOM_USER = "SELECT UserName FROM Users ORDER BY RAND() LIMIT 1;";

	// Column name constants
	private static final String COLUMN_COMMENT_ID = "CommentId";
	private static final String COLUMN_CREATED = "Created";
	private static final String COLUMN_CONTENT = "Content";
	private static final String COLUMN_USER_NAME = "UserName";
	private static final String COLUMN_TRACK_ID = "TrackId";

	/**
	 * Constructor for CommentsDao.
	 */
	private CommentsDao() {
		connectionManager = new ConnectionManager();
	}
	
	/**
	 * Get single instance for CommentsDao
	 * @return the singleton instance
	 */
	public static CommentsDao getInstance() {
        if (instance == null) {
            synchronized (CommentsDao.class) {
                if (instance == null) {
                    instance = new CommentsDao();
                }
            }
        }
        return instance;
    }

	/**
     * Create a new comment in the database.
     */
    public Comments create(Comments comment) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, comment.getContent());
            insertStmt.setString(2, comment.getUserName());
            insertStmt.setString(3, comment.getTrackId());
            insertStmt.executeUpdate();
            resultKey = insertStmt.getGeneratedKeys();
            int commentId = -1;
            if (resultKey.next()) {
                commentId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            comment.setCommentId(commentId);
            return comment;
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
     * Retrieve all comments for a specific track.
     */
    public List<Comments> getCommentsByTrackId(String trackId) throws SQLException {
        List<Comments> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_COMMENTS_BY_TRACK);
            selectStmt.setString(1, trackId);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int commentId = results.getInt("CommentId");
                Timestamp created = results.getTimestamp("Created");
                String content = results.getString("Content");
                String userName = results.getString("UserName");
                Comments comment = new Comments(commentId, created, content, userName, trackId);
                comments.add(comment);
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
        return comments;
    }

    /**
     * Retrieve a random username from the Users table.
     */
    public String getRandomUserName() throws SQLException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_RANDOM_USER);
            results = selectStmt.executeQuery();
            if (results.next()) {
                return results.getString("UserName");
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
     * Create a new comment with a random username for a specific track.
     */
    public Comments createCommentWithRandomUser(String content, String trackId) throws SQLException {
        String randomUserName = getRandomUserName();
        if (randomUserName == null) {
            throw new SQLException("Unable to get a random user");
        }
        Comments comment = new Comments(0, new Timestamp(System.currentTimeMillis()), content, randomUserName, trackId);
        return create(comment);
    }

	/**
	 * Retrieve a comment by its ID.
	 * 
	 * @param commentId The ID of the comment to retrieve.
	 * @return The retrieved comment, or null if not found.
	 * @throws SQLException if a database access error occurs.
	 */
	public Comments getCommentById(int commentId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(SELECT_COMMENT);
			selectStmt.setInt(1, commentId);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int resultCommentId = results.getInt(COLUMN_COMMENT_ID);
				Timestamp created = results.getTimestamp(COLUMN_CREATED);
				String content = results.getString(COLUMN_CONTENT);
				String userName = results.getString(COLUMN_USER_NAME);
				String trackId = results.getString(COLUMN_TRACK_ID);
				return new Comments(resultCommentId, created, content, userName, trackId);
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
	 * Retrieve all comments by a specific user.
	 * 
	 * @param userName The username of the user whose comments to retrieve.
	 * @return A list of comments by the specified user.
	 * @throws SQLException if a database access error occurs.
	 */
	public List<Comments> getCommentsByUserName(String userName) throws SQLException {
		List<Comments> comments = new ArrayList<>();
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(SELECT_COMMENTS_BY_USER);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int commentId = results.getInt(COLUMN_COMMENT_ID);
				Timestamp created = results.getTimestamp(COLUMN_CREATED);
				String content = results.getString(COLUMN_CONTENT);
				String trackId = results.getString(COLUMN_TRACK_ID);
				Comments comment = new Comments(commentId, created, content, userName, trackId);
				comments.add(comment);
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
		return comments;
	}


	/**
	 * Update the content of an existing comment.
	 * 
	 * @param comment The comment with updated content.
	 * @return The updated comment.
	 * @throws SQLException if a database access error occurs.
	 */
	public Comments updateComment(Comments comment) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(UPDATE_COMMENT);
			updateStmt.setString(1, comment.getContent());
			updateStmt.setInt(2, comment.getCommentId());
			updateStmt.executeUpdate();
			return comment;
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
	 * Delete a comment from the database.
	 * 
	 * @param comment The comment to be deleted.
	 * @return null if the deletion was successful.
	 * @throws SQLException if a database access error occurs.
	 */
	public Comments delete(Comments comment) throws SQLException {
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(DELETE_COMMENT);
			deleteStmt.setInt(1, comment.getCommentId());
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
