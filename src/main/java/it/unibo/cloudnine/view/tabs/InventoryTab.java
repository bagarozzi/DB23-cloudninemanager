package it.unibo.cloudnine.view.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.LineBorder;
import java.awt.Color;

import it.unibo.cloudnine.dao.InventoryDAO;
import it.unibo.cloudnine.view.View;

public class InventoryTab extends AbstractSplitViewTab {
    private JPanel rightPanel;
    private JPanel ingredientPane = new JPanel(new GridLayout(0, 1));
    private JPanel rawPane = new JPanel(new GridLayout(0, 1));
    private JPanel criticPane = new JPanel(new GridLayout(0, 1));
    private JTextField sogliaCritica = new JTextField();
    private JTextField costoAlKg = new JTextField();
    private JTextField nomeIngrediente = new JTextField();
    private JTextField dataAcquisto = new JTextField();
    private JTextField dataScadenaza = new JTextField();
    private JTextField nomeMateriaPrima = new JTextField();
    private JTextField quantita = new JTextField();


    public record Ingredient(float sogliaCritica, float costoAlKilo, String nome) { 
    }

    public record CriticIngredient(String nome, Double quantity) { 
    }
    public record RawMaterial(Date dataScadenza, String nome, float quantita, Date dataAquisto) { 
    }

    public InventoryTab(View view) {
        super(view);
        initializeRightPanel();
        super.setRightPanel(rightPanel);
        refresh();
    }

    private void initializeRightPanel() {
        rightPanel = new JPanel(new GridBagLayout());
        JButton button = new JButton("Torna agli ingredienti");
        button.addActionListener(e -> refresh());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx=1;
        c.gridy=0;
        c.insets = new Insets(50, 0, 70, 20);
        rightPanel.add(button, c);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx=0;
        c.gridy=1;
        rightPanel.add(new JLabel("Soglia Critica"), c);
        c.gridx=2;
        c.gridy=1;
        rightPanel.add(sogliaCritica, c);
        c.gridx=0;
        c.gridy=2;
        rightPanel.add(new JLabel("Costo al kilo"), c);
        c.gridx=2;
        c.gridy=2;
        rightPanel.add(costoAlKg, c);
        c.gridx=0;
        c.gridy = 3;
        rightPanel.add(new JLabel("Nome"), c);
        c.gridx=2;
        c.gridy=3;
        rightPanel.add(nomeIngrediente, c);
        c.gridx=1;
        c.gridy=4;
        c.insets = new Insets(20, 0, 0, 0);
        rightPanel.add(getButtonCrea(), c);
        c.insets = new Insets(50, 0, 0, 0);
        c.gridx=0;
        c.gridy=5;
        rightPanel.add(new JLabel("Data di scadenza"), c);
        c.gridx=2;
        c.gridy=5;
        rightPanel.add(dataScadenaza, c);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx=0;
        c.gridy=6;
        rightPanel.add(new JLabel("Data di acquisto"), c);
        c.gridx=2;
        c.gridy=6;
        rightPanel.add(dataAcquisto, c);
        c.gridx=0;
        c.gridy = 7;
        rightPanel.add(new JLabel("Nome"), c);
        c.gridx=2;
        c.gridy=7;
        rightPanel.add(nomeMateriaPrima, c);
        c.gridx=0;
        c.gridy=8;
        rightPanel.add(new JLabel("Quantità"), c);
        c.gridx=2;
        c.gridy=8;
        rightPanel.add(quantita, c);
        c.gridx=1;
        c.gridy=9;
        c.insets = new Insets(70, 0, 70, 0);
        rightPanel.add(getButtonAggiungi(), c);
        c.gridx=1;
        c.gridy=10;
        c.insets = new Insets(0, 0, 0, 0);
        rightPanel.add(getButtonSetCritic(c), c);
        c.gridx=2;
        c.gridy=10;
        rightPanel.add(getButtonSetExpired(), c);
    }

    private JButton getButtonSetExpired () {
        var button = new JButton("Ottieni materie \nprime scadute");
        button.addActionListener(e -> {
            setRowExpired();
        });
        return button;
    }

    private JButton getButtonDeleteRaw (Date dataScadenza, String nome, boolean reset) {
        var button = new JButton("Elimina");
        button.addActionListener(e -> {
            InventoryDAO.deleteRaw(dataScadenza, nome);
            if(reset) {
                setRowPane(nome);
            } else {
                setRowExpired();
            }
        });
        return button;
    }

    private void setRowExpired() {
        rawPane.removeAll();
        List<Map<String, Object>> tab =  InventoryDAO.getExpired();
        List<RawMaterial> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new RawMaterial(
                    (Date) row.get("Data_scadenza"),
                    (String) row.get("Nome_Ingrediente"), 
                    (Float) row.get("Quantita"),
                    (Date) row.get("Data_d_acquisto"))));
        resultTab.forEach(r -> {
                final JPanel jp = new JPanel(new GridBagLayout());
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("Data di scadenza" + ": " + r.dataScadenza), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("Data d'acquisto" + ": " + r.dataAquisto), c);
                c.gridx = 0;
                c.gridy = 2;
                jp.add(new JLabel("Nome" + ": " + r.nome), c);
                c.gridx = 0;
                c.gridy = 3;
                jp.add(new JLabel("Quantita'" + ": " + r.quantita), c);
                c.gridx = 1;
                c.gridy = 1;
                jp.add(getButtonDeleteRaw(r.dataScadenza(), r.nome(), false), c); 
                jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
                rawPane.add(jp);
            });
        super.setLeftPanel(rawPane);
    }

    private JButton getButtonSetCritic(final GridBagConstraints c) {
        var button = new JButton("Ottieni ingredienti \nsotto soglia critica");
        button.addActionListener(e -> {
            setCritic(c);
        });
        return button;
    }

    private void setCritic(final GridBagConstraints c) {
        criticPane.removeAll();
        List<Map<String, Object>> tab =  InventoryDAO.getCritic();
        List<CriticIngredient> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new CriticIngredient(
                    (String) row.get("Nome_Ingrediente"),
                    (Double) row.get("Quantita_tot")))); 
        System.out.println(resultTab);
        resultTab.forEach(r -> {
                final JPanel jp = new JPanel(new GridBagLayout());
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("Nome" + ": " + r.nome), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("Quantita'" + ": " + r.quantity), c);
                jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
                criticPane.add(jp);
            });
        super.setLeftPanel(criticPane);
    }

    @Override
    void refresh() {
        ingredientPane.removeAll();
        rawPane.removeAll();
        List<Map<String, Object>> tab =  InventoryDAO.getIngredients();
        List<Ingredient> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new Ingredient(
                    (Float) row.get("Soglia_critica"),
                    (Float) row.get("Costo_al_kg"), 
                    (String) row.get("Nome_Ingrediente"))));
        resultTab.forEach(r -> {
                final JPanel jp = new JPanel(new GridBagLayout());
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("Soglia Critica" + ": " + r.sogliaCritica), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("Costo al kg" + ": " + r.costoAlKilo), c);
                c.gridx = 0;
                c.gridy = 2;
                jp.add(new JLabel("Nome" + ": " + r.nome), c);
                c.gridx = 1;
                c.gridy = 2;
                jp.add(getButtonDettagli(r), c);
                jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
                ingredientPane.add(jp);
            });
            super.setLeftPanel(ingredientPane);
    }


    private JButton getButtonAggiungi() {
        JButton button = new JButton("Aggiungi");
        button.addActionListener(e -> {
            InventoryDAO.insertRaw(Date.valueOf(dataAcquisto.getText()),
                    nomeMateriaPrima.getText(),
                    Float.valueOf(quantita.getText()),
                    Date.valueOf(dataAcquisto.getText()));
                    dataAcquisto.setText("");
                    dataScadenaza.setText("");
                    quantita.setText("");
                    nomeMateriaPrima.setText("");
        });
        return button;
    }

    private JButton getButtonDiminuisci(JTextField quantityField, String name, Date dataScadenza) {
        JButton button = new JButton("Diminuisci");
        button.addActionListener(e -> {
            try {
                InventoryDAO.removeMaterial(Float.valueOf(quantityField.getText()), name, dataScadenza);
                quantityField.setText("");
                setRowPane(name);
            } catch (Exception ex) {
            }
        });
        return button;
    }


    private JButton getButtonCrea() {
        JButton button = new JButton("Crea");
        button.addActionListener(e -> {
            try {
                InventoryDAO.insertIngredient(Float.valueOf(sogliaCritica.getText()),
                        Float.valueOf(costoAlKg.getText()),
                        nomeIngrediente.getText());
                refresh();
                sogliaCritica.setText("");
                costoAlKg.setText("");
                nomeIngrediente.setText("");
            } catch(Exception ex){}
        });
        return button;
    }

    private JButton getButtonDettagli(Ingredient r) {
        JButton button = new JButton("Dettagli");
        button.addActionListener(e -> {
            setRowPane(r.nome);
        });
        return button;
    }
    
    private void setRowPane(String name) {
        rawPane.removeAll();
        List<Map<String, Object>> tab =  InventoryDAO.getRow(name);
        List<RawMaterial> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new RawMaterial(
                    (Date) row.get("Data_scadenza"),
                    (String) row.get("Nome_Ingrediente"), 
                    (Float) row.get("Quantita"),
                    (Date) row.get("Data_d_acquisto"))));
        resultTab.forEach(r -> {
                JTextField quantityField = new JTextField();
                final JPanel jp = new JPanel(new GridBagLayout());
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("Data di scadenza" + ": " + r.dataScadenza), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("Data d'acquisto" + ": " + r.dataAquisto), c);
                c.gridx = 0;
                c.gridy = 2;
                jp.add(new JLabel("Nome" + ": " + r.nome), c);
                c.gridx = 0;
                c.gridy = 3;
                jp.add(new JLabel("Quantita'" + ": " + r.quantita), c);
                c.gridx = 1;
                c.gridy = 2;
                jp.add(quantityField, c);
                c.gridx = 1;
                c.gridy = 3;
                jp.add(getButtonDiminuisci(quantityField, r.nome, r.dataScadenza), c);
                c.gridx = 1;
                c.gridy = 1;
                jp.add(getButtonDeleteRaw(r.dataScadenza(), r.nome(), true), c); 
                jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
                rawPane.add(jp);
            });
        super.setLeftPanel(rawPane);
    }
}
