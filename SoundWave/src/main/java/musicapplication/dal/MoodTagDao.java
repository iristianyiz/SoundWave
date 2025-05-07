package musicapplication.dal;

import musicapplication.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class to interact with the MoodTag table in
 * the database.
 */
public class MoodTagDao {
    protected ConnectionManager connectionManager;

    private static MoodTagDao instance = null;

    // Private constructor to prevent instantiation
    protected MoodTagDao() {
        connectionManager = new ConnectionManager();
    }

    // Singleton method to get the instance
    public static MoodTagDao getInstance() {
        if (instance == null) {
            instance = new MoodTagDao();
        }
        return instance;
    }

    // SQL statements
    private static final String INSERT_MOOD_TAG = "INSERT INTO MoodTag(Mood, TrackId, UserName) VALUES(?,?,?);";
    private static final String SELECT_MOOD_TAG = "SELECT MoodId, Mood, TrackId, UserName FROM MoodTag WHERE MoodId=?;";
    private static final String SELECT_MOOD_TAG_BY_TRACK = "SELECT MoodId, Mood, TrackId, UserName FROM MoodTag WHERE TrackId=?;";
    private static final String DELETE_MOOD_TAG = "DELETE FROM MoodTag WHERE MoodId=?;";

    // Create a new MoodTag record
    public MoodTag create(MoodTag moodTag) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_MOOD_TAG, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, moodTag.getMood().name());  // Store enum as string
            insertStmt.setString(2, moodTag.getTrackId());
            insertStmt.setString(3, moodTag.getUserName());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int moodId = -1;
            if (resultKey.next()) {
                moodId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            moodTag.setMoodId(moodId);  // Set the MoodId after insertion
            return moodTag;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }

    // Retrieve a MoodTag by its ID
    public MoodTag getMoodTagById(int moodId) throws SQLException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_MOOD_TAG);
            selectStmt.setInt(1, moodId);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int resultId = results.getInt("MoodId");  // Corrected to MoodId
                String moodName = results.getString("Mood");
                MoodTag.Mood mood = MoodTag.Mood.valueOf(moodName); // Convert string back to enum
                String trackId = results.getString("TrackId");
                String userName = results.getString("UserName");
                return new MoodTag(resultId, mood, trackId, userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return null;
    }

    // Retrieve MoodTags by track ID
    public List<MoodTag> getMoodTagsByTrackId(String trackId) throws SQLException {
        List<MoodTag> moodTags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_MOOD_TAG_BY_TRACK);
            selectStmt.setString(1, trackId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int resultId = results.getInt("MoodId");  // Corrected to MoodId
                String moodName = results.getString("Mood");
                MoodTag.Mood mood = MoodTag.Mood.valueOf(moodName); // Convert string back to enum
                String userName = results.getString("UserName");
                moodTags.add(new MoodTag(resultId, mood, trackId, userName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return moodTags;
    }

    // Delete a MoodTag record
    public void delete(MoodTag moodTag) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_MOOD_TAG);
            deleteStmt.setInt(1, moodTag.getMoodId());  // Corrected to MoodId
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (deleteStmt != null) deleteStmt.close();
        }
    }
}


