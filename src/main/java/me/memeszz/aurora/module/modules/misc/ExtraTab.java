package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
//Credit 086
public class ExtraTab extends Module {
    public static ExtraTab INSTANCE;
    public Setting.i tabsize;

    public ExtraTab() {
        super("ExtraTab", Category.Misc);
        tabsize = this.registerI("Tabsize", "TabSize",255, 1, 1000);

        INSTANCE = this;
    }
    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (Friends.isFriend(dname)) return String.format("%sa%s", Command.SECTIONSIGN(), dname);
        return dname;
    }
}


