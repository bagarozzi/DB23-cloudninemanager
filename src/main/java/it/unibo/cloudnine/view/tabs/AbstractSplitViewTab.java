package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.view.View;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;

public abstract class AbstractSplitViewTab extends AbstractTab {

    public AbstractSplitViewTab(final View view) {
        super(view);
        final JPanel itemsList = new JPanel(new GridLayout(0, 1));

        final JScrollPane scrollpane = new JScrollPane(itemsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setWheelScrollingEnabled(true);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollpane, new JPanel());
        splitPane.setDividerLocation(0.5d);
        splitPane.setResizeWeight(0.5d);
        this.add(splitPane, BorderLayout.CENTER);
    }

}
