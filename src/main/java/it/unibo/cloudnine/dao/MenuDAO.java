package it.unibo.cloudnine.dao;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;
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

    private static final String ALL_MENUS = "SELECT menu.Nome_Menu, menu.Costo_menu_AYCE, COUNT(proposta.Cod_vivanda) as num FROM menu INNER JOIN proposta ON proposta.Nome_Menu = menu.Nome_Menu GROUP BY menu.Nome_Menu";

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
    
}
