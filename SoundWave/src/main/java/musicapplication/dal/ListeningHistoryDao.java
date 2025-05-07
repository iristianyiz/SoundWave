package musicapplication.dal;

import musicapplication.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListeningHistoryDao {
    protected ConnectionManager connectionManager;
    private static ListeningHistoryDao instance = null;

    protected ListeningHistoryDao() {
        connectionManager = new ConnectionManager();
    }

    public static ListeningHistoryDao getInstance() {
        if (instance == null) {
            instance = new ListeningHistoryDao();
        }
        return instance;
    }

    // SQL statements
    private static final String INSERT_LISTENING_HISTORY = 
        "INSERT INTO ListeningHistory(TimesListened, Duration, TrackId, UserName) VALUES(?,?,?,?);";
    private static final String SELECT_LISTENING_HISTORY = 
        "SELECT HistoryId, Created, TimesListened, Duration, TrackId, UserName FROM ListeningHistory WHERE HistoryId=?;";
    private static final String SELECT_LISTENING_HISTORY_BY_USER = 
    		"SELECT lh.HistoryId, lh.TrackId, t.TrackName, lh.UserName, lh.TimesListened, lh.Duration " +
    	            "FROM ListeningHistory lh " +
    	            "JOIN Tracks t ON lh.TrackId = t.TrackId " +
    	            "WHERE lh.UserName = ? " +
    	            "ORDER BY lh.TimesListened DESC;";
    private static final String DELETE_LISTENING_HISTORY = 
        "DELETE FROM ListeningHistory WHERE HistoryId=?;";

    public ListeningHistory create(ListeningHistory listeningHistory) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_LISTENING_HISTORY, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, listeningHistory.getTimesListened());
            insertStmt.setInt(2, listeningHistory.getDuration());
            insertStmt.setString(3, listeningHistory.getTrackId());
            insertStmt.setString(4, listeningHistory.getUserName());
            insertStmt.executeUpdate();
            
            resultKey = insertStmt.getGeneratedKeys();
            int historyId = -1;
            if (resultKey.next()) {
                historyId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            listeningHistory.setHistoryId(historyId);
            
            // Fetch the Created timestamp from the database
            ListeningHistory created = getListeningHistoryById(historyId);
            listeningHistory.setCreated(created.getCreated());
            
            return listeningHistory;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }

    public ListeningHistory getListeningHistoryById(int historyId) throws SQLException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_LISTENING_HISTORY);
            selectStmt.setInt(1, historyId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int resultId = results.getInt("HistoryId");
                Timestamp created = results.getTimestamp("Created");
                int timesListened = results.getInt("TimesListened");
                int duration = results.getInt("Duration");
                String trackId = results.getString("TrackId");
                String userName = results.getString("UserName");
                return new ListeningHistory(resultId, created, timesListened, duration, trackId, userName);
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

    public List<ListeningHistory> getListeningHistoryForUser(String userName) throws SQLException {
        List<ListeningHistory> listeningHistoryList = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_LISTENING_HISTORY_BY_USER);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int historyId = results.getInt("HistoryId");
                String trackId = results.getString("TrackId");
                String trackName = results.getString("TrackName");
                int timesListened = results.getInt("TimesListened");
                int duration = results.getInt("Duration");
                
                ListeningHistory history = new ListeningHistory(historyId, null, timesListened, duration, trackId, userName);
                history.setTrackName(trackName);
                listeningHistoryList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return listeningHistoryList;
    }

    public void delete(ListeningHistory listeningHistory) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_LISTENING_HISTORY);
            deleteStmt.setInt(1, listeningHistory.getHistoryId());
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
