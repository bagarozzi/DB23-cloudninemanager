package it.unibo.cloudnine.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.tabs.AbstractTab;
import it.unibo.cloudnine.view.tabs.AccountsTab;
import it.unibo.cloudnine.view.tabs.BookingsTab;
import it.unibo.cloudnine.view.tabs.EarningsTab;
import it.unibo.cloudnine.view.tabs.FoodTab;
import it.unibo.cloudnine.view.tabs.InventoryTab;
import it.unibo.cloudnine.view.tabs.LoginTab;
import it.unibo.cloudnine.view.tabs.MenuTab;
import it.unibo.cloudnine.view.tabs.OrderTab;
import it.unibo.cloudnine.view.tabs.ReceiptsTab;
import it.unibo.cloudnine.view.tabs.StaffTab;

/*
 * BAGA: Accounts(done), Earnings, Menu(done), SingleMenu(done), Food(done), Staff(done), alla fine: SingleOrder
 * LUCA: Bookings, Inventory, Order (tutti gli ordini), Receipts (Tutte le comande), alla fine: SingleReceipt
 */

public class View {

    private final JFrame frame = new JFrame();
    private final JButton logoutButton = new JButton("Logout");
    private final JTabbedPane pane = new JTabbedPane();

    public View() {
        frame.pack();
        frame.setSize(new Dimension(1000, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logoutButton.addActionListener(e -> showLoginTab());

        pane.add(logoutButton);
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

    public void openTab(final AbstractTab tab, final String title) {
        pane.add(tab, title);
    }

    private void setAdminTabs() {
        pane.add(new AccountsTab(this), "Account");
        pane.add(new BookingsTab(this), "Prenotazioni");
        pane.add(new MenuTab(this), "Menus");
        pane.add(new FoodTab(this), "Vivande");
        pane.add(new InventoryTab(this), "inventario");
        pane.add(new ReceiptsTab(this), "Comande");
        pane.add(new StaffTab(this), "Staff");
        pane.add(new EarningsTab(this), "Guadagni");
        pane.add(new OrderTab(this), "Ordini");
    }

    private void setCookTabs() {
        pane.add("Inventario", new InventoryTab(this));
        pane.add("Ordini", new OrderTab(this));
    }

    private void setWaiterTabs() {
        pane.add(new ReceiptsTab(this), "Comande");
        pane.add(new BookingsTab(this), "Prenotazioni");
    }
}
