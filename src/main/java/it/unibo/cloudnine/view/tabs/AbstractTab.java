package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import it.unibo.cloudnine.view.View;

public abstract class AbstractTab extends JPanel {
    
    public AbstractTab(final View view) {
        final JButton refreshButton = new JButton("Refresh");
        final JButton closeButton = new JButton("Close tab");
        refreshButton.addActionListener(e -> refresh());
        closeButton.addActionListener(e -> view.closeTab(this));
        this.setLayout(new BorderLayout());
        this.add(refreshButton, BorderLayout.NORTH);
        this.add(closeButton, BorderLayout.NORTH);
    }

    abstract void refresh();
}
