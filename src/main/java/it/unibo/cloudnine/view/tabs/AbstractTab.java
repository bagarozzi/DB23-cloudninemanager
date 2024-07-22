package it.unibo.cloudnine.view.tabs;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
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

        final JList<JPanel> itemsList = new JList<>();
        final DefaultListModel<JPanel> listModel = new DefaultListModel<>();
        for (int i = 0; i < 10; i++) {
            JPanel jp = new JPanel();
            jp.add(new JLabel("sdfsdfg"));
            listModel.add(i, jp);
        }
        itemsList.setModel(listModel);

        final JScrollPane scrollpane = new JScrollPane(itemsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollpane, new JPanel());
        splitPane.setDividerLocation(0.5d);
        splitPane.setResizeWeight(0.5d);
        this.add(splitPane, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refresh());
        closeButton.addActionListener(e -> view.closeTab(this));
    }

    abstract void refresh();
}
