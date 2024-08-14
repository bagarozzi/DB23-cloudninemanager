package it.unibo.cloudnine.dao;

import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.sql.Time;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;

public class BookingsDAO {
    private static final String BOOKINGS = "SELECT * FROM prenoatazione";
    private static final String CREATE_BOOKING = "INSERT INTO prenoatazione (Data, Ora, Nominativo, Coperti, Telefono, CodFiscale) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CANCEL_BOOKING = "DELETE FROM prenoatazione WHERE Data=? AND Ora=? AND Telefono=?" + //
                "";

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    public static List<Map<String, Object>> getTableBookings() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(BOOKINGS);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
        return null;
    }

    public static void cancelBooking(Date data, Time ora, String telefono) {
        try {
            manager.openConnection();
            manager.setQuery(CANCEL_BOOKING, data, ora, telefono);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertBooking(Date data, Time ora, String nominativo, Integer coperti, String telefono, String codFisc) {
        try {
            manager.openConnection();
            manager.setQuery(CREATE_BOOKING, data, ora, nominativo, coperti, telefono, codFisc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
