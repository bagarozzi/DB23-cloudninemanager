package it.unibo.cloudnine.view.tabs;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.util.Vector; 

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.data.Account;
import it.unibo.cloudnine.view.View;

public final class AccountsTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();
    
    private final JTextField username = new JTextField();
    private final JTextField password = new JTextField();
    private final JTextField confirmPassword = new JTextField();
    private final Vector<String> comboBoxVector = new Vector<>();
    private final JComboBox<String> comboBox = new JComboBox<>(comboBoxVector);

    public AccountsTab(View view) {
        super(view);
        initializeRightPanel();
        setLeftPanel(scrollingPane);
        setRightPanel(rightPane);
        refresh();
    }

    @Override
    void refresh() {
        comboBoxVector.clear();
        scrollingPane.removeAll();
        UserManagementDAO.getAllAccounts().forEach(account -> {
            final JPanel element = new JPanel(new GridBagLayout());
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            element.add(new JLabel("username: " + account.username()), c);
            c.gridx = 1;
            c.gridy = 0;
            element.add(getDeleteAccountButton(account), c);
            c.gridx = 0;
            c.gridy = 1;
            element.add(new JLabel("utente: "
                + account.name()
                + " "
                + account.surname()),
            c);
            c.gridx = 1;
            c.gridy = 1;
            element.add(getChangePasswordButton(account), c);
            c.gridx = 0;
            c.gridy = 2;
            element.add(new JLabel("Codice fiscale: " + account.codFisc()), c);
            element.setBorder(new LineBorder(new Color(90, 93, 97), 3));
            scrollingPane.add(element);
        });
        UserManagementDAO.getAllFiscalCodes().forEach(comboBoxVector::add);
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(new JLabel("Utente su cui fare la modifica: "), c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(new JLabel("Nome utente: "), c);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 40;
        rightPane.add(password, c);
        c.gridx = 1;
        c.gridy = 0;
        rightPane.add(comboBox, c);
        c.gridx = 1;
        c.gridy = 1;
        rightPane.add(username, c);
        c.gridx = 1;
        c.gridy = 3;
        rightPane.add(confirmPassword, c);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 0;
        rightPane.add(new JLabel("Password: "), c);
        c.gridx = 0;
        c.gridy = 3;
        rightPane.add(new JLabel("Conferma password: "), c);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(70, 20, 70, 0);
        rightPane.add(getClearFormButton(), c);
        c.gridx = 1;
        c.gridy = 4;
        rightPane.add(getConfirmFormButton(), c);
    }

    private JButton getDeleteAccountButton(final Account account) {
        final JButton button = new JButton("Cancella account");
        button.addActionListener(e -> {
            UserManagementDAO.removeAccount(account);
            refresh();
        });
        return button;
    }

    private JButton getChangePasswordButton(final Account account) {
        final JButton button = new JButton("Cambia password");
        button.addActionListener(e -> {
            this.username.setText(account.username());
            this.comboBox.setSelectedItem(account.codFisc());
            refresh();
        });
        return button;
    }

    private JButton getConfirmFormButton() {
        final JButton button = new JButton("Conferma modifiche");
        button.addActionListener(e -> {
            if(UserManagementDAO.getAllAccounts().stream().anyMatch(account -> account.username().equals(username.getText()))) {
                UserManagementDAO.modifyAccount(username.getText(), password.getText(), comboBoxVector.get(comboBox.getSelectedIndex()));
            }
            else {
                UserManagementDAO.addAccount(new Account("", "", comboBoxVector.get(comboBox.getSelectedIndex()), username.getText(), password.getText()));
            }
            refresh();
            clearForm();
        });
        return button;
    }

    private JButton getClearFormButton() {
        final JButton button = new JButton("Cancella form");
        button.addActionListener(e -> {
            clearForm();
        });
        return button;
    }

    private void clearForm() {
        username.setText("");
        confirmPassword.setText("");
        password.setText("");
    }
    
}
