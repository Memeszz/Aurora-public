package me.memeszz.aurora.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.friends.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class MiddleClickFriends extends Module {
    public MiddleClickFriends() {
        super("MCF", Category.Misc, "Middle click players to add / remove them as a friend");
    }

    @Listener
    public void mouseInputEvent(InputEvent.MouseInputEvent event) {
        if (mc.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.ENTITY) && mc.objectMouseOver.entityHit instanceof EntityPlayer && Mouse.getEventButton() == 2) {
            if (!mc.player.isOnLadder() && Friends.isFriend(mc.objectMouseOver.entityHit.getName())) {
                Aurora.getInstance().friends.delFriend(mc.objectMouseOver.entityHit.getName());
                Wrapper.sendClientMessage(ChatFormatting.RED + "Removed " + mc.objectMouseOver.entityHit.getName() + " from friends list");
            } else {
                Aurora.getInstance().friends.addFriend(mc.objectMouseOver.entityHit.getName());
                Wrapper.sendClientMessage(ChatFormatting.AQUA + "Added " + mc.objectMouseOver.entityHit.getName() + " to friends list");
            }
        }
    }

}