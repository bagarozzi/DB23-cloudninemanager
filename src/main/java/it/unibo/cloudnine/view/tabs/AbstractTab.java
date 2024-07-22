package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.cloudnine.view.View;

public abstract class AbstractTab extends JPanel {
    
    public AbstractTab(final View view) {
        final JButton refreshButton = new JButton("Refresh");
        final JButton closeButton = new JButton("Close tab");
        this.add(refreshButton);
        this.add(closeButton);

        refreshButton.addActionListener(e -> refresh());
        closeButton.addActionListener(e -> view.closeTab(this));
    }

    abstract void refresh();
}
