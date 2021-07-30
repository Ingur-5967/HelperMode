package ru.solomka.helper.database.tables;

import lombok.Getter;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.DatabaseHandler;
import ru.solomka.helper.database.tables.utils.HandlerRequests;
import ru.solomka.helper.database.tables.utils.IUsers;
import ru.solomka.helper.database.tables.utils.RequestsType;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Users implements IUsers {

    private final DatabaseHandler db = new DatabaseHandler();
    private final HandlerRequests rq = new HandlerRequests();

    @Getter private final Main plugin;

    public Users(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @Override
    public void addUser(String name, String status, String reason, int valueGrades, boolean isHelper,
                        boolean isMuted, boolean blacklist, boolean isActiveQuestion, boolean haveCooldown) throws SQLException {


        String insert = "INSERT INTO " + db.DB_FILE + " (name,helper,grades,status,muted," +
                "blacklist,active_question,reason,cooldown)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = db.getConnection().prepareStatement(insert);

        preparedStatement.setString(1, name);
        preparedStatement.setBoolean(2, isHelper);
        preparedStatement.setInt(3, valueGrades);
        preparedStatement.setString(4, status);
        preparedStatement.setBoolean(5, isMuted);
        preparedStatement.setBoolean(6, blacklist);
        preparedStatement.setBoolean(7, isActiveQuestion);
        preparedStatement.setString(8, reason);
        preparedStatement.setBoolean(9, haveCooldown);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public String getStatusPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "status", db.DB_FILE, name, RequestsType.STRING);
        return rq.getRsString();
    }

    @Override
    public String getReasonMutedPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "reason", db.DB_FILE, name, RequestsType.STRING);
        return rq.getRsString();
    }

    @Override
    public int getValueGradesPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "grades", db.DB_FILE, name, RequestsType.INT);
        return rq.getRsInt();
    }


    @Override
    public boolean isHelperPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "helper", db.DB_FILE, name, RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isHaveCooldown(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "cooldown", db.DB_FILE, name, RequestsType.BOOLEAN);
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
        rq.getRequest(db.getConnection(), "muted", db.DB_FILE, name, RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isBlockingPlayer(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "blacklist", db.DB_FILE, name, RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public boolean isActiveQuestion(String name) throws SQLException {
        rq.getRequest(db.getConnection(), "active_question", db.DB_FILE, name, RequestsType.BOOLEAN);
        return rq.isRsBoolean();
    }

    @Override
    public List<String> getHelpersOnline() throws SQLException {
        rq.getRequest(db.getConnection(), null, db.DB_FILE, null, RequestsType.ALL);
        return rq.getRsList();
    }

    @Override
    public void setStatusPlayer(StatusType status, String name) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "status",
                name, RequestsType.STRING, 0, false, status.getStatus());
    }

    @Override
    public void setReasonMutedPlayer(String reason, String name) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "reason",
                name, RequestsType.STRING, 0, false, reason);
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
    public void setGradesPlayer(String name, int value) throws SQLException {
        rq.updateRequest(db.getConnection(), db.DB_FILE, "grades",
                name, RequestsType.INT, value, false, null);
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
}