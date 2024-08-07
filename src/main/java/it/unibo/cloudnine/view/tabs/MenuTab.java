package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.data.Menu;
import it.unibo.cloudnine.view.View;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.Vector; 

public class MenuTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
    private final JTextField menuName = new JTextField();
    private final JTextField menuCost = new JTextField();
    private final Vector<String> comboBoxVector = new Vector<>();
    private final JComboBox<String> comboBox = new JComboBox<>(comboBoxVector);

    private final View view;

    public MenuTab(View view) {
        super(view);
        this.view = view;
        initializeRightPanel();
        setLeftPanel(scrollingPane);
        setRightPanel(rightPane);
        refresh();
    }

    @Override
    void refresh() {
        comboBoxVector.clear();
        scrollingPane.removeAll();
        MenuDAO.getAllMenus().forEach(menu -> {
            final JPanel element = new JPanel(new GridBagLayout());
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0; 
            element.add(getDetailsButton(menu), c);
            c.gridx = 1;
            c.gridy = 1;
            element.add(getDeleteButton(menu), c);
            c.gridx = 1;
            c.gridy = 2;
            element.add(getModifyButton(menu), c);
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 20);
            element.add(new JLabel("Menu: " + menu.nome()), c);
            c.gridx = 0;
            c.gridy = 1;
            element.add(new JLabel("Numero di piatti: " + menu.numPiatti()), c);
            c.gridx = 0;
            c.gridy = 2;
            element.add(new JLabel("Costo AYCE: " + menu.costo() + " euro"), c);
            element.setBorder(new LineBorder(new Color(90, 93, 97), 3));
            scrollingPane.add(element);
            comboBoxVector.add(menu.nome());
        });
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(new JLabel("Menu' su cui fare la modifica: "), c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(new JLabel("Nome del menu': "), c);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 40;
        rightPane.add(menuCost, c);
        c.gridx = 1;
        c.gridy = 0;
        rightPane.add(comboBox, c);
        c.gridx = 1;
        c.gridy = 1;
        rightPane.add(menuName, c);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 0;
        rightPane.add(new JLabel("Costo del menu': "), c);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(70, 20, 70, 0);
        rightPane.add(getClearFormButton(), c);
        c.gridx = 1;
        c.gridy = 4;
        rightPane.add(getConfirmFormButton(), c);
    }

    private JButton getConfirmFormButton() {
        return new JButton("Conferma modifiche");
    }

    private JButton getClearFormButton() {
        final JButton button = new JButton("Pulisci form");
        button.addActionListener(e -> {
            menuName.setText("");
            menuCost.setText("");
        });
        return button;
    }

    private JButton getModifyButton(final Menu menu) {
        final JButton button = new JButton("Modifica");
        button.addActionListener(e -> {
            comboBox.setSelectedItem(menu.nome());
            menuName.setText(menu.nome());
            menuCost.setText(Float.toString(menu.costo()));
        });
        return button;
    }

    private JButton getDeleteButton(final Menu menu) {
        final JButton button = new JButton("Cancella menu");
        button.addActionListener(e -> {
            MenuDAO.deleteMenu(menu);
            refresh();
        });
        return button;
    }

    private JButton getDetailsButton(final Menu menu) {
        final JButton button = new JButton("Detttagli");
        button.addActionListener(e -> {
            view.openTab(new SingleMenuTab(view, menu), "Dettagli di " + menu.nome());
        });
        return button;
    }
    
}
