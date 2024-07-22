package it.unibo.cloudnine.core;

public final class CloudnineManager extends DatabaseManager {

    private static DatabaseManager manager;

    public static DatabaseManager getDatabaseManager() {
        if (manager == null) {
            return manager = new DatabaseManager();
        }
        return manager;
    }
}
