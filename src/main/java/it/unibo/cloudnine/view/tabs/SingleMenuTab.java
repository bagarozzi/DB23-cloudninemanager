package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.data.Menu;
import it.unibo.cloudnine.view.View;

import javax.swing.JPanel;
import java.awt.GridLayout;

public class SingleMenuTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();

    public SingleMenuTab(View view, final Menu menu) {
        super(view);
        initializeRightPanel();
        setRightPanel(rightPane);
        setLeftPanel(scrollingPane);
        refresh();
    }

    @Override
    void refresh() {
        
    }

    private void initializeRightPanel() {
        
    }
    
}
