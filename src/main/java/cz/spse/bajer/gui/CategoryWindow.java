package cz.spse.bajer.gui;

import cz.spse.bajer.object.Category;

import javax.swing.*;
import java.awt.event.*;

public class CategoryWindow extends JDialog {
    private JPanel contentPane;
    private JButton OKButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JTextField nameField;
    private JLabel nameLabel;

    private byte res;

    private Category category;

    public CategoryWindow(Category category) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);

        res = 0;
        this.category = category;

        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onRemove();
            }
        });

        if (category != null)
            nameField.setText(category.getName());


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // Kontrola, zda je pole vyplněno
        if (nameField.getText().trim().isEmpty()) {
            // Pokud je prázdné, zobrazíme chybovou zprávu
            JOptionPane.showMessageDialog(this,
                    "Fill all fields!",
                    "Error while creating",
                    JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return;
        }
        res = 1;
        dispose();
    }

    private void onCancel() {
        res = 0;
        dispose();
    }

    private void onRemove() {
        if (this.category == null) {
            JOptionPane.showMessageDialog(this,
                    "Can't delete category when creating!",
                    "Error while creating",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this category?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            res = 2;
            dispose();
        }
    }


    public byte getRes() {
        return res;
    }

    public String getCategoryName() {
        return nameField.getText().trim();
    }


}
