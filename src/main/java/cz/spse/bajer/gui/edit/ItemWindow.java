// Rozšířené okno pro cenové objekty
package cz.spse.bajer.gui.edit;

import cz.spse.bajer.app.App;
import cz.spse.bajer.object.templates.AbstractObj;
import cz.spse.bajer.object.templates.AbstractItem;

import javax.swing.*;
import java.awt.*;

public class ItemWindow extends CategoryWindow {

    public ItemWindow(AbstractObj obj, App app) {
        super(obj, app);
        setTitle("Priced Object Window"); // Změna názvu okna
    }

    @Override
    protected JPanel createFormPanel() {
        return new PricedObjectFormPanel(obj);
    }

    public float getPrice() {
        return ((PricedObjectFormPanel) getContentPane().getComponent(0)).getPrice();
    }

    public String getCategory() {
        return ((PricedObjectFormPanel) getContentPane().getComponent(0)).getCategory();
    }

    protected class PricedObjectFormPanel extends FormNamePanel {
        protected final JTextField priceField;
        protected final JComboBox<String> categoriesComboBox;

        public PricedObjectFormPanel(AbstractObj obj) {
            super(obj); // 

            String[] categories = app.getCategoryManager().readAll().stream()
                    .map(AbstractObj::getName)
                    .toArray(String[]::new);

            JPanel pricePanel = new JPanel();
            pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));

            JLabel priceLabel = new JLabel("Price:");
            pricePanel.add(priceLabel);
            pricePanel.add(Box.createRigidArea(new Dimension(5, 0)));

            priceField = new JTextField();
            priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            if (obj.getId()!= 0){
                priceField.setText(String.valueOf(((AbstractItem) obj).getPrice()));
            }
            pricePanel.add(priceField);

            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));

            JLabel categoryLabel = new JLabel("Category:");
            categoryPanel.add(categoryLabel);
            categoryPanel.add(Box.createRigidArea(new Dimension(5, 0)));

            categoriesComboBox = new JComboBox<>(categories);
            categoriesComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            if (obj.getId() != 0) {
                String categoryName = ((AbstractItem) obj).getCategory().getName();
                System.out.println(categoryName);
                categoriesComboBox.setSelectedItem(categoryName);
            }

            categoryPanel.add(categoriesComboBox);

            add(pricePanel);
            add(Box.createVerticalStrut(10));
            add(categoryPanel);
            add(Box.createVerticalStrut(10));
        }

        public float getPrice() {
            try {
                return Float.parseFloat(priceField.getText());
            } catch (NumberFormatException e) {
                return 0.0F;
            }
        }

        public String getCategory() {
            return (String) categoriesComboBox.getSelectedItem();
        }
    }
}
