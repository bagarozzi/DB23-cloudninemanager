package it.unibo.cloudnine.dao;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;

public class UserManagementDAO {

    private final DatabaseManager manager;

    public enum USER_TYPES {
        ADMIN,
        COOK,
        WAITER
    }

    public UserManagementDAO() {
        manager = CloudnineManager.getDatabaseManager();
    }
    
    public static boolean tryLogin(final String user, final String password) {
        return true;
    }

    public static USER_TYPES getUserType(final String user) {
        return USER_TYPES.ADMIN;
    }

    public static void addAccount() {

    }
    
    public static void removeAccount() {

    }
}
