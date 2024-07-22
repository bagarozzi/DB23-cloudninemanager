package it.unibo.cloudnine.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseManager {

    private final Logger logger = Logger.getLogger("DatabaseManager");

    private String url;
    private String username;
    private String pwd;

    private Optional<Connection> currentConnection = Optional.empty();

    public DatabaseManager() {
        this.url = "192.168.1.54";
        this.username = "root";
        this.pwd = "";
    }

    public void openConnection() throws SQLException {
        if (!isConnectionOpen()) {
            currentConnection = Optional.of(DriverManager.getConnection(url, username, pwd));
        }
    }

    public void closeConnection() {
        try {
            if (currentConnection.isPresent()) {
                currentConnection.get().close();
            }
        } catch (final SQLException e) {
            logger.log(Level.SEVERE, "Couldn't close the connection to the database", e);
        }
    }
 
    public boolean isConnectionOpen() throws SQLException {
        if(currentConnection.isPresent() && !currentConnection.get().isClosed()) {
            return true;
        }
        return false; 
    }

    public void setQuery(final String query, final Object... params) throws SQLException {
        if (!isConnectionOpen()) {
            throw new SQLException("The connection to the database is not open!");
        }
        try (final PreparedStatement ps = prepareStatement(query, params)) {
            ps.executeUpdate();
        }
    }

    public List<Map<String, Object>> getQuery(final String query, final Object... params) throws SQLException {
        if (!isConnectionOpen()) {
            throw new SQLException("The connection to the database is not open!");
        }
        try (final PreparedStatement ps = prepareStatement(query, params)){
            final List<Map<String, Object>> resultRows = new ArrayList<>();
            final ResultSet result = ps.executeQuery();
            final ResultSetMetaData metadata = result.getMetaData();
            while(result.next()) {
                final Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    row.put(metadata.getColumnName(i), result.getObject(i));
                }
                resultRows.add(row);
            }   
            return resultRows;
        }
    }

    private PreparedStatement prepareStatement(final String query, final Object... params) throws SQLException {
        final PreparedStatement preparedStatement = this.currentConnection.get().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }
}
