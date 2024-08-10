package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import it.unibo.cloudnine.view.View;

public abstract class AbstractTab extends JPanel {

    private final JPanel actionBarPanel = new JPanel(new GridBagLayout());
    
    public AbstractTab(final View view) {
        final JButton refreshButton = new JButton("Refresh");
        final JButton closeButton = new JButton("Close tab");
        refreshButton.addActionListener(e -> refresh());
        closeButton.addActionListener(e -> view.closeTab(this));
        this.setLayout(new BorderLayout());
        
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        actionBarPanel.add(refreshButton, c);
        c.gridx = 1;
        c.gridy = 0;
        actionBarPanel.add(closeButton, c);

        this.add(actionBarPanel, BorderLayout.NORTH);
    }

    abstract void refresh();
}
