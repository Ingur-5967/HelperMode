package ru.solomka.helper.database;

import ru.solomka.helper.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

    public final String DB_DIR = Main.getInstance().getDataFolder() + File.separator + "users.db";
    public final String DB_FILE = "users";
    public final String DB_URL = "jdbc:h2:/" + DB_DIR + "/" + DB_FILE;
    public final String DB_Driver = "org.h2.Driver";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + DB_FILE + "` (" +
                "`name` VARCHAR(15) NOT NULL, " +
                "`helper` TINYINT NOT NULL, " +
                "`grades` INT NOT NULL, " +
                "`status` VARCHAR(15) NOT NULL, " +
                "`muted` TINYINT NOT NULL, " +
                "`blacklist` TINYINT NOT NULL, " +
                "`send_question` TINYINT NOT NULL, " +
                "`reason` VARCHAR(15) NOT NULL, " +
                "`cooldown` TINYINT NOT NULL)");
        Main.getInstance().getLogger().info("Table '" + DB_FILE +  "' successfully created!");
    }

    public DatabaseHandler() throws ClassNotFoundException, SQLException {
        Class.forName(DB_Driver);
        createTable();
    }
}
