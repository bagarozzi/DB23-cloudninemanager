package it.unibo.cloudnine.view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.tabs.AbstractTab;
import it.unibo.cloudnine.view.tabs.BookingsTab;
import it.unibo.cloudnine.view.tabs.InventoryTab;
import it.unibo.cloudnine.view.tabs.LoginTab;
import it.unibo.cloudnine.view.tabs.OrderTab;

/*
 * BAGA: Accounts, Earnings, Menu, SingleMenu, Staff, alla fine: SingleOrder
 * LUCA: Bookings, Inventory, Order (tutti gli ordini), Receipts (Tutte le comande), alla fine: SingleReceipt
 */

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

    public void closeTab(final AbstractTab tab) {
        pane.remove(tab);
    }

    private void setAdminTabs() {
        /* tutte */
        pane.add(new BookingsTab(this), "Prenotazioni");
    }

    private void setCookTabs() {
        /* inventario, menù, comande, prenotazioni */
        pane.add("Inventario", new InventoryTab(this));
        pane.add("Ordini", new OrderTab(this));
        /*  */
    }

    private void setWaiterTabs() {
        /* COMANDE, ORDINI, INVENTARIO, PRENOTAZIONI */
    }
}
