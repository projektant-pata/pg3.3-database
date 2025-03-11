package cz.spse.bajer.gui;

import cz.spse.bajer.facade.CategoryFacade;
import cz.spse.bajer.facade.FoodFacade;
import cz.spse.bajer.facade.SpecialOfferFacade;
import cz.spse.bajer.object.*;

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

    private CategoryFacade categoryFacade;
    private FoodFacade foodFacade;
    private SpecialOfferFacade specialOfferFacade;
    private String[] categories;

    private int lastId = -1;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Main Window");
        setLocationRelativeTo(null);

        categoryFacade = new CategoryFacade();
        foodFacade = new FoodFacade();
        specialOfferFacade = new SpecialOfferFacade();

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        addCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAddCategoryButton(null);
            }
        });

        addSpecialOfferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAddSpecialOfferButton(null);
            }
        });

        addFoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAddFoodButton(null);
            }
        });

        fillWindow();
    }

    private void fillWindow() {
        contentPanel.removeAll();

        categories = categoryFacade.readAllCategoryNames();

        List<Category> categories = categoryFacade.readAll();
        for(Category category : categories) {
            List<Food> foods = foodFacade.readAllFoodsByCategory(category.getId());
            List<SpecialOffer> specialOffers = specialOfferFacade.readAllSpecialOffersByCategory(category.getId());

            JButton categoryButton = new JButton(category.getName());
            categoryButton.setBackground(Color.yellow);
            categoryButton.setPreferredSize(new Dimension(400, 50));
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setAddCategoryButton(category);
                }
            });
            contentPanel.add(categoryButton);

            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new GridLayout(0, 4));

            for(Food food : foods) {
                JButton foodButton = new JButton(food.getName() + " - " + food.getPrice() + " Kč");
                foodButton.setBackground(Color.blue);
                foodButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        setAddFoodButton(food);
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
                        setAddSpecialOfferButton(specialOffer);
                    }
                });
                itemsPanel.add(specialOfferButton);
            }
            contentPanel.add(itemsPanel);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }


    private void setAddCategoryButton(Category category){
        if (category != null)
            lastId = category.getId();
        CategoryWindow categoryWindow = new CategoryWindow(category);
        categoryWindow.pack();
        categoryWindow.setVisible(true);

        switch(categoryWindow.getRes()) {
            case 0:
                break;
            case 1:
                if (lastId == -1)
                    categoryFacade.create(new Category(0, categoryWindow.getCategoryName()));
                else
                    categoryFacade.update(lastId , new Category(lastId, categoryWindow.getCategoryName()));
                fillWindow();
                break;
            case 2:
                categoryFacade.delete(lastId);
                fillWindow();
                break;
        }
        lastId = -1;
    }

    private void setAddFoodButton(Food food){
        if (food != null)
            lastId = food.getId();
        FoodWindow foodWindow = new FoodWindow(food, categories);
        foodWindow.pack();
        foodWindow.setVisible(true);

        switch(foodWindow.getRes()) {
            case 0:
                break;
            case 1:
                if (lastId == -1)
                    foodFacade.create(new Food(0, foodWindow.getFoodName(), foodWindow.getFoodPrice(), foodFacade.translateCategoryNameToId(foodWindow.getCategoryName())));
                else
                    foodFacade.update(lastId ,new Food(lastId, foodWindow.getFoodName(), foodWindow.getFoodPrice(), foodFacade.translateCategoryNameToId(foodWindow.getCategoryName())));
                fillWindow();
                break;
            case 2:
                foodFacade.delete(lastId);
                fillWindow();
                break;
        }
        lastId = -1;
    }

    private void setAddSpecialOfferButton(SpecialOffer specialOffer){
        if (specialOffer != null)
            lastId = specialOffer.getId();
        SpecialOfferWindow specialOfferWindow = new SpecialOfferWindow(specialOffer, categories);
        specialOfferWindow.pack();
        specialOfferWindow.setVisible(true);

        switch(specialOfferWindow.getRes()) {
            case 0:
                break;
            case 1:
                if (lastId == -1) {
                    specialOfferFacade.create(new SpecialOffer(0, specialOfferWindow.getSpecialOfferName(), specialOfferWindow.getSpecialOfferPrice(), specialOfferFacade.translateCategoryNameToId(specialOfferWindow.getSpecialOfferCategoryName())));
                } else
                    specialOfferFacade.update(lastId ,new SpecialOffer(lastId, specialOfferWindow.getSpecialOfferName(), specialOfferWindow.getSpecialOfferPrice(), specialOfferFacade.translateCategoryNameToId(specialOfferWindow.getSpecialOfferCategoryName())));
                fillWindow();
                break;
            case 2:
                specialOfferFacade.delete(lastId);
                fillWindow();
                break;
        }
        lastId = -1;
    }

}

