package it.unibo.cloudnine.dao;

public class UserManagementDAO {

    public enum USER_TYPES {
        ADMIN,
        COOK,
        WAITER
    }
    
    public static boolean tryLogin(final String user, final String password) {
        return true;
    }

    public static USER_TYPES getUserType(final String user) {
        return USER_TYPES.ADMIN;
    }
}
