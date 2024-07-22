package it.unibo.cloudnine.view.tabs;

import it.unibo.cloudnine.view.View;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
public abstract class AbstractSplitViewTab extends AbstractTab {

    private final JScrollPane scrollpane = new JScrollPane(new JPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollpane, new JPanel());

    public AbstractSplitViewTab(final View view) {
        super(view);
        scrollpane.setWheelScrollingEnabled(true);
        splitPane.setDividerLocation(0.5d);
        splitPane.setResizeWeight(0.5d);
        this.add(splitPane, BorderLayout.CENTER);
    }

    public void setLeftPanel(final JPanel leftPanel) {
        scrollpane.setViewportView(leftPanel);
    }

    public void setRightPanel(final JPanel rightPanel) {
        splitPane.setRightComponent(rightPanel);
    }

}
