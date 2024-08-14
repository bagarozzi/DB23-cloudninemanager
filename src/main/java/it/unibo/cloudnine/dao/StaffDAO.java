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

    private static final String DELETE_STAFF_MEMBER = "DELETE FROM membro_del_personale WHERE CodFiscale = ?";

    private static final String MODIFY_STAFF_MEMBER = "UPDATE `membro_del_personale` SET `Nome` = ?, `Cognome` = ?, `Professione` = ?, `Ruolo_cuoco` = ?, `Numero_Telefono` = ? WHERE `membro_del_personale`.`CodFiscale` = ?";

    private static final String ADD_STAFF_MEMBER = "INSERT INTO `membro_del_personale` (`CodFiscale`, `Ruolo_cuoco`, `Professione`, `Nome`, `Cognome`, `Numero_Telefono`) VALUES (?, ?, ?, ?, ?, ?)";

    public static Set<StaffMember> getStaff() {
        final Set<StaffMember> staff = new HashSet<>();
        try {
            manager.openConnection();
            List<Map<String, Object>> result = manager.getQuery(ALL_STAFF);
            result.forEach(member -> {
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
            System.out.println(e);
        }
        return staff;
    }

    public static void addStaffMember(final StaffMember member) {
        try {
            manager.openConnection();
            manager.setQuery(
                ADD_STAFF_MEMBER,
                member.code(),
                (member.kitchenRole().isPresent() ? member.kitchenRole() : "NULL"),
                member.job(),
                member.name(),
                member.surname(),
                member.phone()
            );
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void modifyStaffMember(final StaffMember member) {
        try {
            manager.openConnection();
            manager.setQuery(
                MODIFY_STAFF_MEMBER,
                member.name(),
                member.surname(),
                member.job(),
                (member.kitchenRole().isPresent() ? member.kitchenRole() : "NULL"),
                member.phone(),
                member.code()
            );
        } catch (SQLException e) {
            System.out.println(e);
        } 
    }

    public static void deleteStaffMember(final StaffMember member) {
        try {
            manager.openConnection();
            manager.setQuery(DELETE_STAFF_MEMBER, member.code());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
