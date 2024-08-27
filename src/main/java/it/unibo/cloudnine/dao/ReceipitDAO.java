package it.unibo.cloudnine.dao;

import java.util.List;
import java.util.Map;

import java.sql.Date;
import java.sql.Time;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;

public class ReceipitDAO {
    private static final String GET_RECEIPIT = "SELECT * FROM `comanda`";

    private static final String GET_ORDER = "SELECT * FROM `ordine` WHERE Cod_comanda = ?";

    private static final String DELETE_RECEIPIT = "DELETE FROM comanda WHERE Cod_comanda = ?;";

    private static final String INSERT_RECEIPIT = "INSERT INTO `comanda` (`Conto_finale`, `Cod_Comanda`, `Modalita_d_odine`, `Coperti`, `Data`, `Ora`, `Nome_Menu`, `Num_Tavolo`, `CodFiscale`) VALUES (NULL, NULL, ?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_TABLE = "SELECT Num_tavolo from tavolo;";

    private static final String GET_CHECK = "UPDATE Comanda AS C\r\n" + //
                "SET C.Conto_finale = IF(Modalita_d_odine = 'CARTA',\r\n" + //
                "    (\r\n" + //
                "     SELECT SUM(V.Prezzo * R.N_vivande)\r\n" + //
                "    FROM Ordine AS O\r\n" + //
                "    JOIN Richiede AS R ON O.Cod_Comanda = R.Cod_Comanda AND O.N_Ordine = R.N_Ordine\r\n" + //
                "    JOIN Vivanda AS V ON R.Cod_vivanda = V.Cod_vivanda\r\n" + //
                "    WHERE O.Cod_Comanda = C.Cod_Comanda),\r\n" + //
                "    (\r\n" + //
                "    SELECT SUM(V.Prezzo * R.N_vivande) + (SELECT Costo_menu_AYCE from menu WHERE Nome_Menu = C.Nome_Menu)\r\n" + //
                "    FROM Ordine AS O\r\n" + //
                "    JOIN Richiede AS R ON O.Cod_Comanda = R.Cod_Comanda AND O.N_Ordine = R.N_Ordine\r\n" + //
                "    JOIN Vivanda AS V ON R.Cod_vivanda = V.Cod_vivanda\r\n" + //
                "    WHERE O.Cod_Comanda = C.Cod_Comanda AND V.tipologia = 'Bevanda'\r\n" + //
                "\t)\r\n" + //
                ")\r\n" + //
                "WHERE C.Cod_Comanda = ?;";

    private static final String DELETE_ORDER = "DELETE FROM Ordine WHERE Cod_Comanda` = ? N_Ordine` = ?";

    private static final String SELECT_ORDER_NUMBER = "SELECT IF(MAX(N_Ordine) IS NULL, 1, MAX(N_Ordine) + 1) AS N FROM Ordine WHERE Cod_Comanda = ?";
   
    private static final String INSERT_ORDER = "INSERT INTO Ordine(`Cod_Comanda`, `Stato`, `N_Ordine`)\n" + //
                "\tVALUES(?, ?, ?);";

    private static final String INSERT_FOOD_ORDER = "INSERT INTO `richiede`(`Cod_Comanda`, `N_Ordine`, `Cod_vivanda`, `N_vivande`) VALUES (?,?,?,?)";

    private static final String LAST_ORDER_NUMBER = "SELECT MAX(N_Ordine) AS N FROM Ordine WHERE Cod_Comanda = ?";

    private static final String GET_PLATE = "SELECT Nome_vivanda, R.Cod_vivanda, N_vivande\r\n" + //
                "FROM richiede as R, vivanda AS V\r\n" + //
                "WHERE V.Cod_vivanda = R.Cod_vivanda AND R.Cod_Comanda = ? AND R.N_Ordine = ?";

    private static final String DELETE_PLATE_FROM_ORDER = "DELETE FROM richiede\r\n" + //
                "WHERE Cod_Comanda = ? AND N_Ordine = ? AND Cod_vivanda = ?";

    private static final String MODIFY_RECIEPIT = "UPDATE `comanda` SET Modalita_d_odine = ?, Coperti = ?, Data = ?, Ora = ?, Nome_Menu = ?, Num_Tavolo = ? ,CodFiscale = ?\r\n" + //
                "WHERE Cod_Comanda = ?";

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }


    public static void modifyReciepit(Integer codComanda, String modalitaOrdine, Integer nCoperti, Date data, Time ora, String nomeMenu, Integer numTavolo, String codFiscale) {
        try {
            manager.openConnection();
            manager.setQuery(MODIFY_RECIEPIT, modalitaOrdine, nCoperti, data, ora, nomeMenu, numTavolo, codFiscale, codComanda);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deletePlateFromOrder(int codComanda, int nOrdine, int codVivanda) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_PLATE_FROM_ORDER, codComanda, nOrdine, codVivanda);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static List<Map<String, Object>> getPlate(Integer codComanda, Integer nOrdine) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_PLATE, codComanda, nOrdine);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void insertOrderFood(int codComanda, int codVivanda, int nPiatti) {
        try {
            manager.openConnection();
            List<Map<String, Object>> tab = manager.getQuery(LAST_ORDER_NUMBER, codComanda);
            manager.setQuery(INSERT_FOOD_ORDER, codComanda,  tab.get(0).get("N"), codVivanda, nPiatti);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertOrder(int codComanda, String stato) {
        try {
            manager.openConnection();
            List<Map<String, Object>> tab = manager.getQuery(SELECT_ORDER_NUMBER, codComanda);
            manager.setQuery(INSERT_ORDER, codComanda, stato, tab.get(0).get("N"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteOrder(int codComanda, int nOrdine) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_ORDER, codComanda, nOrdine);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertReceipit(String modalitaOrdine, int coperti, Date data,
             Time ora, String nomeMenu, int numTavolo, String codFiscale) {
        try {
            manager.openConnection();
            manager.setQuery(INSERT_RECEIPIT, modalitaOrdine, coperti, data, ora, nomeMenu, numTavolo, codFiscale);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getChek(Integer codComanda) {
        try {
            manager.openConnection();
            manager.setQuery(GET_CHECK, codComanda);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static List<Map<String, Object>> getOrders(Integer codComanda) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_ORDER, codComanda);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static List<Map<String, Object>> getReceipit() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_RECEIPIT);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void deleteReceipit(int codComanda) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_RECEIPIT, codComanda);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static List<Map<String, Object>> getTable() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_TABLE);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}