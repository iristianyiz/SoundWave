package musicapplication.dal;

import musicapplication.model.Artists;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the Artists table in your
 * MySQL instance. This is used to store Artists into your MySQL instance and
 * retrieve Artists from the MySQL instance.
 */
public class ArtistsDao {

    // Singleton instance of ArtistsDao
    private static ArtistsDao instance = null;

    // Connection manager to manage database connections
    protected ConnectionManager connectionManager;

   
    // Private constructor for Singleton pattern
    private ArtistsDao() {
        connectionManager = new ConnectionManager();
    }

    // Singleton method to get the instance of ArtistsDao
    public static ArtistsDao getInstance() {
        if (instance == null) {
            instance = new ArtistsDao();
        }
        return instance;
    }
    
    // SQL statements as constants
    private static final String INSERT_ARTIST = "INSERT INTO Artists (ArtistName) VALUES (?);";
    private static final String UPDATE_ARTIST = "UPDATE Artists SET ArtistName=? WHERE ArtistId=?;";
    private static final String DELETE_ARTIST = "DELETE FROM Artists WHERE ArtistId=?;";
    private static final String SELECT_ARTIST_BY_ID = "SELECT ArtistId, ArtistName FROM Artists WHERE ArtistId=?;";
    private static final String SELECT_ALL_ARTISTS = "SELECT ArtistId, ArtistName FROM Artists;";
    private static final String SELECT_ARTISTS_BY_NAME = "SELECT ArtistId, ArtistName FROM Artists WHERE ArtistName LIKE ?;";


    /**
     * Create a new Artist in the database.
     * @param artist The Artist object to be saved.
     * @return The created Artist object with the generated ID.
     * @throws SQLException if an SQL error occurs.
     */
    public Artists create(Artists artist) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS)) {

            insertStmt.setString(1, artist.getArtistName());
            insertStmt.executeUpdate();

            // Get the generated ArtistId
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    artist.setArtistId(generatedKeys.getInt(1));
                }
            }
            return artist;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Update the Artist's name in the database.
     * @param artist The Artist object to be updated.
     * @return The updated Artist object.
     * @throws SQLException if an SQL error occurs.
     */
    public Artists update(Artists artist) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_ARTIST)) {

            updateStmt.setString(1, artist.getArtistName());
            updateStmt.setInt(2, artist.getArtistId());
            int affectedRows = updateStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating artist failed, no rows affected.");
            }
            return artist;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Delete an Artist from the database.
     * @param artist The Artist object to be deleted.
     * @throws SQLException if an SQL error occurs.
     */
    public void delete(Artists artist) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(DELETE_ARTIST)) {

            deleteStmt.setInt(1, artist.getArtistId());
            int affectedRows = deleteStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting artist failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieve an Artist by its ID.
     * @param artistId The ID of the artist.
     * @return The Artist object if found, or null if no artist with the given ID.
     * @throws SQLException if an SQL error occurs.
     */
    public Artists getArtistById(int artistId) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ARTIST_BY_ID)) {

            selectStmt.setInt(1, artistId);
            try (ResultSet results = selectStmt.executeQuery()) {
                if (results.next()) {
                    String artistName = results.getString("ArtistName");
                    return new Artists(artistId, artistName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    /**
     * Retrieve all artists from the database.
     * @return A list of all artists.
     * @throws SQLException if an SQL error occurs.
     */
    public List<Artists> getAllArtists() throws SQLException {
        List<Artists> artists = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_ARTISTS);
             ResultSet results = selectStmt.executeQuery()) {

            while (results.next()) {
                int artistId = results.getInt("ArtistId");
                String artistName = results.getString("ArtistName");
                artists.add(new Artists(artistId, artistName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return artists;
    }

    /**
     * Retrieve artists by their name.
     * @param artistName The name of the artist.
     * @return A list of artists with the given name.
     * @throws SQLException if an SQL error occurs.
     */
    public List<Artists> getArtistsByName(String artistName) throws SQLException {
        List<Artists> artists = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ARTISTS_BY_NAME)) {

            selectStmt.setString(1, "%" + artistName + "%");
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
                    int artistId = results.getInt("ArtistId");
                    String name = results.getString("ArtistName");
                    artists.add(new Artists(artistId, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return artists;
    }
}
