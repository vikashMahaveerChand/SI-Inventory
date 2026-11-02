package com.si.inventory;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::showMainWindow);
    }

    private static void showMainWindow() {
        JFrame frame = new JFrame("SI-Inventory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel welcome = new JLabel("Welcome", SwingConstants.CENTER);
        welcome.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        frame.add(welcome);

        frame.setVisible(true);
    }
}
