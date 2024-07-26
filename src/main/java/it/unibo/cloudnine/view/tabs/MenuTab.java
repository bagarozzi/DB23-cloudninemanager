package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.dao.UserManagementDAO;
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
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.Vector; 

public class MenuTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
    private final JTextField username = new JTextField();
    private final JTextField password = new JTextField();
    private final JTextField confirmPassword = new JTextField();
    private final Vector<String> comboBoxVector = new Vector<>();
    private final JComboBox<String> comboBox = new JComboBox<>(comboBoxVector);

    public MenuTab(View view) {
        super(view);
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
            element.add(new JButton("Dettagli"), c);
            c.gridx = 1;
            c.gridy = 1;
            element.add(new JButton("Elimina menu\'"), c);
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
        
    }
    
}
