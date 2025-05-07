package musicapplication.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import musicapplication.model.*;

public class AlbumsDao {
	protected ConnectionManager connectionManager;

	private static AlbumsDao instance = null;
	protected AlbumsDao() {
		connectionManager = new ConnectionManager();
	}
	public static AlbumsDao getInstance() {
		if (instance == null) {
			instance = new AlbumsDao();
		}
		return instance;
	}

	public Albums create(Albums album) throws SQLException {
		String insertAlbum = "INSERT INTO Albums(AlbumName) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAlbum, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, album.getAlbumName());
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int albumId = -1;
			if (resultKey.next()) {
				albumId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			album.setAlbumId(albumId);
			return album;
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

	public Albums getAlbumById(int albumId) throws SQLException {
		String selectAlbum = "SELECT AlbumId, AlbumName FROM Albums WHERE AlbumId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAlbum);
			selectStmt.setInt(1, albumId);
			results = selectStmt.executeQuery();

			if (results.next()) {
				int resultAlbumId = results.getInt("AlbumId");
				String albumName = results.getString("AlbumName");
				Albums album = new Albums(resultAlbumId, albumName);
				return album;
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

	public List<Albums> getAlbumsByName(String albumName) throws SQLException {
		List<Albums> albums = new ArrayList<>();
		String selectAlbums = "SELECT AlbumId, AlbumName FROM Albums WHERE AlbumName LIKE ? LIMIT 20;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAlbums);
			selectStmt.setString(1, "%" + albumName + "%");
			results = selectStmt.executeQuery();

			while (results.next()) {
				int albumId = results.getInt("AlbumId");
				String resultAlbumName = results.getString("AlbumName");
				Albums album = new Albums(albumId, resultAlbumName);
				albums.add(album);
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
		return albums;
	}

	public Albums delete(Albums album) throws SQLException {
		String deleteAlbum = "DELETE FROM Albums WHERE AlbumId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAlbum);
			deleteStmt.setInt(1, album.getAlbumId());
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
	
	public List<Albums> getAlbumsPaginated(String titleFilter, int page, int pageSize) throws SQLException {
	    List<Albums> albums = new ArrayList<>();
	    String selectAlbums = 
	        "SELECT AlbumId, AlbumName " +
	        "FROM Albums " +
	        "WHERE AlbumName LIKE ? " +
	        "ORDER BY AlbumName " +
	        "LIMIT ? OFFSET ?;";
	    
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        selectStmt = connection.prepareStatement(selectAlbums);
	        selectStmt.setString(1, "%" + titleFilter + "%");
	        selectStmt.setInt(2, pageSize);
	        selectStmt.setInt(3, (page - 1) * pageSize);
	        results = selectStmt.executeQuery();
	        while (results.next()) {
	            int albumId = results.getInt("AlbumId");
	            String albumName = results.getString("AlbumName");
	            Albums album = new Albums(albumId, albumName);
	            albums.add(album);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (connection != null) connection.close();
	        if (selectStmt != null) selectStmt.close();
	        if (results != null) results.close();
	    }
	    return albums;
	}

	public int getTotalAlbumsCount(String titleFilter) throws SQLException {
	    String countQuery = 
	        "SELECT COUNT(*) FROM Albums " +
	        "WHERE AlbumName LIKE ?;";
	    
	    Connection connection = null;
	    PreparedStatement countStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        countStmt = connection.prepareStatement(countQuery);
	        countStmt.setString(1, "%" + titleFilter + "%");
	        results = countStmt.executeQuery();
	        if (results.next()) {
	            return results.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (connection != null) connection.close();
	        if (countStmt != null) countStmt.close();
	        if (results != null) results.close();
	    }
	    return 0;
	}
}