package it.unibo.cloudnine.view.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.sql.Time;

import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import it.unibo.cloudnine.dao.BookingsDAO;
import it.unibo.cloudnine.view.View;

public class BookingsTab extends AbstractSplitViewTab {
    private JPanel rightPanel;
    private JPanel leftPane = new JPanel(new GridLayout(0, 1));
    private final JTextField data = new JTextField(3);
    private final JTextField ora = new JTextField(2);
    private final JTextField nominativo = new JTextField();
    private final JTextField coperti = new JTextField();
    private final JTextField telefono = new JTextField();
    private final JTextField codFiscale = new JTextField();

    public record Booking(Date data, Time ora, String nominativo, int coperti, String telefono, String codFiscale) { 
        
    }

    public BookingsTab(final View view) {
        super(view);
        initializeRightPanel();
        refresh();
        super.setRightPanel(rightPanel);
        super.setLeftPanel(leftPane);
    }

    private void initializeRightPanel() {
        rightPanel =  new JPanel(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill =  GridBagConstraints.HORIZONTAL;
        rightPanel.add(new JLabel("data" + ": "), c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill =  GridBagConstraints.HORIZONTAL;
        rightPanel.add(data, c);

        c.gridx = 0;
        c.gridy = 1;
        rightPanel.add(new JLabel("ora" + ": "), c);

        c.gridx = 1;
        c.gridy = 1;
        rightPanel.add(ora, c);

        c.gridx = 0;
        c.gridy = 2;
        rightPanel.add(new JLabel("nominativo" + ": "), c);

        c.gridx = 1;
        c.gridy = 2;
        rightPanel.add(nominativo, c);

        c.gridx = 0;
        c.gridy = 3;
        rightPanel.add(new JLabel("coperti" + ": "), c);

        c.gridx = 1;
        c.gridy = 3;
        rightPanel.add(coperti, c);

        c.gridx = 0;
        c.gridy = 4;
        rightPanel.add(new JLabel("telefono" + ": "), c);

        c.gridx = 1;
        c.gridy = 4;
        rightPanel.add(telefono, c);

        c.gridx = 0;
        c.gridy = 5;
        rightPanel.add(new JLabel("codice fiscale" + ": "), c);

        c.gridx = 1;
        c.gridy = 5;
        rightPanel.add(codFiscale, c);

        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(70, 0, 70, 20);
        rightPanel.add(getClearFormButton(), c);

        c.gridx = 1;
        c.gridy = 6;
        rightPanel.add(getCreaButton(), c);
    }

    @Override
    void refresh() {
        leftPane.removeAll();
        List<Map<String, Object>> tab =  BookingsDAO.getTableBookings();
        List<Booking> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new Booking((Date) row.get("Data"), 
                    (Time) row.get("Ora"), 
                    (String) row.get("Nominativo"),
                    (Integer) row.get("Coperti"), 
                    (String) row.get("Telefono"), 
                    (String) row.get("CodFiscale"))));
        resultTab.forEach(r -> {
                final JPanel jp = new JPanel(new GridBagLayout());
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("data" + ": " + r.data().toString()), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("ora" + ": " + r.ora().toString() + System.lineSeparator()), c);
                c.gridx = 0;
                c.gridy = 2;
                jp.add(new JLabel("nominativo" + ": " + r.nominativo() + System.lineSeparator()), c);
                c.gridx = 0;
                c.gridy = 3;
                jp.add(new JLabel("telefono" + ": " + r.telefono() + System.lineSeparator()), c);
                c.gridx = 0;
                c.gridy = 4;
                jp.add(new JLabel("codFiscale" + ": " + r.codFiscale() + System.lineSeparator()), c);
                c.gridx = 1;
                c.gridy = 2;
                jp.add(new JButton("cancella"), c);
            });
    }


    private JButton getCreaButton() {
        JButton button = new JButton("Crea");
        button.addActionListener(e -> {
            BookingsDAO.insertBooking(Date.valueOf(data.getText()),
                    Time.valueOf(ora.getText()), 
                    nominativo.getText(),
                    Integer.valueOf(coperti.getText()),
                    telefono.getText(),
                    codFiscale.getText());
            refresh();
        });
        return button;
    }

    private JButton getClearFormButton() {
        final JButton button = new JButton("Cancella form");
        button.addActionListener(e -> {
            data.setText("");
            ora.setText("");
            nominativo.setText("");
            coperti.setText("");
            telefono.setText("");
            codFiscale.setText("");
        });
        return button;
    }
}
