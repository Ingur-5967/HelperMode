package ru.solomka.helper.database.tables.utils;

import java.sql.Date;
import java.sql.SQLException;

public interface IUsers {

    void addUser(String name, boolean helper, boolean isMuted, boolean blacklist,
                 boolean isActiveQuestion, int valueWarns, boolean isHaveCooldown,
                 int likeGrades, int dislikeGrades, int allGrades, String date) throws SQLException;

    boolean isHelperPlayer(String name) throws SQLException;

    int getValueDisLikeGradesPlayer(String name) throws SQLException;

    int getValueLikeGradesPlayer(String name) throws SQLException;

    int getValueAllGradesPlayer(String name) throws SQLException;

    String getLastJoinPlayer(String name) throws SQLException;

    boolean isMutedPlayer(String name) throws SQLException;

    boolean isBlockingPlayer(String name) throws SQLException;

    boolean isHaveCooldown(String name) throws SQLException;

    boolean isHavePlayer (String name) throws SQLException;

    int getValueWarns(String name) throws SQLException;

    void setHelperPlayer(String name, boolean value) throws SQLException;

    void setMutedPlayer(String name, boolean value) throws SQLException;

    void setBlacklistPlayer(String name, boolean value) throws SQLException;

    boolean isActiveQuestion(String name) throws SQLException;

    void setCooldownPlayer(String name, boolean value) throws SQLException;

    void setActiveQuestion(String name, boolean value) throws SQLException;

    void setWarns(String name, int value) throws SQLException;

    void setValueDisLikeGradesPlayer(String name, int value) throws SQLException;

    void setValueLikeGradesPlayer(String name, int value) throws SQLException;

    void setValueAllGradesPlayer(String name, int value) throws SQLException;

    void setLastJoinPlayer(String name, String date) throws SQLException;

}
