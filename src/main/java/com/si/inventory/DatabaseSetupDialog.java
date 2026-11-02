package com.si.inventory;

import java.awt.GridLayout;
import java.util.Properties;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

final class DatabaseSetupDialog {

    private DatabaseSetupDialog() {
    }

    static Properties showDialog(java.awt.Component parent, Properties defaults) {
        JTextField hostField = new JTextField(value(defaults, "db.host", "localhost"), 20);
        JTextField portField = new JTextField(value(defaults, "db.port", "3306"), 20);
        JTextField databaseField = new JTextField(value(defaults, "db.name", ""), 20);
        JTextField usernameField = new JTextField(value(defaults, "db.username", ""), 20);
        JPasswordField passwordField = new JPasswordField(value(defaults, "db.password", ""), 20);
        KeyboardNavigation.configureEnterAsTab(hostField);
        KeyboardNavigation.configureEnterAsTab(portField);
        KeyboardNavigation.configureEnterAsTab(databaseField);
        KeyboardNavigation.configureEnterAsTab(usernameField);
        KeyboardNavigation.configureEnterAsTab(passwordField);

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.add(new JLabel("MySQL host:"));
        panel.add(hostField);
        panel.add(new JLabel("Port:"));
        panel.add(portField);
        panel.add(new JLabel("Database name:"));
        panel.add(databaseField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Configure MySQL",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        Properties properties = new Properties();
        properties.setProperty("db.host", hostField.getText().trim());
        properties.setProperty("db.port", portField.getText().trim());
        properties.setProperty("db.name", databaseField.getText().trim());
        properties.setProperty("db.username", usernameField.getText().trim());
        properties.setProperty("db.password", new String(passwordField.getPassword()));
        return properties;
    }

    private static String value(Properties properties, String key, String defaultValue) {
        return properties == null ? defaultValue : properties.getProperty(key, defaultValue);
    }
}
