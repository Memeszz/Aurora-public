package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class DonkeyAlert extends Module
{

    Setting.b donkeyAlert;
    Setting.b muleAlert;
    Setting.b llamaAlert;
    Setting.b horseAlert;
    Setting.mode mode;
    private int antiSpam;

    public DonkeyAlert()
    {
        super("DonkeyAlert", Category.Misc, "Announces the location of any donkeys in the players render distance");
    }

   /* private Setting<Boolean> donkeyAlert = register(Settings.b("Donkey", true));
    private Setting<Boolean> muleAlert = register(Settings.b("Mule", true));
    private Setting<Boolean> llamaAlert = register(Settings.b("Llama", true));
    private Setting<Boolean> horseAlert = register(Settings.b("Horse", false));

    private Setting<DonkeyAlert.colour> mode = register(Settings.e("Colour", DonkeyAlert.colour.DARK_PURPLE)); */

    public void setup()
    {

        donkeyAlert = this.registerB("DonkeyAlert", "DonkeyAlert", true);
        muleAlert = this.registerB("MuleAlert", "MuleAlert", true);
        llamaAlert = this.registerB("LlamaAlert", "LlamaAlert", true);
        horseAlert = this.registerB("HorseAlert", "HorseAlert", false);

        ArrayList<String> modes = new ArrayList<>();
        modes.add("BLACK");
        modes.add("RED");
        modes.add("AQUA");
        modes.add("BLUE");
        modes.add("GOLD");
        modes.add("GRAY");
        modes.add("WHITE");
        modes.add("GREEN");
        modes.add("YELLOW");
        modes.add("DARK_RED");
        modes.add("DARK_AQUA");
        modes.add("DARK_BLUE");
        modes.add("DARK_GRAY");
        modes.add("DARK_GREEN");
        modes.add("DARK_PURPLE");
        modes.add("LIGHT_PURPLE");


        mode = this.registerMode("Mode", "Mode", modes, "DARK_RED");

    }

    @Listener
    public void onUpdate(UpdateEvent event)
    {
        antiSpam++;

        for (Entity e : Minecraft.getMinecraft().world.loadedEntityList)
        {
            if (e instanceof EntityDonkey && donkeyAlert.getValue())
            {
                if (antiSpam >= 100)
                {
                    Wrapper.sendClientMessage(colorchoice() + " Found Donkey!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);
                    antiSpam = -600;
                }
            }
            if (e instanceof EntityMule && muleAlert.getValue())
            {
                if (antiSpam >= 100)
                {
                    Wrapper.sendClientMessage(colorchoice() + " Found Mule!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);
                    antiSpam = -600;
                }

            }
            if (e instanceof EntityLlama && llamaAlert.getValue())
            {
                if (antiSpam >= 100)
                {
                    Wrapper.sendClientMessage(colorchoice() + " Found Llama!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);

                    antiSpam = -600;
                }

            }
            if (e instanceof EntityHorse && horseAlert.getValue())
            {
                if (antiSpam >= 100)
                {
                    Wrapper.sendClientMessage(colorchoice() + " Found Horse!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);

                    antiSpam = -600;
                }

            }

        }
    }

    private String colorchoice()
    {
        switch (mode.getValue())
        {
            case "BLACK":
                return "§0";
            case "RED":
                return "§c";
            case "AQUA":
                return "§b";
            case "BLUE":
                return "§9";
            case "GOLD":
                return "§6";
            case "GRAY":
                return "§7";
            case "WHITE":
                return "§f";
            case "GREEN":
                return "§a";
            case "YELLOW":
                return "§e";
            case "DARK_RED":
                return "§4";
            case "DARK_AQUA":
                return "§3";
            case "DARK_BLUE":
                return "§1";
            case "DARK_GRAY":
                return "§8";
            case "DARK_GREEN":
                return "§2";
            case "DARK_PURPLE":
                return "§5";
            case "LIGHT_PURPLE":
                return "§d";
            default:
                return "";
        }


    }


}





