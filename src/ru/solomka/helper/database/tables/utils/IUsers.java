package ru.solomka.helper.database.tables.utils;

import java.sql.SQLException;
import java.util.List;

public interface IUsers {

    void addUser(String name, String status, String reason, int valueGrades, boolean isHelper,
                 boolean isMuted, boolean blacklist, boolean isActiveQuestion, int valueWarns, boolean isHaveCooldown) throws SQLException;

    String getStatusPlayer(String name) throws SQLException;

    boolean isHelperPlayer(String name) throws SQLException;

    int getValueGradesPlayer(String name) throws SQLException;

    boolean isMutedPlayer(String name) throws SQLException;

    boolean isBlockingPlayer(String name) throws SQLException;

    boolean isHaveCooldown(String name) throws SQLException;

    boolean isHavePlayer (String name) throws SQLException;

    int getValueWarns(String name) throws SQLException;

    String getReasonMutedPlayer(String name) throws SQLException;

    List<String> getHelpersOnline() throws SQLException;

    void setStatusPlayer(StatusType status, String name) throws SQLException;

    void setHelperPlayer(String name, boolean value) throws SQLException;

    void setGradesPlayer(String name, int value) throws SQLException;

    void setMutedPlayer(String name, boolean value) throws SQLException;

    void setReasonMutedPlayer(String reason, String name) throws SQLException;

    void setBlacklistPlayer(String name, boolean value) throws SQLException;

    boolean isActiveQuestion(String name) throws SQLException;

    void setCooldownPlayer(String name, boolean value) throws SQLException;

    void setActiveQuestion(String name, boolean value) throws SQLException;

    void setWarns(String name, int value) throws SQLException;

}
