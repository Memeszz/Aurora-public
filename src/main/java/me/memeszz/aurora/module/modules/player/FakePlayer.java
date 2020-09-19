package me.memeszz.aurora.module.modules.player;

import com.mojang.authlib.GameProfile;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakePlayer extends Module {
    private List<Integer> fakePlayerIdList = null;
    public FakePlayer() {

        super("FakePlayer", Category.Player);
    }
    Setting.mode mode;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Single");
        modes.add("Multi");
        mode = this.registerMode("Mode","Mode", modes, "Single");
    }

    private static final String[][] fakePlayerInfo =

            {
                    {"66666666-6666-6666-6666-666666666600", "nigga0", "0", "0"},
                    {"66666666-6666-6666-6666-66666666660", "nigga1", "3", "0"},
                    {"66666666-6666-6666-6666-666666666", "nigga2", "6", "0"},
                    {"66666666-6666-6666-6666-66666660", "nigga3", "9", "0"},
            };

    @Override
    protected void onEnable() {

        if (mc.player == null || mc.world == null) {
            this.disable();
            return;
        }

        fakePlayerIdList = new ArrayList<>();

        int entityId = -101;

        for (String[] data : fakePlayerInfo) {

            if (mode.getValue().equals("Single")) {
                addFakePlayer(data[0], data[1], entityId, 0, 0);
            }
            if (mode.getValue().equals("Multi")) {
                addFakePlayer(data[0], data[1], entityId, Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            }

            entityId--;

        }

    }

    private void addFakePlayer(String uuid, String name, int entityId, int offsetX, int offsetZ) {

        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString(uuid), name));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.posX = fakePlayer.posX + offsetX;
        fakePlayer.posZ = fakePlayer.posZ + offsetZ;
        mc.world.addEntityToWorld(entityId, fakePlayer);
        fakePlayerIdList.add(entityId);

    }

    @Override
    public void onUpdate() {

        if (fakePlayerIdList == null || fakePlayerIdList.isEmpty() ) {
            this.disable();
        }

    }

    @Override
    protected void onDisable() {

        if (mc.player == null || mc.world == null) {
            return;
        }

        if (fakePlayerIdList != null) {
            for (int id : fakePlayerIdList) {
                mc.world.removeEntityFromWorld(id);
            }
        }

    }

}