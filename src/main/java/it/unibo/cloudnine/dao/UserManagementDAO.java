package it.unibo.cloudnine.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;
import it.unibo.cloudnine.data.Account;

public class UserManagementDAO {

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    public enum USER_TYPES {
        ADMIN,
        COOK,
        WAITER
    }

    private static final String ACCOUNTS = "SELECT Nome, Cognome, account.CodFiscale, Nome_Utente, Password FROM account INNER JOIN membro_del_personale ON account.CodFiscale = membro_del_personale.CodFiscale";
    
    private static final String LOGIN = "SELECT Password FROM account WHERE account.Nome_Utente = ?";

    private static final String GET_USER_TYPE = "SELECT Professione FROM account INNER JOIN Membro_del_Personale ON account.CodFiscale = Membro_del_Personale.CodFiscale WHERE account.Nome_Utente = ?";

    private static final String DELETE_ACCOUNT = "DELETE FROM account WHERE account.CodFiscale = ?";
    
    public static boolean tryLogin(final String user, final String password) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(LOGIN, user);
            if (result.size() == 1 && result.get(0).get("Password").equals(password)) {
                return true;
            }
        } catch (SQLException e) {
            // TODO
        }
        return false;
    }

    public static USER_TYPES getUserType(final String user) {
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(GET_USER_TYPE, user);
            if (result.size() == 1) {
                return getTypeFromQuery((String)(result.get(0).get("Professione")));
            }
            else {
                
            }
        } catch (SQLException e) {
            // TODO 
        }
        return null;
    }

    public static List<Account> getAllAccounts() {
        final List<Account> resultList = new ArrayList<>();
        try {
            manager.openConnection();
            final List<Map<String, Object>> result = manager.getQuery(ACCOUNTS);
            result.forEach(row -> resultList.add(new Account(
                (String)row.get("Nome"),
                (String)row.get("Cognome"),
                (String)row.get("CodFiscale"),
                (String)row.get("Nome_Utente"),
                (String)row.get("Password")
            )));
        } catch (SQLException e) {
            // TODO 
        }
        return resultList;
    }

    public static void addAccount() {

    }
    
    public static void removeAccount(final Account account) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_ACCOUNT, account.codFisc());
        } catch (SQLException e) {
            // TODO 
        }
    }

    private static USER_TYPES getTypeFromQuery(final String queryType) {
        return switch(queryType) {
            case "Amministratore" -> USER_TYPES.ADMIN;
            case "Cameriere" -> USER_TYPES.WAITER;
            case "Cuoco" -> USER_TYPES.COOK;
            default -> USER_TYPES.WAITER;
        };
    }
}
