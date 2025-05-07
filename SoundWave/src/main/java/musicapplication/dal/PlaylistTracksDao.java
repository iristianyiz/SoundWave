package musicapplication.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import musicapplication.model.PlaylistTracks;

/**
 * Data Access Object (DAO) class to interact with the Users table in the
 * database.
 * 
 * @author Jia Ying Li
 */
public class PlaylistTracksDao {
	protected ConnectionManager connectionManager;

    private static PlaylistTracksDao instance = null;

    protected PlaylistTracksDao() {
        connectionManager = new ConnectionManager();
    }

    public static PlaylistTracksDao getInstance() {
        if (instance == null) {
            instance = new PlaylistTracksDao();
        }
        return instance;
    }

    /**
     * Create a new PlaylistTrack in the database.
     */
    public PlaylistTracks create(PlaylistTracks playlistTrack) throws SQLException {
        String insertPlaylistTrack = "INSERT INTO PlaylistTracks(PlayListId, Track_Id, Position) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPlaylistTrack, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, playlistTrack.getPlayListId());
            insertStmt.setString(2, playlistTrack.getTrack_Id());
            insertStmt.setInt(3, playlistTrack.getPosition());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int recordId = -1;
            if (resultKey.next()) {
                recordId = resultKey.getInt(1);
            }
            playlistTrack.setRecordId(recordId);
            return playlistTrack;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultKey != null) resultKey.close();
            if (insertStmt != null) insertStmt.close();
            if (connection != null) connection.close();
        }
    }

    public PlaylistTracks getPlaylistTrackById(int recordId) throws SQLException {
        String selectPlaylistTrack = "SELECT RecordId, PlayListId, Track_Id, Position FROM PlaylistTracks WHERE RecordId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPlaylistTrack);
            selectStmt.setInt(1, recordId);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int playListId = results.getInt("PlayListId");
                String trackId = results.getString("Track_Id");
                int position = results.getInt("Position");

                return new PlaylistTracks(recordId, playListId, trackId, position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (results != null) results.close();
            if (selectStmt != null) selectStmt.close();
            if (connection != null) connection.close();
        }
        return null;
    }

    // New getTracksByPlaylistId method
    public List<PlaylistTracks> getTracksByPlaylistId(int playlistId) throws SQLException {
        List<PlaylistTracks> playlistTracksList = new ArrayList<>();
        String selectTracks = "SELECT RecordId, PlayListId, Track_Id, Position FROM PlaylistTracks WHERE PlayListId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTracks);
            selectStmt.setInt(1, playlistId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int recordId = results.getInt("RecordId");
                String trackId = results.getString("Track_Id");
                int position = results.getInt("Position");

                PlaylistTracks playlistTrack = new PlaylistTracks(recordId, playlistId, trackId, position);
                playlistTracksList.add(playlistTrack);
            }
            return playlistTracksList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (results != null) results.close();
            if (selectStmt != null) selectStmt.close();
            if (connection != null) connection.close();
        }
    }

    
    public PlaylistTracks updatePosition(PlaylistTracks playlistTrack, int newPosition) throws SQLException {
        String updatePosition = "UPDATE PlaylistTracks SET Position=? WHERE RecordId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatePosition);
            updateStmt.setInt(1, newPosition);
            updateStmt.setInt(2, playlistTrack.getRecordId());
            updateStmt.executeUpdate();

            // Update the position field in the playlistTrack object
            playlistTrack.setPosition(newPosition);
            return playlistTrack;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (updateStmt != null) updateStmt.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Delete a PlaylistTrack from the database.
     */
    public PlaylistTracks delete(PlaylistTracks playlistTrack) throws SQLException {
        String deletePlaylistTrack = "DELETE FROM PlaylistTracks WHERE RecordId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePlaylistTrack);
            deleteStmt.setInt(1, playlistTrack.getRecordId());
            deleteStmt.executeUpdate();

            // Return null to indicate the playlist track was deleted
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (deleteStmt != null) deleteStmt.close();
            if (connection != null) connection.close();
        }
    }
}