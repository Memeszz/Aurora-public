package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.friends.Friends;

public class FriendCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"friend", "friends", "f"};
    }

    @Override
    public String getSyntax() {
        return "friend <add | del> <Name>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args[0].equalsIgnoreCase("add")){
            if(Friends.isFriend(args[1])) {
                Wrapper.sendClientMessage(args[1] + " is already a friend!");
                return;
            }
            if(!Friends.isFriend(args[1])){
                Aurora.getInstance().friends.addFriend(args[1]);
                Wrapper.sendClientMessage("Added " + args[1] + " to friends list");
            }
        }
        if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove")){
            if(!Friends.isFriend(args[1])){
                Wrapper.sendClientMessage(args[1] + " is not a friend!");
                return;
            }
            if(Friends.isFriend(args[1])){
                Aurora.getInstance().friends.delFriend(args[1]);
                Wrapper.sendClientMessage("Removed " + args[1] + " from friends list");
            }
        }
    }
}
