package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.mixin.accessor.IRenderManager;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.block.BlockInteractionHelper;
import me.memeszz.aurora.util.entity.EntityUtil;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.math.Timah;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCrystal extends Module {

    public AutoCrystal() {
        super("AutoCrystal", Category.Combat, "Places and breaks crystals on nearby players");
    }

    Setting.b autoSwitch;
    Setting.d walls;
    Setting.b place;
    Setting.d range;
    Setting.i facePlace;
    Setting.b spoofRotations;
    Setting.d maxSelfDmg;
    Setting.d minDmg;
    Setting.d placeRange;
    Setting.i hitDelay;
    Setting.b renderDamage;
    Setting.b rainbow;
    Setting.d enemyRange;
    Setting.i espR;
    Setting.i espG;
    Setting.i espB;
    Setting.b rotate;
    Setting.i espA;
    Setting.b slabRender;
    static Setting.mode mode;
    Setting.mode daThing;

    public void setup() {
        ArrayList<String> placeModes = new ArrayList<>();
        placeModes.add("Normal");
        placeModes.add("1.13");
        ArrayList<String> modes = new ArrayList<>();
        modes.add("BREAKPLACE");
        modes.add("PLACEBREAK");
        daThing = registerMode("huh", "huh", modes, "BREAKPLACE");
        mode = registerMode("PlaceMode", "PlaceMode", placeModes, "Normal");
        place = registerB("Place", "Place", true);
        rotate = registerB("Rotate", "Rotate", true);
        spoofRotations = registerB("SpoofRotations", "SpoofRotations", true);
        autoSwitch = registerB("AutoSwitch", "AutoSwitch", false);
        hitDelay = registerI("HitDelayMS", "HitDelayMS", 0, 0, 600);
        range = registerD("HitRange", "HitRange", 4.5, 0, 6);
        walls = registerD("WallRange", "WallRange", 3.5, 0, 4);
        enemyRange = registerD("EnemyRange", "EnemyRange", 13, 5, 15);
        placeRange = registerD("PlaceRange", "PlaceRange", 4.5, 0, 6);
        maxSelfDmg = registerD("MaxSelfDamage", "MaxSelfDamage", 8, 0, 36);
        minDmg = registerD("MinDmg", "MinDmg", 4, 0, 20);
        facePlace = registerI("FaceplaceHp", "FaceplaceHp", 8, 0, 36);
        espR = registerI("Red", "Red", 255, 0, 255);
        espG = registerI("Green", "Green", 0, 0, 255);
        espB = registerI("Blue", "Blue", 255, 0, 255);
        espA = registerI("Alpha", "Alpha", 28, 0, 255);
        slabRender = registerB("SlabRender", "SlabRender", true);
        rainbow = registerB("Rainbow", "Rainbow", true);
        renderDamage = registerB("RenderDamage", "RenderDamage", false);
    }

    public static EntityPlayer target2;
    BlockPos render;
    BlockPos pos = null;
    String damageString;
    Timah breakTimer = new Timah();
    boolean mainhand = false;
    boolean offhand = false;

    @Listener
    public void onPacketRecieve(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (Entity e : mc.world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            e.setDead();
                        }
                    }
                }
            }
        }
    }


    public void onTick() {
        doDaTHING();
    }

    void doDaTHING() {
        switch (daThing.getValue()) {
            case "BREAKPLACE" :
                daThing();
                gloop();
                break;
            case "PLACEBREAK" :
                gloop();
                daThing();
        }
    }

    void daThing() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);
        if (crystal != null && mc.player.getDistance(crystal) <= range.getValue()) {
            if (breakTimer.passedMs(hitDelay.getValue())) {
                mc.playerController.attackEntity(mc.player, crystal);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                breakTimer.reset();
            }
        }
    }


    void gloop() {
        double dmg = .5;
        mainhand = (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL);
        offhand = (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        final List<EntityPlayer> entities = mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.player && !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList());
        for (EntityPlayer entity2 : entities) {
            if (entity2.getHealth() <= 0.0f || mc.player.getDistance(entity2) > enemyRange.getValue())
                continue;
            for (final BlockPos blockPos : possiblePlacePositions((float) placeRange.getValue(), true)) {
                final double d = calcDmg(blockPos, entity2);
                final double self = calcDmg(blockPos, mc.player);
                if (d < minDmg.getValue() && entity2.getHealth() + entity2.getAbsorptionAmount() > facePlace.getValue() || maxSelfDmg.getValue() <= self || d <= dmg)
                    continue;
                dmg = d;
                pos = blockPos;
                target2 = entity2;
            }
        }

        if (dmg == .5) {
            render = null;
            return;
        }

        if (place.getValue()) {
            if (offhand || mainhand) {
                render = pos;
                placeCrystalOnBlock(pos, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                damageString = String.valueOf(dmg);
            }
        }
    }

    public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand) {
        RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)pos.getX() + 0.5, (double)pos.getY() - 0.5, (double)pos.getZ() + 0.5));
        EnumFacing facing = result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }


    public void onWorldRender(RenderEvent event) {
        if (this.render != null && target2 != null) {
            final float[] hue = {(System.currentTimeMillis() % (360 * 7) / (360f * 7))};
            int rgb = Color.HSBtoRGB(hue[0], 1, 1);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;
            final AxisAlignedBB bb = new AxisAlignedBB(render.getX() - mc.getRenderManager().viewerPosX, render.getY() - mc.getRenderManager().viewerPosY + 1, render.getZ() - mc.getRenderManager().viewerPosZ, render.getX() + 1 - mc.getRenderManager().viewerPosX, render.getY() + (slabRender.getValue() ? 1.1 : 0) - mc.getRenderManager().viewerPosY, render.getZ() + 1 - mc.getRenderManager().viewerPosZ);
            if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                RenderUtil.drawESP(bb, rainbow.getValue() ? r : espR.getValue(), rainbow.getValue() ? g : espG.getValue(), rainbow.getValue() ? b : espB.getValue(), espA.getValue());
                RenderUtil.drawESPOutline(bb, rainbow.getValue() ? r : espR.getValue(), rainbow.getValue() ? g : espG.getValue(), rainbow.getValue() ? b : espB.getValue(), 255f, 1f);
                if (renderDamage.getValue()) {
                    final double posX = render.getX() - ((IRenderManager) mc.getRenderManager()).getRenderPosX();
                    final double posY = render.getY() - ((IRenderManager) mc.getRenderManager()).getRenderPosY();
                    final double posZ = render.getZ() - ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
                    RenderUtil.renderTag(damageString, posX + 0.5, posY - 0.3, posZ + 0.5, new Color(255, 255, 255, 255).getRGB());
                }
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }

    public float calcDmg(BlockPos b, EntityPlayer target) {
        return EntityUtil.calculateDamage(b.getX() + .5, b.getY() + 1, b.getZ() + .5, target);
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static List<BlockPos> possiblePlacePositions(float placeRange, boolean specialEntityCheck) {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(BlockInteractionHelper.getSphere(getPlayerPos(), placeRange, (int) placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, specialEntityCheck)).collect(Collectors.toList()));
        return positions;
    }

    public static boolean canPlaceCrystal(BlockPos blockPos, boolean specialEntityCheck) {
        block7: {
            BlockPos boost = blockPos.add(0, 1, 0);
            BlockPos boost2 = blockPos.add(0, 2, 0);
            try {
                if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
                if (!mode.getValue().equalsIgnoreCase("1.13")) {
                    if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
                        return false;
                    }
                }
                else {
                    if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
                        return false;
                    }
                }
                if (specialEntityCheck) {
                    for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost))) {
                        if (entity instanceof EntityEnderCrystal) continue;
                        return false;
                    }
                    for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2))) {
                        if (entity instanceof EntityEnderCrystal) continue;
                        return false;
                    }
                    break block7;
                }
                return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
            }
            catch (Exception ignored) {
                return false;
            }
        }
        return true;
    }


    public void onDisable() {
        render = null;
        target2 = null;
    }


    @Override
    public String getHudInfo() {
        if (target2 != null) {
            return "\u00A77[\u00A7a" + target2.getName() + "\u00A77]";
        } else {
            return "\u00A77[\u00A7c" + "No target!" + "\u00A77]";
        }
    }
}