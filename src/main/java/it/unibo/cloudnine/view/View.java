package it.unibo.cloudnine.view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class View {

    private final JFrame frame = new JFrame();
    private final JTabbedPane pane = new JTabbedPane();

    public View() {
        frame.pack();
        frame.setSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showLoginTab();
        frame.setVisible(true);
    }

    public void showLoginTab() {
        pane.removeAll();
        pane.add(new LoginTab(this));
    }

    public void showUserTabs(final String user) {
        
    }
}
