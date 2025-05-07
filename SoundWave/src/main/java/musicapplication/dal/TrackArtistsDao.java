package musicapplication.dal;  // Adjust the package as needed

import musicapplication.model.TrackArtists;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class to interact with the TrackArtists table in
 * the database.
 */
public class TrackArtistsDao {
    protected ConnectionManager connectionManager;

    private static TrackArtistsDao instance = null;

    // Private constructor to prevent instantiation
    protected TrackArtistsDao() {
        connectionManager = new ConnectionManager();  // Initialize the connection manager
    }

    // Singleton method to get the instance
    public static TrackArtistsDao getInstance() {
        if (instance == null) {
            instance = new TrackArtistsDao();
        }
        return instance;
    }

    // SQL statements
    private static final String INSERT_TRACK_ARTIST = "INSERT INTO TrackArtists(TrackId, ArtistId) VALUES(?,?);";
    private static final String SELECT_TRACK_ARTIST = "SELECT TrackId, ArtistId FROM TrackArtists WHERE TrackId=? AND ArtistId=?;";
    private static final String SELECT_TRACK_ARTISTS_BY_TRACK = "SELECT TrackId, ArtistId FROM TrackArtists WHERE TrackId=?;";
    private static final String SELECT_TRACK_ARTISTS_BY_ARTIST = "SELECT TrackId, ArtistId FROM TrackArtists WHERE ArtistId=?;";
    private static final String DELETE_TRACK_ARTIST = "DELETE FROM TrackArtists WHERE TrackId=? AND ArtistId=?;";

    // Create a new TrackArtist relationship
    public TrackArtists create(TrackArtists trackArtist) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_TRACK_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, trackArtist.getTrackId());
            insertStmt.setInt(2, trackArtist.getArtistId());
            insertStmt.executeUpdate();


            return trackArtist;  // Just return the TrackArtist since no relationId is needed
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }

    // Retrieve a TrackArtist by trackId and artistId
    public TrackArtists getTrackArtistByIds(String trackId, int artistId) throws SQLException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_TRACK_ARTIST);
            selectStmt.setString(1, trackId);
            selectStmt.setInt(2, artistId);
            results = selectStmt.executeQuery();

            if (results.next()) {
                // We don't actually need to map the relationId, just trackId and artistId
                return new TrackArtists(trackId, artistId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return null;  // Return null if no match is found
    }

    // Retrieve all artists associated with a given track
    public List<TrackArtists> getTrackArtistsByTrackId(String trackId) throws SQLException {
        List<TrackArtists> trackArtists = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_TRACK_ARTISTS_BY_TRACK);
            selectStmt.setString(1, trackId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int artistId = results.getInt("ArtistId");
                TrackArtists trackArtist = new TrackArtists(trackId, artistId);
                trackArtists.add(trackArtist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return trackArtists;  // Return the list of track-artist relationships
    }

    // Retrieve all tracks associated with a given artist
    public List<TrackArtists> getTrackArtistsByArtistId(int artistId) throws SQLException {
        List<TrackArtists> trackArtists = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_TRACK_ARTISTS_BY_ARTIST);
            selectStmt.setInt(1, artistId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                String trackId = results.getString("TrackId");
                TrackArtists trackArtist = new TrackArtists(trackId, artistId);
                trackArtists.add(trackArtist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return trackArtists;  // Return the list of track-artist relationships
    }

    // Delete a TrackArtist relationship
    public void delete(TrackArtists trackArtist) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_TRACK_ARTIST);
            deleteStmt.setString(1, trackArtist.getTrackId());
            deleteStmt.setInt(2, trackArtist.getArtistId());
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No rows affected, delete operation failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (deleteStmt != null) deleteStmt.close();
        }
    }
}
