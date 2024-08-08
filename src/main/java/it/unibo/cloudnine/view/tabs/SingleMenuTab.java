package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.dao.FoodDAO;
import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.data.Menu;
import it.unibo.cloudnine.view.View;
import it.unibo.cloudnine.data.Food;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.util.Set;
import java.util.Comparator;
import java.util.List;
import java.util.Vector; 
import java.util.Objects; 

public class SingleMenuTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();

    private final Vector<Food> comboBoxVector = new Vector<>();
    private final JComboBox<Food> comboBox = new JComboBox<>(comboBoxVector);
    
    private final Menu menu;

    public SingleMenuTab(View view, final Menu menu) {
        super(view);
        this.menu = menu;
        initializeRightPanel();
        setRightPanel(rightPane);
        setLeftPanel(scrollingPane);
        refresh();
    }

    @Override
    void refresh() {
        scrollingPane.removeAll();
        comboBoxVector.clear();
        final Set<Food> everyFood = FoodDAO.getAllFoods();
        everyFood.removeAll(MenuDAO.getAllFoods(menu));
        comboBoxVector.addAll(everyFood);
        orderFoods(MenuDAO.getAllFoods(menu)).forEach(food -> {
            final JPanel element = new JPanel();
            element.setLayout(new GridBagLayout());
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            element.add(new JLabel("Vivanda: " + food.name()), c);
            c.gridx = 0;
            c.gridy = 1;
            element.add(new JLabel("Prezzo: " + food.price()), c);
            c.gridx = 0;
            c.gridy = 2;
            element.add(getRemoveFoodButton(food), c);
            scrollingPane.add(element);
        });
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(comboBox, c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(getAddFoodButton(), c);
    }

    private List<Food> orderFoods(final Set<Food> foodSet) {
        return foodSet.stream().sorted(new Comparator<Food>() {

            @Override
            public int compare(Food f1, Food f2) {
                return f1.category().compareTo(f2.category());
            }
            
        })
        .toList();
    }

    private JButton getRemoveFoodButton(final Food food) {
        final JButton button = new JButton("Rimuovi dal menu'");
        button.addActionListener(e -> {
            MenuDAO.deleteFromMenu(menu, food);
            refresh();
        });
        return button;
    }

    private JButton getAddFoodButton() {
        final JButton button = new JButton("Aggiungi al menu'");
        button.addActionListener(e -> {
            if (Objects.nonNull(comboBox.getSelectedItem())) {
                MenuDAO.addToMeu(menu, (Food)comboBox.getSelectedItem());
                refresh();
            }
        });
        return button;
    }
    
}
