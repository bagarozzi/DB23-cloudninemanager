package it.unibo.cloudnine.dao;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;
import it.unibo.cloudnine.data.Food;
import it.unibo.cloudnine.data.Menu;

import java.util.Set;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MenuDAO {

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    private static final String ALL_MENUS = "SELECT menu.Nome_Menu, menu.Costo_menu_AYCE, COUNT(proposta.Cod_vivanda) as num FROM menu LEFT JOIN proposta ON proposta.Nome_Menu = menu.Nome_Menu GROUP BY menu.Nome_Menu";

    private static final String ALL_FOODS = "SELECT proposta.Cod_vivanda, Nome_Vivanda, Nome_Categoria, tipologia, prezzo FROM proposta INNER JOIN vivanda ON proposta.Cod_vivanda = vivanda.Cod_vivanda WHERE proposta.Nome_Menu = ?";

    private static final String DELETE_FOOD_MENU = "DELETE FROM proposta WHERE proposta.Nome_Menu = ? AND proposta.Cod_vivanda = ?";

    private static final String ADD_FOOD_MENU = "INSERT INTO `proposta` (`Nome_Menu`, `Cod_vivanda`) VALUES (?, ?);";

    public static Set<Menu> getAllMenus() {
        final Set<Menu> menus = new HashSet<>();
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ALL_MENUS);
            result.forEach(menu -> {
                menus.add(new Menu(
                    (String)menu.get("Nome_Menu"),
                    (float)menu.get("Costo_menu_AYCE"),
                    (long)menu.get("num")
                ));
            });
        } catch (SQLException e) {
            // TODO 
        }
        return menus;
    }

    public static Menu getMenu() {
        return new Menu("sdfsd", 324.23f, 2);
    }

    public static void deleteMenu(final Menu menu) {
        
    }

    public static Set<Food> getAllFoods(final Menu menu) {
        final Set<Food> foods = new HashSet<>();
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ALL_FOODS, menu.nome());
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

    public static void deleteFromMenu(final Menu menu, final Food food) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_FOOD_MENU, menu.nome(), food.codice());
        } catch (SQLException e) {
            // TODO
        }
    }

    public static void addToMeu(final Menu menu, final Food food) {
        try {
            manager.openConnection();
            manager.setQuery(ADD_FOOD_MENU, menu.nome(), food.codice());
        } catch (SQLException e) {
            // TODO
        }
    }
    
}
