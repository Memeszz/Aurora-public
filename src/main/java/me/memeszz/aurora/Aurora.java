package me.memeszz.aurora;

import me.memeszz.aurora.command.CommandManager;
import me.memeszz.aurora.event.EventProcessor;
import me.memeszz.aurora.gui.ClickGUI;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.config.*;
import me.memeszz.aurora.util.enemy.Enemies;
import me.memeszz.aurora.util.font.CFontRenderer;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.macro.MacroManager;
import me.memeszz.aurora.util.setting.SettingManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.stiff.pomelo.EventManager;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;

import java.awt.*;


@Mod(modid = Aurora.MODID, name = Aurora.FORGENAME, version = Aurora.MODVER, clientSideOnly = true)
public class Aurora {
    public static final String MODID = "aurora";
    public static String MODNAME = "Aurora";
    public static final String MODVER = "4.2";
    public static final String FORGENAME = "Aurora";
    public static final Logger log = LogManager.getLogger(MODNAME);
    public ClickGUI clickGui;
    public SettingManager settingsManager;
    public Friends friends;
    public ModuleManager moduleManager;
    public SaveModules saveModules;
    public MacroManager macroManager;
    EventProcessor eventProcessor;
    public LoadConfiguration loadConfiguration;
    public static CFontRenderer fontRenderer;
    public static CFontRenderer fontRendererBIG;
    public static Enemies enemies;
    private EventManager eventManager;
    public SaveConfiguration saveConfiguration;
    public LoadModules loadModules;
    @Mod.Instance
    public static Aurora INSTANCE;

    public Aurora() {
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //hwid();
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        fontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
        fontRendererBIG = new CFontRenderer(new Font("Caviar Dreams", 0, 20), true, true);


        settingsManager = new SettingManager();
        log.info("Settings initialized!");

        friends = new Friends();
        enemies = new Enemies();
        log.info("Friends and enemies initialized!");

        moduleManager = new ModuleManager();
        log.info("Modules initialized!");

        clickGui = new ClickGUI();
        log.info("ClickGUI initialized!");

        macroManager = new MacroManager();
        log.info("Macros initialized!");

        saveConfiguration = new SaveConfiguration();
        Runtime.getRuntime().addShutdownHook(new Stopper());
        log.info("Config Saved!");

        loadConfiguration = new LoadConfiguration();
        log.info("Config Loaded!");

        saveModules = new SaveModules();
        Runtime.getRuntime().addShutdownHook(new Stopper());
        log.info("Modules Saved!");

        loadModules = new LoadModules();
        log.info("Modules Loaded!");

        CommandManager.initCommands();
        log.info("Commands initialized!");

        log.info("Initialization complete!\n");
    }


    public EventManager getEventManager() {
        if (this.eventManager == null) {
            this.eventManager = new AnnotatedEventManager();
        }

        return this.eventManager;
    }


    public static Aurora getInstance() {
        return INSTANCE;
    }

}
