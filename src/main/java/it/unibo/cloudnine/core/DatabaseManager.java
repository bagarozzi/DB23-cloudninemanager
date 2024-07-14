package it.unibo.cloudnine.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DatabaseManager {

    private final Logger logger = Logger.getLogger("DatabaseManager");

    private String url;
    private String username;
    private String pwd;

    private Optional<Connection> currentConnection = Optional.empty();

    public DatabaseManager() {
        this.url = "localhost";
        this.username = "root";
        this.pwd = "";
    }

    public void openConnection() throws SQLException {
        currentConnection = Optional.of(DriverManager.getConnection(url, username, pwd));
    }

    public Connection getDatabase() {
        if (!currentConnection.isPresent()) {
            try {
                openConnection();
            } catch (SQLException e) {
                logger.log(Level.FINE, "Exception in opening connection to database " + url);
            }
        }
        return currentConnection.get();
    }
}
