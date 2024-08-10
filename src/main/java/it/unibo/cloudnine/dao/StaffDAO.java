package it.unibo.cloudnine.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import it.unibo.cloudnine.core.CloudnineManager;
import it.unibo.cloudnine.core.DatabaseManager;
import it.unibo.cloudnine.data.StaffMember;

public class StaffDAO {

    private static final DatabaseManager manager;

    static {
        manager = CloudnineManager.getDatabaseManager();
    }

    private static final String ALL_STAFF = "SELECT * FROM membro_del_personale";

    public static Set<StaffMember> getStaff() {
        final Set<StaffMember> staff = new HashSet<>();
                try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ALL_STAFF);
            result.forEach(member -> {
                System.out.println(member);
                final Optional<String> kitchenRole = Objects.isNull(member.get("Ruolo_cuoco")) ?
                    Optional.empty() :
                    Optional.of((String)member.get("Ruolo_cuoco")); 
                staff.add(new StaffMember(
                    (String)member.get("CodFiscale"),
                    (String)member.get("Nome"), 
                    (String)member.get("Cognome"), 
                    (String)member.get("Numero_Telefono"), 
                    (String)member.get("Professione"), 
                    kitchenRole
                ));
            });
        } catch (SQLException e) {
            // TODO  
        }
        return staff;
    }
    
}
