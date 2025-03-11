package cz.spse.bajer.gui;

import cz.spse.bajer.object.SpecialOffer;

import javax.swing.*;
import java.awt.event.*;

public class SpecialOfferWindow extends JDialog {
    private JPanel contentPane;
    private JButton OKButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JPanel contentPanel;
    private JTextField nameField;
    private JTextField priceField;
    private JComboBox categoryComboBox;
    private JLabel priceLabel;

    private byte res;
    private SpecialOffer specialOffer;

    public SpecialOfferWindow(SpecialOffer specialOffer, String[] categories) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);

        this.res = 0;
        this.specialOffer = specialOffer;
        categoryComboBox.setModel(new DefaultComboBoxModel(categories));

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
        if (specialOffer != null) {
            nameField.setText(specialOffer.getName());
            priceField.setText(String.valueOf(specialOffer.getPrice()));
//            categoryComboBox.setSelectedItem(specialOffer.getCategory().getName());
        }
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (nameField.getText().trim().isEmpty() || priceField.getText().trim().isEmpty() || categoryComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Fill all fields!",
                    "Error while creating or updating",
                    JOptionPane.ERROR_MESSAGE);
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
        if (this.specialOffer == null) {
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

    public String getSpecialOfferName() {
        return nameField.getText().trim();
    }
    public Float getSpecialOfferPrice() {
        return Float.parseFloat(priceField.getText().trim());
    }
    public String getSpecialOfferCategoryName() {
        return (String) categoryComboBox.getSelectedItem();
    }



}
