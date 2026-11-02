package com.si.inventory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

final class DatabaseConnection {

    private static final String CONFIG_DIRECTORY = "SI-Inventory";
    private static final String CONFIG_FILE = "database.properties";

    private DatabaseConnection() {
    }

    static Properties loadConfiguration() throws IOException {
        Path configPath = getConfigPath();
        Properties properties = new Properties();
        if (!Files.isRegularFile(configPath)) {
            return null;
        }

        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
        }
        return properties;
    }

    static void verifyConnection(Properties properties) throws IOException, SQLException {
        String host = requiredProperty(properties, "db.host");
        String portValue = requiredProperty(properties, "db.port");
        String database = requiredProperty(properties, "db.name");
        String username = requiredProperty(properties, "db.username");
        String password = properties.getProperty("db.password", "");

        int port;
        try {
            port = Integer.parseInt(portValue);
        } catch (NumberFormatException exception) {
            throw new IOException("Database setting db.port must be a number.", exception);
        }
        if (port < 1 || port > 65535) {
            throw new IOException("Database setting db.port must be between 1 and 65535.");
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?connectTimeout=5000&socketTimeout=5000&serverTimezone=UTC";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (!connection.isValid(5)) {
                throw new SQLException("The MySQL server did not validate the connection.");
            }
        }
    }

    static void saveConfiguration(Properties properties) throws IOException {
        Path configPath = getConfigPath();
        Files.createDirectories(configPath.getParent());
        Path temporaryPath = Files.createTempFile(configPath.getParent(), "database-", ".properties");
        try {
            try (OutputStream output = Files.newOutputStream(temporaryPath)) {
                properties.store(output, "SI-Inventory MySQL connection");
            }
            try {
                Files.move(temporaryPath, configPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.ATOMIC_MOVE);
            } catch (java.nio.file.AtomicMoveNotSupportedException exception) {
                Files.move(temporaryPath, configPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporaryPath);
        }
    }

    private static Path getConfigPath() throws IOException {
        String appData = System.getenv("APPDATA");
        if (appData == null || appData.trim().isEmpty()) {
            throw new IOException("The APPDATA environment variable is not set. Cannot store database settings.");
        }
        return Paths.get(appData, CONFIG_DIRECTORY, CONFIG_FILE);
    }

    private static String requiredProperty(Properties properties, String name) throws IOException {
        String value = properties.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IOException("Missing required database setting: " + name);
        }
        return value.trim();
    }
}
