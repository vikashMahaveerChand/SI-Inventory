package com.si.inventory;

import java.awt.Font;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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

        JLabel databaseStatus = new JLabel("Checking MySQL connection and database updates...", SwingConstants.CENTER);
        frame.add(databaseStatus, java.awt.BorderLayout.SOUTH);
        frame.setVisible(true);

        Properties configuration;
        try {
            configuration = DatabaseConnection.loadConfiguration();
        } catch (IOException exception) {
            showDatabaseError(databaseStatus, exception.getMessage(), exception);
            configuration = null;
        }

        if (configuration == null) {
            promptForDatabaseConfiguration(frame, databaseStatus, null);
        } else {
            checkDatabaseConnection(frame, databaseStatus, configuration, false);
        }
    }

    private static void promptForDatabaseConfiguration(JFrame frame, JLabel databaseStatus,
            Properties defaults) {
        Properties configuration = DatabaseSetupDialog.showDialog(frame, defaults);
        if (configuration == null) {
            databaseStatus.setText("MySQL setup cancelled.");
            return;
        }
        checkDatabaseConnection(frame, databaseStatus, configuration, true);
    }

    private static void checkDatabaseConnection(JFrame frame, JLabel databaseStatus, Properties configuration,
            boolean saveConfiguration) {
        databaseStatus.setText("Checking MySQL connection and database updates...");
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                DatabaseConnection.verifyConnection(configuration);
                if (saveConfiguration) {
                    DatabaseConnection.saveConfiguration(configuration);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    databaseStatus.setText("MySQL connected; database changes are up to date.");
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    showDatabaseError(databaseStatus, "Database setup was interrupted.", exception);
                } catch (ExecutionException exception) {
                    Throwable cause = exception.getCause();
                    showDatabaseError(databaseStatus, cause.getMessage(), cause);
                    promptForDatabaseConfiguration(frame, databaseStatus, configuration);
                }
            }
        }.execute();
    }

    private static void showDatabaseError(JLabel databaseStatus, String message, Throwable cause) {
        String details = message == null ? cause.toString() : message;
        databaseStatus.setText("Database setup failed: " + details);
        JOptionPane.showMessageDialog(null, details, "Database Setup Failed", JOptionPane.ERROR_MESSAGE);
    }
}
