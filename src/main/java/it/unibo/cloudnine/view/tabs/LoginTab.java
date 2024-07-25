package it.unibo.cloudnine.view.tabs;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.View;

public final class LoginTab extends JPanel {

    private final JButton loginButton = new JButton("Log in");
    private final JTextArea usernameField = new JTextArea();
    private final JPasswordField passwordField = new JPasswordField();

    public LoginTab(final View view) {
        this.setLayout(new GridBagLayout());

        /* TODO: luca farà il gridbag layout qui */
        final GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        this.add(new JLabel("Nome utente: "), c);
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 50;
        c.ipady = 3;
        this.add(usernameField, c);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 1;
        c.ipady = 1;
        this.add(new JLabel("Password: "), c);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 50;
        c.ipady = 3;
        this.add(passwordField, c);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 1;
        c.ipady = 1;
        c.insets = new Insets(100, 100, 100, 100);
        c.gridwidth = 2;
        this.add(new JLabel("Log in to the server!"), c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        this.add(loginButton, c);

        loginButton.addActionListener(e -> {
            if(UserManagementDAO.tryLogin(usernameField.getText(), String.valueOf(passwordField.getPassword()))) {
                view.showUserTabs(usernameField.getText());
            }
        });

    }
}