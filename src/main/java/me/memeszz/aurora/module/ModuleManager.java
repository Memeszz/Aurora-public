package me.memeszz.aurora.module;

import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.module.modules.combat.*;
import me.memeszz.aurora.module.modules.gui.*;
import me.memeszz.aurora.module.modules.misc.*;
import me.memeszz.aurora.module.modules.movement.*;
import me.memeszz.aurora.module.modules.player.*;
import me.memeszz.aurora.module.modules.render.*;
import me.memeszz.aurora.module.modules.world.*;
import me.memeszz.aurora.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ModuleManager {
    public static ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList<>();
        /**
         * Combat
         */
        addMod(new AutoCrystal());
        addMod(new OffHandCrystal());
        addMod(new AutoArmour());
        addMod(new AutoTotem());
        addMod(new AutoEchest());
        addMod(new AutoTrap());
        addMod(new OffHandGap());
        addMod(new TotemPopCounter());
        addMod(new Criticals());
        addMod(new KillAura());
        addMod(new MultiTask());
        addMod(new Surround());
        addMod(new HoleFill());
        addMod(new AutoWeb());
        addMod(new EzPearl());
        addMod(new FootXp());
        /**
         * Player
         */
        addMod(new AutoBedCraft());
        addMod(new AntiVoid());
        addMod(new AutoReplanish());
        addMod(new Blink());
        addMod(new FakePlayer());
        addMod(new FastUse());
        addMod(new NoMiningTrace());
        addMod(new NoSwing());
        addMod(new PortalGodMode());
        addMod(new SpeedMine());
        /**
         * Movement
         */
        addMod(new FastSwim());
        addMod(new HoleTP());
        addMod(new IceSpeed());
        addMod(new NoSlow());
        addMod(new Step());
        addMod(new ReverseStep());
        addMod(new Sprint());
        addMod(new Speed());
        addMod(new FastWebFall());
        addMod(new Velocity());
        /**
         * Misc
         */
        addMod(new AutoRespawn());
        addMod(new Lagger());
        addMod(new BuildHeight());
        addMod(new ExtraTab());
        addMod(new ChatSuffix());
        addMod(new DonkeyAlert());
        addMod(new InventoryMove2b());
        addMod(new GuiMove());
        addMod(new AutoCrash());
        addMod(new MiddleClickFriends());
        addMod(new LogoutCoords());
        /**
         * World
         */
        addMod(new Announcer());
        addMod(new AutoGG());
        addMod(new BetterChat());
        addMod(new ChatTimeStamps());
        addMod(new str2detect());
        addMod(new ToggleMsgs());
        addMod(new VisualRange());
        addMod(new Welcomer());
        /**
         * Render
         */
        addMod(new ESP());
        addMod(new AntiFog());
        addMod(new BlockHighlight());
        addMod(new Brightness());
        addMod(new CameraClip());
        addMod(new LowHand());
        addMod(new TargetHud());
        addMod(new Fov());
        addMod(new HoleESP());
        addMod(new VoidESP());
        addMod(new EntityESP());
        addMod(new NameTags());
        addMod(new NoLag());
        addMod(new NoRender());
        addMod(new ShulkerPreview());
        addMod(new SkyColor());
        addMod(new Trajectories());
        addMod(new OldChangeItemAnim());
        /**
         * Gui
         */
        addMod(new ClickGuiModule());
        addMod(new DurabiltyWarning());
        addMod(new Hud());
        addMod(new Compass());
        addMod(new Pvpinfo());
        addMod(new RainbowOffset());
        addMod(new PotionEffects());
        addMod(new InvPreview());

    }
//
    public static void addMod(Module m){
        modules.add(m);
    }

    public static void onUpdate() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public static void onTick() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onTick);
    }

    public static void onRender() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onRender);
    }

    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("aurora");

        Minecraft.getMinecraft().profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();

        GlStateManager.glLineWidth(1f);
        Vec3d renderPos = RenderUtil.getInterpolatedPos(Minecraft.getMinecraft().player, event.getPartialTicks());

        RenderEvent e = new RenderEvent(RenderUtil.INSTANCE, renderPos, event.getPartialTicks());
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(Module::isEnabled).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1f);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        RenderUtil.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }


    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesInCategory(Module.Category c){
        return (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList());
    }

    public static void onBind(int key) {
        if (key == 0) return;
        modules.forEach(module -> {
            if(module.getBind() == key){
                module.toggle();
            }
        });
    }

    public static Module getModuleByName(String name){
        return getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static boolean isModuleEnabled(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        assert m != null;
        return m.isEnabled();
    }

    public static boolean isModuleEnabled(Module m){
        return m.isEnabled();
    }

}
