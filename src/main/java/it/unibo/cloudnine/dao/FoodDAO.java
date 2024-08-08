package it.unibo.cloudnine.dao;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;
import it.unibo.cloudnine.data.Food;

import java.util.Set;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FoodDAO {

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    private static final String ALL_FOODS = "SELECT * FROM vivanda";

    public static Set<Food> getAllFoods() {
        final Set<Food> foods = new HashSet<>();
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ALL_FOODS);
            result.forEach(food -> {
                foods.add(new Food(
                    (int)food.get("Cod_vivanda"),
                    (String)food.get("Nome_Vivanda"),
                    (String)food.get("Nome_Categoria"),
                    (String)food.get("tipologia"),
                    (float)food.get("prezzo")
                ));
            });
        } catch (SQLException e) {
            // TODO  
        }
        return foods;
    }
    
}
