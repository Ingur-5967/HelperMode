package ru.solomka.helper.database.tables.utils;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.List;

public class HandlerRequests {

    @Getter public int rsInt;
    @Getter public String rsString;
    @Getter public boolean rsBoolean;
    @Getter public List<String> rsList;



    public void updateRequest(Connection connect, String db, String object, String name,
                       RequestsType requests, int integer, boolean bool, String string) throws SQLException {
        String set = "UPDATE " + db + " SET " + object + " = (?) WHERE name = '" + name + "'";
        PreparedStatement preparedStatement = connect.prepareStatement(set);
        switch(requests) {
            case INT : {
                preparedStatement.setInt(1, integer);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return;
            }
            case STRING : {
                preparedStatement.setString(1, string);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return;
            }
            case BOOLEAN : {
                preparedStatement.setBoolean(1, bool);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            default : preparedStatement.close();
        }
    }

      public void getRequest(Connection connection, String selected, String db, String name, RequestsType requests) throws SQLException {
        String get = "SELECT " + selected + " FROM " + db + " WHERE name = '" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(get);
        switch(requests) {
            case INT : {
                while (rs.next()) rsInt = rs.getInt(selected);
                statement.close();
                return;
            }
            case STRING : {
                while (rs.next()) rsString = rs.getString(selected);
                statement.close();
                return;
            }
            case BOOLEAN : {
                while (rs.next()) rsBoolean = rs.getBoolean(selected);
                statement.close();
            }
            case ALL : {
                String all = "SELECT " + selected + " FROM " + db + " WHERE helper = true";
                Statement statementAll = connection.createStatement();
                ResultSet rsAll = statementAll.executeQuery(all);
                for(int i = 0; i < rsAll.findColumn("name"); i++) {
                    if(!Bukkit.getPlayer(rsAll.getString("name")).isOnline()) continue;
                    rsList.add(rsAll.getString("name"));
                }
            }
            default : statement.close();
        }
    }
}
