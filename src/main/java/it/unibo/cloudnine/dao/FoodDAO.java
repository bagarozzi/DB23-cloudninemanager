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

    private static final String ALL_CATEGORIES = "SELECT * FROM categoria";

    private static final String REMOVE_FOOD = "DELETE FROM vivanda WHERE vivanda.Cod_vivanda = ?";

    private static final String UPDATE_FOOD = "UPDATE vivanda SET Nome_Vivanda = ?, prezzo = ?, tipologia = ?, Nome_Categoria = ? WHERE vivanda.Cod_vivanda = ?";

    private static final String ADD_FOOD = "INSERT INTO `vivanda` (`Cod_vivanda`, `prezzo`, `tipologia`, `Nome_Vivanda`, `Nome_Categoria`) VALUES (NULL, ?, ?, ?, ?);";

    private static final String ADD_CATEGORY = "INSERT INTO `categoria` (`Nome_Categoria`) VALUES (?)";

    private static final String DELETE_CATEGORY = "DELETE FROM categoria WHERE categoria.Nome_Categoria = ?";

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
            System.out.println(e);
        }
        return foods;
    }

    public static Set<String> getAllCategories() {
        final Set<String> categories = new HashSet<>();
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ALL_CATEGORIES);
            result.forEach(category -> {
                categories.add((String)category.get("Nome_Categoria"));
            });
        } catch (SQLException e) {
            System.out.println(e);
        }
        return categories;
    }

    public static void updateFood(final Food food) {
        try {
            manager.openConnection();
            manager.setQuery(UPDATE_FOOD, food.name(), food.price(), food.type(), food.category(), food.codice());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void addFood(final Food food) {
        try {
            manager.openConnection();
            manager.setQuery(ADD_FOOD, food.price(), food.type(), food.name(), food.category());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void removeFood(final Food food) {
        try {
            manager.openConnection();
            manager.setQuery(REMOVE_FOOD, food.codice());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void addCategory(final String category) {
        try {
            manager.openConnection();
            manager.setQuery(ADD_CATEGORY, category);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteCategory(final String category) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_CATEGORY, category);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
