package musicapplication.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import musicapplication.model.*;

public class GenresDao {
	protected ConnectionManager connectionManager;

	private static GenresDao instance = null;
	protected GenresDao() {
		connectionManager = new ConnectionManager();
	}
	public static GenresDao getInstance() {
		if (instance == null) {
			instance = new GenresDao();
		}
		return instance;
	}

	public Genres create(Genres genre) throws SQLException {
		String insertGenre = "INSERT INTO Genres(GenreName) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertGenre, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, genre.getGenreName());
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int genreId = -1;
			if (resultKey.next()) {
				genreId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			genre.setGenreId(genreId);
			return genre;
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

	public Genres getGenreById(int genreId) throws SQLException {
		String selectGenre = "SELECT GenreId, GenreName FROM Genres WHERE GenreId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGenre);
			selectStmt.setInt(1, genreId);
			results = selectStmt.executeQuery();

			if (results.next()) {
				int resultGenreId = results.getInt("GenreId");
				String genreName = results.getString("GenreName");
				Genres genre = new Genres(resultGenreId, genreName);
				return genre;
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

	public List<Genres> getGenresByName(String genreName) throws SQLException {
		List<Genres> genres = new ArrayList<>();
		String selectGenres = "SELECT GenreId, GenreName FROM Genres WHERE GenreName LIKE ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGenres);
			selectStmt.setString(1, "%" + genreName + "%");
			results = selectStmt.executeQuery();

			while (results.next()) {
				int genreId = results.getInt("GenreId");
				String resultGenreName = results.getString("GenreName");
				Genres genre = new Genres(genreId, resultGenreName);
				genres.add(genre);
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
		return genres;
	}

	public Genres delete(Genres genre) throws SQLException {
		String deleteGenre = "DELETE FROM Genres WHERE GenreId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteGenre);
			deleteStmt.setInt(1, genre.getGenreId());
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