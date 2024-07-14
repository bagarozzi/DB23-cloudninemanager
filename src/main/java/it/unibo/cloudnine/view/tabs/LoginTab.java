package it.unibo.cloudnine.view.tabs;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import it.unibo.cloudnine.dao.UserManagementDAO;
import it.unibo.cloudnine.view.View;

public final class LoginTab extends JPanel {

    private final JButton loginButton = new JButton();
    private final JTextArea usernameField = new JTextArea();
    private final JPasswordField passwordField = new JPasswordField();

    public LoginTab(final View view) {
        this.setLayout(new GridLayout(4, 1));

        /* TODO: luca farà il gridbag layout qui */
        this.add(new JLabel("Log in to the server!"));
        this.add(usernameField);
        this.add(passwordField);
        this.add(loginButton);

        loginButton.addActionListener(e -> {
            if(UserManagementDAO.tryLogin(usernameField.getText(), String.valueOf(passwordField.getPassword()))) {
                view.showUserTabs(usernameField.getText());
            }
        });

    }
}