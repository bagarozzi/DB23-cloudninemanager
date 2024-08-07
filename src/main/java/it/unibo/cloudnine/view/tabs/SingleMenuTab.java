package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.data.Menu;
import it.unibo.cloudnine.view.View;
import it.unibo.cloudnine.data.Food;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.util.Set;
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.List;

public class SingleMenuTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
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
            System.out.println(food.name());
            scrollingPane.add(element);
        });
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

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
    
}
