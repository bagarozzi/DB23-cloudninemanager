package it.unibo.cloudnine.view.tabs;

import java.util.Optional;
import java.util.Vector;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.cloudnine.dao.StaffDAO;
import it.unibo.cloudnine.data.StaffMember;
import it.unibo.cloudnine.view.View;

public class StaffTab extends AbstractSplitViewTab {

    private final JPanel scrollingPane = new JPanel(new GridLayout(0, 1));
    private final JPanel rightPane = new JPanel();

    private final JTextField codFiscTextField = new JTextField();
    private final JTextField name = new JTextField();
    private final JTextField surname = new JTextField();
    private final JTextField phone = new JTextField();
    private final JTextField kitchenRole = new JTextField();
    private final JTextField job = new JTextField();

    private final JButton newMemberButton = new JButton("Nuovo membro");
    private final JButton confirmButton = new JButton("Conferma modifica");
    private final JButton clearFormButton = new JButton("Pulisci form");
    
    private final Vector<StaffMember> comboBoxVector = new Vector<>();
    private final JComboBox<StaffMember> comboBox = new JComboBox<>(comboBoxVector);

    private boolean isFormForNewMember = false;

    public StaffTab(View view) {
        super(view);
        initializeRightPanel();
        setRightPanel(rightPane);
        setLeftPanel(scrollingPane);
        refresh();

        newMemberButton.addActionListener(e -> {
            if(isFormForNewMember) {
                setFormAsExsisting();
            }
            else {
                setFormAsNew();
            }
        });
        
        clearFormButton.addActionListener(e -> {
            clearForm();
        });

        confirmButton.addActionListener(e -> {
            if(isFormForNewMember) {
                StaffDAO.addStaffMember(
                    new StaffMember(
                        codFiscTextField.getText(),
                        name.getText(),
                        surname.getText(),
                        phone.getText(),
                        job.getText(),
                        (kitchenRole.getText().equals("") ? Optional.empty() : Optional.of(kitchenRole.getText()))
                    )
                );
            }
            else {
                StaffDAO.modifyStaffMember(
                    new StaffMember(
                        comboBoxVector.get(comboBox.getSelectedIndex()).code(),
                        name.getText(),
                        surname.getText(),
                        phone.getText(),
                        job.getText(),
                        (kitchenRole.getText().equals("") ? Optional.empty() : Optional.of(kitchenRole.getText()))
                    )
                );
            }
            clearForm();
            refresh();
        });
    }

    @Override
    void refresh() {
        scrollingPane.removeAll();
        comboBoxVector.clear();
        StaffDAO.getStaff().forEach(member -> {
            final JPanel element = new JPanel();
            final GridBagConstraints c = new GridBagConstraints();
            element.setLayout(new GridBagLayout());
            c.gridx = 0;
            c.gridy = 0;
            element.add(new JLabel("Nome: " + member.name()), c);
            c.gridx = 0;
            c.gridy = 1;
            element.add(new JLabel("Cognome: " + member.surname()), c);
            c.gridx = 0;
            c.gridy = 2;
            element.add(new JLabel("Codice fiscale: " + member.code()), c);
            c.gridx = 0;
            c.gridy = 3;
            element.add(new JLabel(
                "Professione: " +
                member.job() +
                " -- Ruolo cuoco: " +
                (member.kitchenRole().isPresent() ? member.kitchenRole().get() : "nessuno"
            )), c);
            c.gridx = 1;
            c.gridy = 0;
            element.add(getModifyStaffMemberButton(member), c);
            c.gridx = 1;
            c.gridy = 1;
            element.add(getDeleteStaffMemberButton(member), c);
            scrollingPane.add(element);
            comboBoxVector.add(member);
        });
    }

    private void initializeRightPanel() {
        rightPane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        rightPane.add(new JLabel("Codice fiscale: "), c);
        c.gridx = 1;
        c.gridy = 0; 
        rightPane.add(comboBox, c);
        c.gridx = 2;
        c.gridy = 0;
        rightPane.add(newMemberButton, c);
        c.gridx = 0;
        c.gridy = 1;
        rightPane.add(new JLabel("Nome: "), c);
        c.gridx = 1;
        c.gridy = 1;
        rightPane.add(name, c);
        c.gridx = 0;
        c.gridy = 2;
        rightPane.add(new JLabel("Cognome: "), c);
        c.gridx = 1;
        c.gridy = 2;
        rightPane.add(surname, c);
        c.gridx = 0;
        c.gridy = 3;
        rightPane.add(new JLabel("Telefono: "), c);
        c.gridx = 1;
        c.gridy = 3;
        rightPane.add(phone, c);
        c.gridx = 0;
        c.gridy = 4;
        rightPane.add(new JLabel("Ruolo: "), c);
        c.gridx = 1;
        c.gridy = 4;
        rightPane.add(job, c);
        c.gridx = 0;
        c.gridy = 5;
        rightPane.add(new JLabel("Professione in cucina: "), c);
        c.gridx = 1;
        c.gridy = 5;
        rightPane.add(kitchenRole, c);
        c.gridx = 1;
        c.gridy = 6;
        rightPane.add(confirmButton, c);
        c.gridx = 0;
        c.gridy = 6;
        rightPane.add(clearFormButton, c);

    }

    private JButton getModifyStaffMemberButton(final StaffMember member) {
        final JButton button = new JButton("Modifica");
        button.addActionListener(e -> {
            setFormAsExsisting();
            name.setText(member.name());
            surname.setText(member.surname());
            job.setText(member.job());
            kitchenRole.setText((!member.kitchenRole().isPresent() ? "" : member.kitchenRole().get()));
            comboBox.setSelectedItem(member);
            phone.setText(member.phone());
        });
        return button;
    }

    private JButton getDeleteStaffMemberButton(final StaffMember member) {
        final JButton button = new JButton("Cancella");
        button.addActionListener(e -> {
            StaffDAO.deleteStaffMember(member);
            refresh();
        });
        return button;
    }

    private void setFormAsExsisting() {
        isFormForNewMember = false;
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        rightPane.remove(codFiscTextField);
        rightPane.add(comboBox, c);
        newMemberButton.setText("Nuovo membro");
        confirmButton.setText("Conferma modifica");
        this.revalidate();
    }

    private void setFormAsNew() {
        isFormForNewMember = true;
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        rightPane.remove(comboBox);
        rightPane.add(codFiscTextField, c);
        newMemberButton.setText("Membro esistente");
        confirmButton.setText("Aggiungi nuovo membro");
        this.revalidate();
    }
    
    private void clearForm() {
        name.setText("");
        surname.setText("");
        job.setText("");
        kitchenRole.setText("");
        codFiscTextField.setText("");
        comboBox.setSelectedItem(0);
    }

    
}
