// Komponenta pro tlačítka
package cz.spse.bajer.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonsEdit extends JPanel {
    private final JButton addButton;
    private final JButton removeButton;
    private final JButton closeButton;

    public ButtonsEdit(JDialog parent, boolean isRemove) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        addButton = new JButton("Add");
        add(addButton);

        removeButton = new JButton("Remove");
        if (isRemove) {
            add(removeButton);
        }

        closeButton = new JButton("Close");
        add(closeButton);
    }

    // Metody pro nastavení akcí tlačítek z vnějšku
    public void setAddAction(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setRemoveAction(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void setCloseAction(ActionListener listener) {
        closeButton.addActionListener(listener);
    }
}