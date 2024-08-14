package it.unibo.cloudnine.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;
import it.unibo.cloudnine.data.StaffMember;

public class ReceiptsDAO {

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    private static final String GET_EARNINGS = "SELECT SUM(comanda.Conto_finale) FROM comanda WHERE comanda.Data = CURRENT_DATE";

    public static float getTodaysEarnings() {
        final List<Map<String, Object>> result = new ArrayList<>();
        try {
            manager.openConnection();
            result.addAll(manager.getQuery(GET_EARNINGS));
            System.out.println("diomerda" + result);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return (float)result.get(0).get("SUM(comanda.Conto_finale)");
    }
    
}
