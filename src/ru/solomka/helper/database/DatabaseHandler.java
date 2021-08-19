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
    public final String DB_URL = "jdbc:sqlite:" + DB_DIR;
    public final String DB_Driver = "org.sqlite.JDBC";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + DB_FILE + "` (" +
                "`name` VARCHAR(15) NOT NULL, " +
                "`helper` TINYINT NOT NULL, " +
                "`like_grades` INT NOT NULL, " +
                "`dislike_grades` INT NOT NULL, " +
                "`all_grades` INT NOT NULL, " +
                "`last_join` VARCHAR(20) NOT NULL, " +
                "`muted` TINYINT NOT NULL, " +
                "`blacklist` TINYINT NOT NULL, " +
                "`active_question` TINYINT NOT NULL, " +
                "`warns` INT NOT NULL, " +
                "`cooldown` TINYINT NOT NULL)");
    }

    public DatabaseHandler() throws ClassNotFoundException, SQLException {
        Class.forName(DB_Driver);
        createTable();
    }
}