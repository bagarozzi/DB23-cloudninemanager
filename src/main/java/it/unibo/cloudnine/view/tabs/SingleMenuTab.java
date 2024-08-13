package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.dao.FoodDAO;
import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.data.Menu;
import it.unibo.cloudnine.view.View;
import it.unibo.cloudnine.data.Availability;
import it.unibo.cloudnine.data.Food;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Set;
import java.util.Comparator;
import java.util.List;
import java.util.Vector; 
import java.util.Objects;
import java.util.Map.Entry; 

public class SingleMenuTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();

    private final JLabel currentAvailability = new JLabel();

    private final Vector<Food> comboBoxVector = new Vector<>();
    private final JComboBox<Food> comboBox = new JComboBox<>(comboBoxVector);

    private final Vector<Availability> availabilityVector = new Vector<>();
    private final JComboBox<Availability> availabilityComboBox = new JComboBox<>(availabilityVector);

    private final Vector<Availability> newAvailabilityVector = new Vector<>();
    private final JComboBox<Availability> newAvailabilityComboBox = new JComboBox<>(newAvailabilityVector);
    
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
        availabilityVector.clear();
        newAvailabilityVector.clear();
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
        if (!MenuDAO.getAvailability(menu).isEmpty()) {
            currentAvailability.setText("<html>Disponibilità: " + "<br>" + formatAvailability(menu) + "</html>");
        }
        else {
            currentAvailability.setText("Disponibilità: nessuna");
        }
        MenuDAO.getAvailability(menu).entrySet().forEach(entry -> {
            entry.getValue().forEach(day -> {
                availabilityVector.add(new Availability(day, entry.getKey()));
            });
        });
        List.of("Pranzo", "Cena").forEach(service -> {
            List.of("Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica").forEach(day -> {
                newAvailabilityVector.add(new Availability(day, service));
            });
        });
        newAvailabilityVector.removeAll(availabilityVector);
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
        // Form categoria
        c.gridx = 0; 
        c.gridy = 2;
        c.insets = new Insets(20, 0, 0, 0);
        rightPane.add(new JLabel("Disponibilità da eliminare:"), c);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(20, 0, 0, 0);
        rightPane.add(availabilityComboBox, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        rightPane.add(getConfirmDeleteAvailabilityButton(), c);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(20, 50, 0, 0);
        rightPane.add(new JLabel("Nuova disponibilità: "), c);
        c.gridx = 3;
        c.gridy = 2;
        rightPane.add(newAvailabilityComboBox, c);
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        rightPane.add(getAddAvailabilityButton(), c);
        c.gridx = 0;
        c.gridy = 4;
        rightPane.add(currentAvailability,  c);
    }

    private JButton getAddAvailabilityButton() {
        final JButton button = new JButton("Aggiungi disponibilità");
        button.addActionListener(e -> {
            if(newAvailabilityComboBox.getSelectedIndex() != -1) {
                MenuDAO.addAvailability(
                    newAvailabilityVector.get(newAvailabilityComboBox.getSelectedIndex()),
                    menu
                );
                refresh();
            }
        });
        return button;
    }

    private JButton getConfirmDeleteAvailabilityButton() {
        final JButton button = new JButton("Conferma eliminazione");
        button.addActionListener(e -> {
            if(availabilityComboBox.getSelectedIndex() != -1) {
                MenuDAO.deleteAvailability(
                    availabilityVector.get(availabilityComboBox.getSelectedIndex()),
                    menu
                );
                refresh();
            }
        });
        return button;
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

    private String formatAvailability(final Menu menu) {
        String availability = "";
        for (Entry<String,List<String>> entry : MenuDAO.getAvailability(menu).entrySet()) {
            String days = "";
            for (String day : entry.getValue()) {
                days = days + day + ", "; 
            }
            days = days.substring(0, days.length() - 2);
            availability = availability
            + entry.getKey() + ": " + days + "<br>";
        }
        return availability;
    }
    
}
