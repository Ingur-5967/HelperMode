package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.config.ConfigManager;

public class MessageHandler {

    private final ConfigManager config = new ConfigManager();

    public String sNotHelper() {
        return config.getString("Message.Helper.NotHelper");
        }

    public String sTakeOffHelper(Player p) {
        return config.getString("Message.Helper.Rank.TakeOffHelper").replace("%player%", p.getName());
        }

    public String sNotHavePermission() {
        return config.getString("Message.NotHavePermission");
    }

    public String sInBlackList(Player p) {
        return config.getString("Message.BlackList.PlayerInBlackList").replace("%player%", p.getName());
    }

    public String sNotInBlackList(Player p) {
        return config.getString("Message.BlackList.PlayerNotBlackList").replace("%player%", p.getName());
    }

    public String sHaveMute(Player p) {
        return config.getString("Message.Helper.Mute.PlayerHaveMute").replace("%player%", p.getName());
    }

    public String sAddMute(Player p, int time) {
        String mute = config.getString("Message.Helper.Mute.PlayerAddMute").replace("%player%", p.getName());
        return mute.replace("%time%", String.valueOf(time));
    }

    public String sHelpersNull() {
        return config.getString("Message.Helper.HelpersNull"); }

    public String haveActiveQuestion() {
        return config.getString("Message.Question.PlayerHaveQuestion"); }

    public String sSendQuestionFormat(String message, Player p) {
        String question =  config.getString("Message.Question.Format").replace("%question%", message);
        return question.replace("%player%", p.getName());
    }

    public String sRankHelper(String status, Player p) {
        String rank = config.getString("Message.Helper.Rank.SetRank").replace("%status%", status);
        return rank.replace("%player%", p.getName());
    }

    public String sEmptyRank(int rank) {
        return config.getString("Message.Helper.Rank.RankEmpty").replace("%rank%", String.valueOf(rank));
    }

    public String pInfBlacklistAdd(Player p, String reason) {
        String infoAdd =config.getString("Message.BlackList.InfoPlayerAddBlacklist").replace("%reason%", reason);
        return infoAdd.replace("%player%", p.getName());
    }

    public String pInfoBlacklistRemove(Player p) {
        return config.getString("Message.BlackList.InfoPlayerRemoveBlacklist").replace("%player%", p.getName());
    }

    public String sBlacklistAdd(Player p, String reason) {
        String add = config.getString("Message.BlackList.AddBlacklistPlayer").replace("%player%", p.getName());
        return add.replace("%reason%", reason);
    }

    public String sBlacklistRemove(Player p) {
        return config.getString("Message.BlackList.RemoveBlacklistPlayer").replace("%player%", p.getName());
    }

    public String sNotHaveQuestion(Player p) {
        return config.getString("Message.Question.PlayerNotHaveQuestion").replace("%player%", p.getName());
    }

    public String sNotMutedPlayer(Player p) {
        return config.getString("Message.Helper.Mute.PlayerNotMuted").replace("%player%", p.getName());
    }

    public String sUnMutePlayer(Player p) {
        return config.getString("Message.Helper.Mute.UnMutePlayer").replace("%player%", p.getName());
    }

    public String pUnMute(Player p) {
        return config.getString("Message.Helper.Mute.UnMute").replace("%player%", p.getName());
    }

    public String sAutoTakeOffMute() {
        return config.getString("Message.Helper.Mute.AutoTakeOffMute");
    }

    public String pIsNull(Player p) {
        return config.getString("Message.PlayerIsEmpty").replace("%player%", p.getName());
    }

    public String sWarn(Player p, int value) {
        String warn = config.getString("Message.Helper.Warns.SetWarn").replace("%warn%", String.valueOf(value));
        return warn.replace("%player%", p.getName());
    }

    public String sInfoWarn(Player p, int value) {
        String infoWarn = config.getString("Message.Helper.Warns.PlayerSetWarn").replace("%value_warn%", String.valueOf(value));
        return infoWarn.replace("%player%", p.getName());
    }

    public String sMessageIsNull() {
        return config.getString("Message.Helper.MessageEmpty");
    }

    public String sMinSizeMessage(int minSizeCount) {
        return config.getString("Message.Helper.ShortMessage").replace("%min_size%", String.valueOf(minSizeCount));
    }
}
