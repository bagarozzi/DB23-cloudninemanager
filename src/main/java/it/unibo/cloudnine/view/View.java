package it.unibo.cloudnine.view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.tabs.LoginTab;


public class View {

    private final JFrame frame = new JFrame();
    private final JTabbedPane pane = new JTabbedPane();

    public View() {
        frame.pack();
        frame.setSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(pane);
        showLoginTab();
        frame.setVisible(true);
    }

    public void showLoginTab() {
        pane.removeAll();
        pane.add(new LoginTab(this), "Log in!");
    }

    public void showUserTabs(final String user) {
        pane.removeAll();
        switch(UserManagementDAO.getUserType(user)) {
            case ADMIN -> setAdminTabs();
            case COOK -> setCookTabs();
            case WAITER -> setWaiterTabs();
        }
    }

    private void setAdminTabs() {
        /* utenti, inventario, tavoli, comande */
    }

    private void setCookTabs() {
        /* tavoli, comande */
    }

    private void setWaiterTabs() {
        /* inventario */
    }
}
