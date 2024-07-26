package it.unibo.cloudnine.view.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.sql.Time;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.View;

public class BookingsTab extends AbstractSplitViewTab {
    
    public record Booking(Date data, Time ora, String nominativo, int coperti, String telefono, String codFiscale) { 
        @Override
        public final String toString() {
            return this.data.toString() + " " + ora.toString() + " " + nominativo + " " + coperti() + telefono + " " + codFiscale;
        }
    }

    public BookingsTab(final View view) {
        super(view);
        JPanel leftPane = new JPanel(new GridLayout(0, 1));
        super.setLeftPanel(leftPane);
        List<Map<String, Object>> tab =  UserManagementDAO.getTableBookings();
        List<Booking> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new Booking((Date) row.get("Data"), 
                    (Time) row.get("Ora"), 
                    (String) row.get("Nominativo"),
                    (Integer) row.get("Coperti"), 
                    (String) row.get("Telefono"), 
                    (String) row.get("codFiscale"))));
        System.out.println(resultTab);
        resultTab.forEach(r -> {
                JPanel jp = new JPanel();
                jp.add(new JLabel(r.toString()));
                leftPane.add(jp);
            });
    }

    @Override
    void refresh() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refresh'");
    }
}
