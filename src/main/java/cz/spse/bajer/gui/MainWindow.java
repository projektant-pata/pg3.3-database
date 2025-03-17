package cz.spse.bajer.gui;

import cz.spse.bajer.app.App;
import cz.spse.bajer.app.interfaces.ICategoryManager;
import cz.spse.bajer.app.interfaces.IFoodManager;
import cz.spse.bajer.app.interfaces.ISpecialOfferManager;
import cz.spse.bajer.gui.edit.CategoryWindow;
import cz.spse.bajer.gui.edit.ItemWindow;
import cz.spse.bajer.object.*;
import cz.spse.bajer.object.templates.AbstractObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JButton addCategoryButton;
    private JButton addSpecialOfferButton;
    private JButton addFoodButton;
    private JPanel addBtnsPanel;
    private JPanel contentPanel;

    private ICategoryManager categoryManager;
    private IFoodManager foodManager;
    private ISpecialOfferManager specialOfferManager;

    private App app;
    private int lastId = -1;

    public MainWindow(App app) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Main Window");
        setLocationRelativeTo(null);

        this.app = app;
        this.categoryManager = app.getCategoryManager();
        this.foodManager = app.getFoodManager();
        this.specialOfferManager = app.getSpecialOfferManager();

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        addCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Category category = new Category(0, "temp");
                editDialogBuilder(category);
            }
        });
        addFoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Food food = new Food(0, "temp", 0, new Category(0, "temp"));
                editDialogBuilder(food);
            }
        });
        addSpecialOfferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SpecialOffer specialOffer = new SpecialOffer(0, "temp", 0, new Category(0, "temp"));
                editDialogBuilder(specialOffer);
            }
        });

        fillWindow();
    }

    private void fillWindow() {
        contentPanel.removeAll();
        List<Category> categories = categoryManager.readAll();

        for(Category category : categories){
            System.out.println(category.getName());
            JButton categoryButton = new JButton(category.getName());
            categoryButton.setBackground(Color.yellow);
            categoryButton.setPreferredSize(new Dimension(400, 50));
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    editDialogBuilder(category);
                }
            });
            contentPanel.add(categoryButton);

            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new GridLayout(0, 4));
            List<Food> foods = foodManager.readAllByCategory(category.getId());
            List<SpecialOffer> specialOffers = specialOfferManager.readAllByCategory(category.getId());

            for(Food food : foods) {
                JButton foodButton = new JButton(food.getName() + " - " + food.getPrice() + " Kč");
                foodButton.setBackground(Color.blue);
                foodButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        editDialogBuilder(food);
                    }
                });
                itemsPanel.add(foodButton);
            }
            for(SpecialOffer specialOffer : specialOffers) {
                JButton specialOfferButton = new JButton(specialOffer.getName() + " - " + specialOffer.getPrice() + " Kč");
                specialOfferButton.setBackground(Color.green);
                specialOfferButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        editDialogBuilder(specialOffer);
                    }
                });
                itemsPanel.add(specialOfferButton);
            }
            contentPanel.add(itemsPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void editDialogBuilder(AbstractObj obj){
        CategoryWindow window;

        if(obj == null)
            return;
        else if(obj instanceof Category){
            window = new CategoryWindow(obj, app);
        }else{
            window = new ItemWindow(obj, app);
        }
        window.setVisible(true);

        switch (window.getResult()) {
            case 0:
                break;
            case 1:
                if(obj instanceof Category) {
                    Category category = new Category(0, window.getName());
                    categoryManager.create(category);
                } else if (obj instanceof Food) {
//                    Food food = new Food(0, window.getName(), window.getPrice(), window.getCategory());
                    foodManager.create((Food) obj);
                } else if (obj instanceof SpecialOffer) {
                    specialOfferManager.create((SpecialOffer) obj);
                }
                break;

            case 2:
                break;
        }
    }
}

