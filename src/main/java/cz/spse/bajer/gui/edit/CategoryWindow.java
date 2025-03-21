// Hlavní třída pro obecné kategorické okno
package cz.spse.bajer.gui.edit;

import cz.spse.bajer.app.App;
import cz.spse.bajer.gui.components.ButtonsEdit;
import cz.spse.bajer.object.templates.AbstractObj;

import javax.swing.*;
import java.awt.*;

public class CategoryWindow extends JDialog {
    protected App app;
    protected AbstractObj obj;
    protected int res;

    public CategoryWindow(AbstractObj obj, App app) {
        this.res = 0;
        this.app = app;
        this.obj = obj;

        setTitle("Change Window");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        buildContent(obj.getId() != 0);

        pack();
        setLocationRelativeTo(null);
    }

    // Tato metoda vytváří obsah okna
    protected void buildContent(boolean isRemove) {
        JPanel formPanel = createFormPanel();

        final ButtonsEdit buttonsPanel = new ButtonsEdit(this, isRemove);


        if (isRemove){
            buttonsPanel.setRemoveAction(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        CategoryWindow.this,
                        "Opravdu chcete smazat tento objekt?",
                        "Potvrzení smazání",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    res = 3;
                    dispose();
                }
            });
            buttonsPanel.setRemoveAction(e -> {res = 3; dispose();});
        }else{
            buttonsPanel.setAddAction(e -> {res = 1; dispose();});
        }

        buttonsPanel.setCloseAction(e -> {
            res = 0;
            dispose();
        });
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    protected JPanel createFormPanel() {
        return new FormNamePanel(obj);
    }

    public int getResult() {
        System.out.println("Result: " + res);
        return res;
    }

    public String getName() {
        return ((FormNamePanel) getContentPane().getComponent(0)).getName();
    }

    protected class FormNamePanel extends JPanel {
        protected final JTextField nameField;

        public FormNamePanel(AbstractObj obj) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel namePanel = new JPanel();
            namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));

            JLabel nameLabel = new JLabel("Name:");
            namePanel.add(nameLabel);
            namePanel.add(Box.createRigidArea(new Dimension(5, 0)));

            nameField = new JTextField();
            nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

            if (obj.getId() != 0) {
                nameField.setText(obj.getName());
            }

            namePanel.add(nameField);
            add(namePanel);

            add(Box.createVerticalStrut(10));
        }

        public String getName() {
            System.out.println("Name: " + nameField.getText());
            return nameField.getText();
        }


    }
}