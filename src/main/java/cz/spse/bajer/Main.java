package cz.spse.bajer;

import cz.spse.bajer.gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}