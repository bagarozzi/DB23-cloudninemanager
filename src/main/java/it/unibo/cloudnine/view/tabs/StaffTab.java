package it.unibo.cloudnine.view.tabs;

import java.util.Vector;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.checkerframework.checker.units.qual.s;

import it.unibo.cloudnine.dao.StaffDAO;
import it.unibo.cloudnine.data.Food;
import it.unibo.cloudnine.data.StaffMember;
import it.unibo.cloudnine.view.View;

public class StaffTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();

    private final Vector<StaffMember> staffVector = new Vector<>();
    private final JComboBox<StaffMember> staffComboBox = new JComboBox<>(staffVector);

    public StaffTab(View view) {
        super(view);
        initializeRightPanel();
        setRightPanel(rightPane);
        setLeftPanel(scrollingPane);
    }

    @Override
    void refresh() {
        StaffDAO.getStaff().forEach(member -> {
            final JPanel element = new JPanel();
            final GridBagConstraints c = new GridBagConstraints();
            element.setLayout(new GridBagLayout());
            c.gridx = 0;
            c.gridy = 0;
            element.add(new JLabel("Nome: " + member.name()), c);
            element.setLayout(new GridBagLayout());
            c.gridx = 0;
            c.gridy = 1;
            element.add(new JLabel("Cognome: " + member.surname()), c);
            element.setLayout(new GridBagLayout());
            c.gridx = 0;
            c.gridy = 2;
            element.add(new JLabel("Codice fiscale: " + member.code()), c);
            element.setLayout(new GridBagLayout());
            c.gridx = 0;
            c.gridy = 3;
            element.add(new JLabel(
                "Professione: " +
                member.job() +
                "Ruolo cuoco: " +
                (member.kitchenRole().isPresent() ? member.kitchenRole().get() : ""
            )), c);
            c.gridx = 1;
            c.gridy = 0;
            element.add(getModifyStaffMemberButton());
            c.gridx = 1;
            c.gridy = 1;
            element.add(getDeleteStaffMemberButton(), c);
            scrollingPane.add(element);
            staffVector.add(member);
        });
    }

    private void initializeRightPanel() {

    }

    private JButton getModifyStaffMemberButton() {
        final JButton button = new JButton("Modifica");
        return button;
    }

    private JButton getDeleteStaffMemberButton() {
        final JButton button = new JButton("Cancella");
        return button;
    }
    
}
