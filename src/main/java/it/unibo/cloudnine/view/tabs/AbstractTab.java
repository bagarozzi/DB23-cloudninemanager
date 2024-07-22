package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import it.unibo.cloudnine.view.View;

public abstract class AbstractTab extends JPanel {
    
    public AbstractTab(final View view) {
        final JButton refreshButton = new JButton("Refresh");
        final JButton closeButton = new JButton("Close tab");
        this.setLayout(new BorderLayout());
        this.add(refreshButton, BorderLayout.NORTH);
        this.add(closeButton, BorderLayout.NORTH);

        final JPanel itemsList = new JPanel(new GridLayout(0, 1));

        final JScrollPane scrollpane = new JScrollPane(itemsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setWheelScrollingEnabled(true);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollpane, new JPanel());
        splitPane.setDividerLocation(0.5d);
        splitPane.setResizeWeight(0.5d);
        this.add(splitPane, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refresh());
        closeButton.addActionListener(e -> view.closeTab(this));
    }

    abstract void refresh();
}
