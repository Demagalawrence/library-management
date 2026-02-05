package com.library.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

import java.util.Properties;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final String DB_URL = "jdbc:h2:./database/library;DB_CLOSE_DELAY=-1;AUTO_RECONNECT=TRUE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static DatabaseConfig instance;
    private boolean initialized = false;

    private DatabaseConfig() {
        // Private constructor to enforce singleton
    }

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public void initialize() {
        if (initialized) {
            return;
        }

        try {
            // Initialize Flyway
            FluentConfiguration config = Flyway.configure()
                    .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true);

            // Run migrations
            Flyway flyway = config.load();
            flyway.migrate();

            logger.info("Database migration completed successfully");
            initialized = true;
        } catch (Exception e) {
            logger.error("Failed to initialize database", e);
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (!initialized) {
            throw new IllegalStateException("Database not initialized. Call initialize() first.");
        }

        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            logger.error("Failed to get database connection", e);
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }

    public void clean() {
        if (!initialized) {
            return;
        }

        try (Connection conn = getConnection()) {
            // This will drop all objects in the schema db
            Flyway flyway = Flyway.configure()
                    .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                    .load();
            flyway.clean();
            logger.info("Database cleaned successfully");
        } catch (Exception e) {
            logger.error("Failed to clean database", e);
            throw new RuntimeException("Failed to clean database", e);
        }
    }
}
