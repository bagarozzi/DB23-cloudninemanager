package it.unibo.cloudnine.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;

public class UserManagementDAO {

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    public enum USER_TYPES {
        ADMIN,
        COOK,
        WAITER
    }

    private static final String ACCOUNTS = "";
    
    private static final String LOGIN = "SELECT Password FROM Account WHERE username = ?";
    
    public static boolean tryLogin(final String user, final String password) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(LOGIN, password);
            if (result.size() == 1 && result.get(0).get("Password").equals(password)) {
                return true;
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public static USER_TYPES getUserType(final String user) {
        return USER_TYPES.ADMIN;
    }

    public static void addAccount() {

    }
    
    public static void removeAccount() {

    }
}
