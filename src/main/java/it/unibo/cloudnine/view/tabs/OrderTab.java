package it.unibo.cloudnine.view.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import it.unibo.cloudnine.dao.OrderDAO;
import it.unibo.cloudnine.data.Order;
import it.unibo.cloudnine.data.Vivanda;
import it.unibo.cloudnine.view.View;

public class OrderTab extends AbstractSplitViewTab {
    private JPanel orderPane = new JPanel(new GridLayout(0, 1));
    private JPanel platePane = new JPanel(new GridLayout(0, 1));

    public OrderTab(View view) {
        super(view);
        refresh();
    }

    @Override
    void refresh() {
        orderPane.removeAll();
        List<Map<String, Object>> tab = OrderDAO.getOrders();
        List<Order> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add(new Order(
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
            orderPane.add(jp);
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 30, 0, 0);
            jp.add(getButtonPlate(r.nOrdine(), r.codComanda()), c);
        });
        super.setLeftPanel(orderPane);
    }

    private JButton getButtonPlate(int nOrdine, int codComanda) {
        var button = new JButton("Vai ai piatti");
        button.addActionListener(e -> {
            setPlatePane(nOrdine, codComanda);
        });
        return button;
    }

    private void setPlatePane(int nOrdine, int codComanda) {
        platePane.removeAll();
        List<Map<String, Object>> tab = OrderDAO.getPlates(nOrdine, codComanda);
        List<Vivanda> resultTab = new ArrayList<>();
        tab.forEach(row -> resultTab.add(new Vivanda(
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
            platePane.add(jp);
        });
        super.setLeftPanel(platePane);
    }
}
