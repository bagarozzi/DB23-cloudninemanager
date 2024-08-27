package it.unibo.cloudnine.view.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.sql.Date;
import java.sql.Time;

import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import it.unibo.cloudnine.dao.FoodDAO;
import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.dao.ReceiptDAO;
import it.unibo.cloudnine.data.Food;
import it.unibo.cloudnine.data.Menu;
import it.unibo.cloudnine.data.Order;
import it.unibo.cloudnine.data.Receipit;
import it.unibo.cloudnine.data.Vivanda;
import it.unibo.cloudnine.view.View;

public class ReceiptsTab extends AbstractSplitViewTab{
    private JPanel rightPanelReceipit;
    private JPanel rightPanelOrders;
    private JPanel platePane = new JPanel(new GridLayout(0, 1));
    private JPanel receipitPane = new JPanel(new GridLayout(0, 1));
    private JPanel OrderPane = new JPanel(new GridLayout(0, 1));
    private int selectedCodComanda;
    private JComboBox <String> modalitaOrdine = new JComboBox<>( new Vector<> (List.of("AYCE", "CARTA")));
    private JTextField nCoperti = new JTextField();
    private JTextField data = new JTextField();
    private JTextField ora = new JTextField();
    private JComboBox <String> nomeMenu = new JComboBox <> (getMenu());
    private JComboBox <Integer> numTavolo = new JComboBox <> (getTable());
    private JComboBox <Vivanda> nomeVivanda = new JComboBox <> (getFoods());
    private JTextField numeroPiatti = new JTextField();
    private JTextField cameriere = new JTextField();
    private JTextField stato = new JTextField();

    public ReceiptsTab(View view) {
        super(view);
        refresh();
    }

    private void setOrderleftPane(int codComanda) {
        receipitPane.removeAll();
        OrderPane.removeAll();
        List<Map<String, Object>> tab =  ReceiptDAO.getOrders(codComanda);
        List<Order> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new Order(
                    (Integer) row.get("Cod_Comanda"), 
                    (String) row.get("Stato"),
                    (Integer) row.get("N_Ordine"))));
                    System.out.println(resultTab);
        resultTab.forEach(r -> {
                final JPanel jp = new JPanel(new GridBagLayout());
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("Codice Comanda" + ": " + r.codComanda()), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("Numero Ordine" + ": " + r.nOrdine()), c);
                c.gridx = 0;
                c.gridy = 2;
                jp.add(new JLabel("Stato" + ": " + r.stato()), c);
                OrderPane.add(jp);
                c.gridx = 1;
                c.gridy = 0;
                c.insets = new Insets(0, 30, 0, 0);
                jp.add(getButtonPlate(codComanda, r.nOrdine()), c);
                c.gridx = 1;
                c.gridy = 1;
                jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
                jp.add(getButtonDeleteOrder(r.codComanda(), r.nOrdine()), c);
            });
            super.setLeftPanel(OrderPane);
    }

    private void setOrderRightPane(int codComanda) {
        rightPanelOrders = new JPanel(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPanelOrders.add(getButtonReceipit(), c);
        c.gridx = 0;
        c.gridy = 1;
        rightPanelOrders.add(new JLabel("stato dell'ordine"), c);
        c.gridx = 1;
        c.gridy = 1;
        rightPanelOrders.add(stato, c);
        c.gridx = 0;
        c.gridy = 2;
        rightPanelOrders.add(getButtonInsertOrder(codComanda), c);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(50, 0, 0, 0);
        rightPanelOrders.add(new JLabel("Vivanda: "), c);
        c.insets =  new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 3;
        rightPanelOrders.add(nomeVivanda, c);
        c.gridx = 0;
        c.gridy = 4;
        rightPanelOrders.add(new JLabel("Numero piatti: "), c);
        c.gridx = 1;
        c.gridy = 4;
        rightPanelOrders.add(numeroPiatti, c);
        c.gridx = 0;
        c.gridy = 5;
        var app = (Vivanda) nomeVivanda.getSelectedItem();
        rightPanelOrders.add(getButtonAddPlate(codComanda, app.codVivanda()), c);
        super.setRightPanel(rightPanelOrders);
    }

    private JButton getButtonAddPlate(int codComanda, int codVivanda) {
        var button = new JButton("Aggiungi");
        button.addActionListener(e -> {
                ReceiptDAO.insertOrderFood(codComanda, codVivanda, Integer.valueOf(numeroPiatti.getText()));
                numeroPiatti.setText("");
            });
        return button;
    }

    private JButton getButtonPlate(int codComanda, int nOrdine) {
        var button = new JButton("Vai ai piatti");
        button.addActionListener(e -> {
            setPlatePane(codComanda, nOrdine);
        });
        return button;
    }

    private void setPlatePane(int codComanda, int nOrdine) {
        platePane.removeAll();
        List<Map<String, Object>> tab =  ReceiptDAO.getPlate(codComanda, nOrdine);
        List<Vivanda> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
            (new Vivanda(
                (Integer) row.get("Cod_vivanda"), 
                (String) row.get("Nome_Vivanda"),
                (Integer) row.get("N_vivande"))));
        resultTab.forEach(r -> {
            final JPanel jp = new JPanel(new GridBagLayout());
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            jp.add(new JLabel("Codice vivanda" + ": " + r.codVivanda()), c);
            c.gridx = 0;
            c.gridy = 1;
            jp.add(new JLabel("Nome vivanda" + ": " + r.nome()), c);
            c.gridx = 0;
            c.gridy = 2;
            jp.add(new JLabel("Numero Piatti" + ": " + r.nPiatti()), c);
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 30, 0, 0);
            jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
            jp.add(getButtonDeletePlate(codComanda, nOrdine, r.codVivanda()), c);
            platePane.add(jp);
        });
        super.setLeftPanel(platePane);
    }

    private JButton getButtonDeletePlate(int codComanda, int nOrdine, int codVivanda) {
        var button = new JButton("Cancella");
        button.addActionListener(e -> {
                ReceiptDAO.deletePlateFromOrder(codComanda, nOrdine, codVivanda);
                setPlatePane(codComanda, nOrdine);
            });
        return button;
    }

    private JButton getButtonReceipit() {
        var button = new JButton("Torna alle comande");
        button.addActionListener(e -> refresh());
        return button;
    }

    private JButton getButtonOrder(int codComanda) {
        var button = new JButton("Vai agli Ordini");
        button.addActionListener(e -> {
                setOrderleftPane(codComanda);
                setOrderRightPane(codComanda);
            });
        return button;
    }

    @Override
    public void refresh() {
        receipitPane.removeAll();
        List<Map<String, Object>> tab =  ReceiptDAO.getReceipit();
        List<Receipit> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add
                (new Receipit(
                    (Float) row.get("Conto_finale"),
                    (Integer) row.get("Cod_Comanda"), 
                    (String) row.get("Modalita_d_odine"),
                    (Integer) row.get("Coperti"),
                    (Date) row.get("Data"), 
                    (Time) row.get("Ora"),
                    (String) row.get("Nome_Menu"),
                    (Integer) row.get("Num_Tavolo"), 
                    (String) row.get("CodFiscale"))));
        resultTab.forEach(r -> {
                final JPanel jp = new JPanel(new GridBagLayout());
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                jp.add(new JLabel("Conto" + ": " + r.contoFinale()), c);
                c.gridx = 0;
                c.gridy = 1;
                jp.add(new JLabel("Codice comanda" + ": " + r.codComanda()), c);
                c.gridx = 0;
                c.gridy = 2;
                jp.add(new JLabel("Modatila' d'ordine" + ": " + r.modatlitaOrdine()), c);
                c.gridx = 0;
                c.gridy = 3;
                jp.add(new JLabel("Coperti" + ": " + r.nCoperti()), c);
                c.gridx = 0;
                c.gridy = 4;
                jp.add(new JLabel("Data" + ": " + r.data()), c);
                c.gridx = 0;
                c.gridy = 5;
                jp.add(new JLabel("Ora" + ": " + r.ora()), c);
                c.gridx = 0;
                c.gridy = 6;
                jp.add(new JLabel("Nome menu" + ": " + r.nomeMenu()), c);
                c.gridx = 0;
                c.gridy = 7;
                jp.add(new JLabel("Numero tavolo" + ": " + r.numTavolo()), c);
                c.gridx = 0;
                c.gridy = 8;
                jp.add(new JLabel("Cameriere" + ": " + r.codFiscale()), c);
                c.gridx = 1;
                c.gridy = 2;
                jp.add(getButtonCheck(r.codComanda()), c);
                c.gridx = 1;
                c.gridy = 3;
                jp.add(getButtonDeleteComanda(r.codComanda()), c);
                c.gridx = 1;
                c.gridy = 4;
                jp.add(getButtonOrder(r.codComanda()), c);
                c.gridx = 1;
                c.gridy = 5;
                jp.setBorder(new LineBorder(new Color(90, 93, 97), 3));
                jp.add(getButtonSeleziona(r.codComanda(), r.modatlitaOrdine(), r.nCoperti(), r.data(),
                    r.ora(), r.nomeMenu(), r.numTavolo(), r.codFiscale()), c);
                receipitPane.add(jp);
            });
        super.setLeftPanel(receipitPane);
        initialiseRightPanel();
        super.setRightPanel(rightPanelReceipit);
    }

    private JButton getButtonSeleziona(Integer codComanda, 
            String modatlitaOrdine, Integer numCoperti,
            Date date, Time time, String menu, Integer tavolo, String codiceFiscale) {
        var button = new JButton("Seleziona");
        button.addActionListener(e -> {
            modalitaOrdine.setSelectedItem(modatlitaOrdine);
            selectedCodComanda = codComanda;
            nCoperti.setText(numCoperti.toString());
            data.setText(date.toString());
            ora.setText(time.toString());
            nomeMenu.setSelectedItem(menu);
            numTavolo.setSelectedItem(tavolo);
            cameriere.setText(codiceFiscale);
        });
        return button;
    }

    private JButton getButtonCheck(int codComanda) {
        var button = new JButton("Calcola conto");
        button.addActionListener(e -> {
                ReceiptDAO.getChek(codComanda);
                refresh();
            });
        return button;
    }

    private JButton getButtonModifyComanda() {
        var button = new JButton("Modifica");
        button.addActionListener(e -> {
                ReceiptDAO.modifyReciepit(selectedCodComanda, (String) modalitaOrdine.getSelectedItem(),
                     Integer.valueOf(nCoperti.getText()), Date.valueOf(data.getText()), Time.valueOf(ora.getText()), 
                     (String) nomeMenu.getSelectedItem(), (Integer) numTavolo.getSelectedItem(), cameriere.getText());
                refresh();
            });
        return button;
    }

    private JButton getButtonDeleteComanda(int codComanda) {
        var button = new JButton("Cancella");
        button.addActionListener(e -> {
                ReceiptDAO.deleteReceipit(codComanda);
                refresh();
            });
        return button;
    }

    private JButton getButtonInsertOrder(int codComanda) {
        var button = new JButton("Crea");
        button.addActionListener(e -> {
                ReceiptDAO.insertOrder(codComanda, stato.getText());
                setOrderleftPane(codComanda);
                stato.setText("");
            });
        return button;
    }

    private JButton getButtonDeleteOrder(int codComanda, int nOrdine) {
        var button = new JButton("Cancella");
        button.addActionListener(e -> {
                ReceiptDAO.deleteOrder(codComanda, nOrdine);
                setOrderleftPane(codComanda);
            });
        return button;
    }

    private void initialiseRightPanel() {
        rightPanelReceipit = new JPanel(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        rightPanelReceipit.add(new JLabel("Modatila' d'ordine" + ": "), c);
        c.gridx = 1;
        c.gridy = 2;
        rightPanelReceipit.add(modalitaOrdine, c);
        c.gridx = 0;
        c.gridy = 3;
        rightPanelReceipit.add(new JLabel("Coperti" + ": "), c);
        c.gridx = 1;
        c.gridy = 3;
        rightPanelReceipit.add(nCoperti, c);
        c.gridx = 0;
        c.gridy = 4;
        rightPanelReceipit.add(new JLabel("Data" + ": "), c);
        c.gridx = 1;
        c.gridy = 4;
        rightPanelReceipit.add(data, c);
        c.gridx = 0;
        c.gridy = 5;
        rightPanelReceipit.add(new JLabel("Ora" + ": "), c);
        c.gridx = 1;
        c.gridy = 5;
        rightPanelReceipit.add(ora, c);
        c.gridx = 0;
        c.gridy = 6;
        rightPanelReceipit.add(new JLabel("Nome menu" + ": "), c);
        c.gridx = 1;
        c.gridy = 6;
        rightPanelReceipit.add(nomeMenu, c);
        c.gridx = 0;
        c.gridy = 7;
        rightPanelReceipit.add(new JLabel("Numero tavolo" + ": "), c);
        c.gridx = 1;
        c.gridy = 7;
        rightPanelReceipit.add(numTavolo, c);
        c.gridx = 0;
        c.gridy = 8;
        rightPanelReceipit.add(new JLabel("Cameriere" + ": "), c);
        c.gridx = 1;
        c.gridy = 8;
        rightPanelReceipit.add(cameriere, c);
        c.gridx = 0;
        c.gridy = 9;
        c.insets = new Insets(40, 0, 0, 0);
        rightPanelReceipit.add(getButtonCrea(), c);
        c.gridx = 1;
        c.gridy = 9;
        rightPanelReceipit.add(getButtonModifyComanda(), c);
    }

    private JButton getButtonCrea() {
        var button = new JButton("Crea");
        button.addActionListener(e -> {
            try {
                ReceiptDAO.insertReceipit((String) modalitaOrdine.getSelectedItem(), Integer.valueOf(nCoperti.getText()),
                        Date.valueOf(data.getText()), Time.valueOf(ora.getText()), (String) nomeMenu.getSelectedItem(),
                        (Integer) numTavolo.getSelectedItem(), cameriere.getText());
                refresh();
            } catch(Exception ex) {}
        });
        return button;
    }

    private Vector<Integer> getTable() {
        Vector <Integer> vec = new Vector<>();
        List<Map<String, Object>> tab =  ReceiptDAO.getTable();
        System.out.println(tab);
        tab.forEach(row -> vec.add((Integer) row.get("Num_Tavolo")));
        return vec;
    }
    
    private Vector<String> getMenu() {
        Vector <String> vec = new Vector<>();
        Set<Menu> set = MenuDAO.getAllMenus();
        set.forEach(e -> vec.add(e.nome()));
        return vec;
    }

    private Vector<Vivanda> getFoods() {
        Vector <Vivanda> vec = new Vector<>();
        Set<Food> set = FoodDAO.getAllFoods();
        set.forEach(e -> vec.add(new Vivanda(e.codice(), e.name(), null)));
        return vec;
    }
}
