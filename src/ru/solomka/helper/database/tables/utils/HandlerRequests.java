package ru.solomka.helper.database.tables.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class HandlerRequests {

    @Getter @Setter public int rsInt;
    @Getter @Setter public String rsString;
    @Getter @Setter public boolean rsBoolean;

    public void updateRequest(Connection connect, String db, String object, String name,
                              RequestsType requests, int integer, boolean bool, String string) throws SQLException {
        String set = "UPDATE " + db + " SET " + object + " = (?) WHERE name = '" + name + "'";
        PreparedStatement preparedStatement = connect.prepareStatement(set);
        switch (requests) {
            case INT : {
                preparedStatement.setInt(1, integer);
                preparedStatement.executeUpdate();
                return;
            }
            case STRING: {
                preparedStatement.setString(1, string);
                preparedStatement.executeUpdate();
                return;
            }
            case BOOLEAN: {
                preparedStatement.setBoolean(1, bool);
                preparedStatement.executeUpdate();
                return;
            }
            default : preparedStatement.close();
        }
    }

    public void getRequest(Connection connection, String selected,
                           String db, String compare, String target, RequestsType requests) throws SQLException {
        String get = "SELECT " + selected + " FROM " + db + " WHERE " + target + " = '" + compare + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(get);
        switch (requests) {
            case INT: {
                while (rs.next()) setRsInt(rs.getInt(selected));
                return;
            }
            case STRING: {
                while (rs.next()) setRsString(rs.getString(selected));
                return;
            }
            case BOOLEAN: {
                while (rs.next()) setRsBoolean(rs.getBoolean(selected));
            }
        }
    }
}