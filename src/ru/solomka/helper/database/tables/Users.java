package ru.solomka.helper.database.tables;

import lombok.Getter;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.DatabaseHandler;
import ru.solomka.helper.database.tables.utils.HandlerRequests;
import ru.solomka.helper.database.tables.utils.IUsers;
import ru.solomka.helper.database.tables.utils.RequestsType;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users implements IUsers {

    @Getter private final Main plugin;

    private final DatabaseHandler db = new DatabaseHandler();
    private final HandlerRequests rq = new HandlerRequests();

    public Users(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @Override
    public void addUser(String name, boolean helper, boolean isMuted, boolean blacklist,
                        boolean isActiveQuestion, int valueWarns, boolean isHaveCooldown,
                        int likeGrades, int dislikeGrades, int allGrades, String date) throws SQLException {

        String insert = "INSERT INTO " + db.DB_FILE + "(name,helper,muted,blacklist,active_question,warns,cooldown,like_grades," +
                "dislike_grades,all_grades,last_join) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = db.getConnection().prepareStatement(insert);

        preparedStatement.setString(1, name);
        preparedStatement.setBoolean(2, helper);
        preparedStatement.setBoolean(3, isMuted);
        preparedStatement.setBoolean(4, blacklist);
        preparedStatement.setBoolean(5, isActiveQuestion);
        preparedStatement.setInt(6, valueWarns);
        preparedStatement.setBoolean(7, isHaveCooldown);
        preparedStatement.setInt(8, likeGrades);
        preparedStatement.setInt(9, dislikeGrades);
        preparedStatement.setInt(10, allGrades);
        preparedStatement.setString(11, date);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public int getValueLikeGradesPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "like_grades", db.DB_FILE, name, "name", RequestsType.INT);
        return rq.getRsInt();
    }

    @Override
    public int getValueAllGradesPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "all_grades", db.DB_FILE, name,"name", RequestsType.INT);
        return rq.getRsInt();
    }

    @Override
    public int getValueDisLikeGradesPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "dislike_grades", db.DB_FILE, name, "name", RequestsType.INT);
        return rq.getRsInt();
    }

    @Override
    public String getLastJoinPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "last_join", db.DB_FILE, name, "name", RequestsType.STRING);
        return rq.getRsString();
    }

    @Override
    public boolean isHelperPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "helper", db.DB_FILE, name, "name", RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isHaveCooldown(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "cooldown", db.DB_FILE, name, "name", RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isHavePlayer(String name) throws SQLException {
        try(PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM " + db.DB_FILE + " WHERE name = (?)")) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean isMutedPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "muted", db.DB_FILE, name, "name", RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isBlockingPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "blacklist", db.DB_FILE, name, "name", RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isActiveQuestion(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "active_question", db.DB_FILE, name, "name", RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public int getValueWarns(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "warns", db.DB_FILE, name, "name", RequestsType.INT);
        return rq.getRsInt();
    }

    @Override
    public void setCooldownPlayer(String name, boolean value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "cooldown",
                name, RequestsType.BOOLEAN, 0, value, null);
    }

    @Override
    public void setHelperPlayer(String name, boolean value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "helper",
                name, RequestsType.BOOLEAN, 0, value, null);
    }

    @Override
    public void setMutedPlayer(String name, boolean value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "muted",
                name, RequestsType.BOOLEAN, 0, value, null);
    }

    @Override
    public void setBlacklistPlayer(String name, boolean value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "blacklist",
                name, RequestsType.BOOLEAN, 0, value, null);
    }

    @Override
    public void setActiveQuestion(String name, boolean value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "active_question",
                name, RequestsType.BOOLEAN, 0, value, null);
    }

    @Override
    public void setWarns(String name, int value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "warns",
                name, RequestsType.INT, value, false, null);
    }

    @Override
    public void setValueDisLikeGradesPlayer(String name, int value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "dislike_grades",
                name, RequestsType.INT, value, false, null);
    }

    @Override
    public void setValueLikeGradesPlayer(String name, int value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "like_grades",
                name, RequestsType.INT, value, false, null);
    }

    @Override
    public void setValueAllGradesPlayer(String name, int value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "all_grades",
                name, RequestsType.INT, value, false, null);
    }

    public void setLastJoinPlayer(String name, String date) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "last_join",
                name, RequestsType.STRING, 0, false, date);
    }
}