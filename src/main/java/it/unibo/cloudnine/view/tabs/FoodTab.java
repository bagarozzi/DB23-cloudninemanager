package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.checkerframework.checker.units.qual.s;

import com.formdev.flatlaf.util.SystemInfo;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import it.unibo.cloudnine.dao.FoodDAO;
import it.unibo.cloudnine.dao.MenuDAO;
import it.unibo.cloudnine.data.Food;
import it.unibo.cloudnine.view.View;

public class FoodTab extends AbstractSplitViewTab {


    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
    private final JTextField foodName = new JTextField();
    private final JTextField foodCost = new JTextField();
    private final JTextField categoryName = new JTextField();

    private final Vector<Food> comboBoxVector = new Vector<>();
    private final JComboBox<Food> comboBox = new JComboBox<>(comboBoxVector);
    
    private final Vector<String> foodTypeVector = new Vector<>();
    private final JComboBox<String> typeComboBox = new JComboBox<>(foodTypeVector);

    private final Vector<String> categoryVector = new Vector<>();
    private final JComboBox<String> categoryComboBox = new JComboBox<>(categoryVector);

    private final JComboBox<String> categoryFormComboBox = new JComboBox<>(categoryVector);

    private final JButton newFoodButton = new JButton("Nuovo piatto");
    private final JButton confirmButton = new JButton("Conferma modifica");

    private boolean isFormForNewFood = false;

    private final View view;

    public FoodTab(View view) {
        super(view);
        this.view = view;
        initializeRightPanel();
        setLeftPanel(scrollingPane);
        setRightPanel(rightPane);

        newFoodButton.addActionListener(e -> {
            if(!isFormForNewFood) {
                setFormAsNew();
            }
            else {
                setFormAsExsisting();
            }
        });

        confirmButton.addActionListener(e -> {
            if(isFormForNewFood) {
                FoodDAO.addFood(new Food(
                    1,
                    foodName.getText(),
                    categoryVector.get(categoryComboBox.getSelectedIndex()),
                    foodTypeVector.get(typeComboBox.getSelectedIndex()),
                    Float.parseFloat(foodCost.getText())
                ));
            }
            else {
                FoodDAO.updateFood(new Food(
                    comboBoxVector.get(comboBox.getSelectedIndex()).codice(),
                    foodName.getText(),
                    categoryVector.get(categoryComboBox.getSelectedIndex()),
                    foodTypeVector.get(typeComboBox.getSelectedIndex()),
                    Float.parseFloat(foodCost.getText())
                ));
            }
            typeComboBox.removeAllItems();
            categoryComboBox.removeAllItems();
            foodName.setText("");
            foodCost.setText("");
            refresh();
        });

        foodTypeVector.add("Piatto");
        foodTypeVector.add("Bevanda");

        refresh();
    }

    @Override
    void refresh() {
        comboBox.removeAllItems();
        categoryComboBox.removeAllItems();
        comboBoxVector.clear();
        categoryVector.clear();
        scrollingPane.removeAll();
        FoodDAO.getAllFoods().forEach(food -> {
            final JPanel element = new JPanel(new GridBagLayout());
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0; 
            element.add(getModifyButton(food), c);
            c.gridx = 1;
            c.gridy = 1;
            element.add(new JButton(""), c);
            c.gridx = 1;
            c.gridy = 2;
            element.add(getRemoveFoodButton(food), c);
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
            comboBoxVector.add(food);
        });
        categoryVector.addAll(FoodDAO.getAllCategories());
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(new JLabel("Vivanda: "), c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(new JLabel("Nome della vivanda: "), c);
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 40;
        rightPane.add(foodCost, c);
        c.gridx = 1;
        c.gridy = 0;
        rightPane.add(comboBox, c);
        c.gridx = 1;
        c.gridy = 1;
        rightPane.add(foodName, c);
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 0;
        rightPane.add(new JLabel("Costo della vivanda: "), c);
        c.gridx = 2;
        c.gridy = 0;
        rightPane.add(newFoodButton, c);
        c.gridx = 0;
        c.gridy = 2;
        rightPane.add(new JLabel("Tipo di vivanda: "), c);
        c.gridx = 1;
        c.gridy = 2;
        rightPane.add(typeComboBox, c);
        c.gridx = 0;
        c.gridy = 4;
        rightPane.add(new JLabel("Categoria della vivanda"), c);
        c.gridx = 1;
        c.gridy = 4; 
        rightPane.add(categoryComboBox, c);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 50, 0);
        rightPane.add(confirmButton, c);
        // Form categoria
        c.gridx = 0; 
        c.gridy = 6;
        c.insets = new Insets(20, 0, 0, 0);
        rightPane.add(new JLabel("Categoria:"), c);
        c.gridx = 1;
        c.gridy = 6;
        c.insets = new Insets(20, 0, 0, 0);
        rightPane.add(categoryFormComboBox, c);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        rightPane.add(getConfirmDeleteCategoryButton(), c);
        c.gridx = 2;
        c.gridy = 6;
        c.insets = new Insets(20, 50, 0, 0);
        rightPane.add(new JLabel("Nuova categoria: "), c);
        c.gridx = 3;
        c.gridy = 6;
        rightPane.add(categoryName, c);
        c.gridx = 2;
        c.gridy = 7;
        rightPane.add(getAddCategoryButton(), c);
    }

    private JButton getModifyButton(final Food food) {
        final JButton button = new JButton("Modifica");
        button.addActionListener(e -> {
            setFormAsExsisting();
            typeComboBox.setSelectedItem(food.type());
            foodCost.setText(Float.toString(food.price()));
            foodName.setText(food.name());
            comboBox.setSelectedItem(food);
            categoryComboBox.setSelectedItem(food.category());
        });
        return button;
    }

    private JButton getRemoveFoodButton(Food food) {
        final JButton button = new JButton("Rimuovi");
        button.addActionListener(e -> {
            FoodDAO.removeFood(food);
            refresh();
        });
        return button;
    }

    private JButton getConfirmDeleteCategoryButton() {
        final JButton button = new JButton("Cancella categoria");
        button.addActionListener(e -> {
            FoodDAO.deleteCategory(categoryVector.get(categoryFormComboBox.getSelectedIndex()));
            refresh();
        });
        return button;
    }

    private JButton getAddCategoryButton() {
        final JButton button = new JButton("Aggiungi categoria");
        button.addActionListener(e -> {
            FoodDAO.addCategory(categoryName.getText());
            refresh();
            categoryName.setText("");
        });
        return button;
    }

    private void setFormAsExsisting() {
        isFormForNewFood = false;
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        rightPane.add(comboBox, c);
        newFoodButton.setText("Nuovo piatto");
        confirmButton.setText("Conferma modifica");
    }

    private void setFormAsNew() {
        isFormForNewFood = true;
        rightPane.remove(comboBox);
        newFoodButton.setText("Piatto esistente");
        confirmButton.setText("Aggiungi nuovo piatto");
        this.revalidate();
    }
}
