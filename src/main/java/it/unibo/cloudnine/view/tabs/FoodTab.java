package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import it.unibo.cloudnine.dao.FoodDAO;
import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.view.View;

public class FoodTab extends AbstractSplitViewTab {


    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
    private final JTextField foodName = new JTextField();
    private final JTextField foodCost = new JTextField();
    private final Vector<String> comboBoxVector = new Vector<>();
    private final JComboBox<String> comboBox = new JComboBox<>(comboBoxVector);

    private final View view;

    public FoodTab(View view) {
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
        FoodDAO.getAllFoods().forEach(food -> {
            final JPanel element = new JPanel(new GridBagLayout());
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0; 
            element.add(new JButton("dwesfgs"), c);
            c.gridx = 1;
            c.gridy = 1;
            element.add(new JButton("dwesfgs"), c);
            c.gridx = 1;
            c.gridy = 2;
            element.add(new JButton("dwesfgs"), c);
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 20);
            element.add(new JLabel(food.name()), c);
            c.gridx = 0;
            c.gridy = 1;
            element.add(new JLabel("Categoria: " + food.category()), c);
            c.gridx = 0;
            c.gridy = 2;
            element.add(new JLabel("Prezzo: " + food.price() + " euro"), c);
            element.setBorder(new LineBorder(new Color(90, 93, 97), 3));
            scrollingPane.add(element);
        });
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(new JLabel("Piatto: "), c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(new JLabel("Nome del piatto: "), c);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 40;
        rightPane.add(foodCost, c);
        c.gridx = 1;
        c.gridy = 0;
        rightPane.add(comboBox, c);
        c.gridx = 1;
        c.gridy = 1;
        rightPane.add(foodName, c);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 0;
        rightPane.add(new JLabel("Costo del piatto: "), c);
        c.gridx = 2;
        c.gridy = 0;
        rightPane.add(new JButton("Nuovo piatto"), c);

    }
}
