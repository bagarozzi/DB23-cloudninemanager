package it.unibo.cloudnine.dao;

import java.util.List;
import java.util.Map;

import java.sql.Date;
import java.sql.Time;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;

public class OrderDAO {
    
    private static final String GET_ORDERS = "SELECT * FROM ordine";

    private static final String GET_PLATES = "SELECT Nome_vivanda, R.Cod_vivanda, N_vivande\r\n" + //
        "FROM richiede as R, vivanda AS V\r\n" + //
        "WHERE V.Cod_vivanda = R.Cod_vivanda AND R.Cod_Comanda = ? AND R.N_Ordine = ?";

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    public static List<Map<String, Object>> getOrders() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_ORDERS);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<Map<String, Object>> getPlates(int codComanda, int nOrdine) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_PLATES, nOrdine, codComanda);
            return result;
        } catch (Exception e) {
        }
        return null;
    }
}
