package it.unibo.cloudnine.dao;

import java.util.List;
import java.util.Map;

import java.sql.Date;


import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;

public class InventoryDAO {
    private static final String INGREDIENTS = "SELECT * FROM ingrediente";

    private static final String ROW_MATERIAL = "SELECT * FROM materia_prima WHERE Nome_ingrediente = ?";
    
    private static final String REMOVE_MATERIAL = "UPDATE Materia_Prima AS M SET Quantita = Quantita - ? WHERE Nome_Ingrediente = ? AND Data_scadenza = (SELECT MIN(Materia_Prima.Data_scadenza) FROM Materia_Prima WHERE Nome_Ingrediente = M.Nome_Ingrediente)";

    private static final String INSERT_INGREDIENT = "INSERT INTO `ingrediente` (`Soglia_critica`, `Costo_al_kg`, `Nome_Ingrediente`) VALUES (?, ?, ?);";

    private static final String INSERT_RAW = "INSERT INTO `materia_prima` (`Data_scadenza`, `Nome_Ingrediente`, `Quantita`, `Data_d_acquisto`) VALUES (?, ?, ?, ?)";

    private static final String GET_EXPIRED = "SELECT * FROM Materia_Prima AS M WHERE M.Data_scadenza < NOW();";

    private static final String GET_CRITIC = "SELECT I.Nome_Ingrediente, Quantita_tot FROM Ingrediente AS I JOIN (SELECT M.Nome_Ingrediente, SUM(M.Quantita) as Quantita_tot FROM Materia_Prima AS M GROUP BY M.Nome_Ingrediente) AS M ON I.Nome_Ingrediente = M.Nome_Ingrediente WHERE I.Nome_Ingrediente = M.Nome_Ingrediente\r\n AND Quantita_tot < I.Soglia_critica;";

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    public static void insertRaw(Date dataScadenza, String nome, Float quantita, Date dataAcquisto) {
        try {
            manager.openConnection();
            manager.setQuery(INSERT_RAW, dataScadenza, nome, quantita, dataAcquisto);
        } catch (Exception e) {
        }
    }

    public static void insertIngredient(Float sogliaCritica, Float costoAlKg, String nome) {
        try {
            manager.openConnection();
            manager.setQuery(INSERT_INGREDIENT, sogliaCritica, costoAlKg, nome);
        } catch (Exception e) {
        }
    }

    public static List<Map<String, Object>> getIngredients() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(INGREDIENTS);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<Map<String, Object>> getRow(String name) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ROW_MATERIAL, name);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<Map<String, Object>> getExpired() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_EXPIRED);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<Map<String, Object>> getCritic() {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_CRITIC);
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public static void removeMaterial(float quantity, String name) {
        try {
            manager.openConnection();
            manager.setQuery(REMOVE_MATERIAL,quantity, name);
        } catch (Exception e) {
        }
    }
}
