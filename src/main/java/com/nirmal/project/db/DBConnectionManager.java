package com.nirmal.project.db;

import com.nirmal.project.App;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DBConnectionManager.class);
    private static HikariDataSource dataSource;static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(ConfigLoader.get("db.url"));
            config.setUsername(ConfigLoader.get("db.username"));
            config.setPassword(ConfigLoader.get("db.password"));
            config.setMaximumPoolSize(Integer.parseInt(ConfigLoader.get("db.pool.size", "20")));
            config.setConnectionTimeout(Long.parseLong(ConfigLoader.get("db.connection.timeout", "30000")));
            config.setIdleTimeout(Long.parseLong(ConfigLoader.get("db.idle.timeout", "600000")));
            config.setMaxLifetime(Long.parseLong(ConfigLoader.get("db.max.lifetime", "1800000")));
            config.setMinimumIdle(1);

            dataSource = new HikariDataSource(config);
            logger.info("DB Connection Successful ✔️");
        } catch (Exception e) {
           logger.error("❌ Failed to initialize HikariCP: {}" , e.getMessage());
        }
    }
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Unable to get DB connection: {}", e.getMessage());
            return null;
        }
    }

    public static void closeConnection(){
        try{
            dataSource.close();
            logger.info("🛑 DB connection closed.");
        }catch(Exception e){
            logger.error("Error while closing connection: {}", e.getMessage());
        }
    }
}
