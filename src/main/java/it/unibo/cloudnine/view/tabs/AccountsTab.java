package it.unibo.cloudnine.view.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.View;

public final class AccountsTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();

    public AccountsTab(View view) {
        super(view);
        setLeftPanel(scrollingPane);
        setRightPanel(rightPane);
        refresh();
    }

    @Override
    void refresh() {
        UserManagementDAO.getAllAccounts().forEach(account -> {
            final JPanel element = new JPanel(new GridBagLayout());
            final GridBagConstraints constr = new GridBagConstraints();
            constr.gridx = 0;
            constr.gridy = 0;
            element.add(new JLabel("username: " + account.username()), constr);
            scrollingPane.add(element);
        });
    }
    
}
