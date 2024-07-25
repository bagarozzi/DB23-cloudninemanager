package it.unibo.cloudnine.view.tabs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.data.Account;
import it.unibo.cloudnine.view.View;

public final class AccountsTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
    private final JTextField username = new JTextField();
    private final JTextField password = new JTextField();
    private final JTextField confirmPassword = new JTextField();

    public AccountsTab(View view) {
        super(view);
        initializeRightPanel();
        setLeftPanel(scrollingPane);
        setRightPanel(rightPane);
        refresh();
    }

    @Override
    void refresh() {
        UserManagementDAO.getAllAccounts().forEach(account -> {
            final JPanel element = new JPanel(new GridLayout(3, 2));
            element.setMaximumSize(new Dimension(80, 2000));
            element.add(new JLabel("username: " + account.username()));
            element.add(getDeleteAccountButton(account));
            element.add(new JLabel("utente: "
                + account.name()
                + " "
                + account.surname()));
            element.add(getChangePasswordButton(account));
            element.add(new JLabel("Codice fiscale: " + account.codFisc()));
            element.setBorder(new LineBorder(new Color(90, 93, 97), 3));
            scrollingPane.add(element);
        });
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(new JLabel("Nome utente: "), c);
        c.gridx = 1;
        c.gridy = 0;
        rightPane.add(username, c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(new JLabel("Password: "), c);
        c.gridx = 1;
        c.gridy = 1;
        rightPane.add(password, c);
        c.gridx = 0;
        c.gridy = 2;
        rightPane.add(new JLabel("Conferma password: "), c);
        c.gridx = 1;
        c.gridy = 2;
        rightPane.add(confirmPassword, c);
        c.gridx = 0;
        c.gridy = 3;
        rightPane.add(getClearFormButton(), c);
        c.gridx = 1;
        c.gridy = 3 ;
        rightPane.add(getConfirmFormButton(), c);
    }

    private JButton getDeleteAccountButton(final Account account) {
        final JButton button = new JButton("Cancella account");
        button.addActionListener(e -> {
            UserManagementDAO.removeAccount(account);
        });
        return button;
    }

    private JButton getChangePasswordButton(final Account account) {
        final JButton button = new JButton("Cancella account");
        button.addActionListener(e -> {
            UserManagementDAO.removeAccount(account);
        });
        return button;
    }

    private JButton getConfirmFormButton() {
        final JButton button = new JButton("Conferma modifiche");
        return button;
    }

    private JButton getClearFormButton() {
        final JButton button = new JButton("Cancella form");
        button.addActionListener(e -> {
            username.setText("");
            confirmPassword.setText("");
            password.setText("");
        });
        return button;
    }
    
}
