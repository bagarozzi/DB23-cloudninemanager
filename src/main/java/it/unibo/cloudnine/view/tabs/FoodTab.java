package it.unibo.cloudnine.view.tabs;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import it.unibo.cloudnine.view.View;

public class FoodTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    private final JTextField costTextField = new JTextField();

    public FoodTab(View view) {
        super(view);
        initializeRightPanel();
        setLeftPanel(scrollingPane);
        setRightPanel(rightPane);
    }

    @Override
    void refresh() {

    }

    private void initializeRightPanel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeRightPanel'");
    }

}
