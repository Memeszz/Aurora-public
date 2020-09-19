package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.event.events.GuiScreenDisplayedEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.config.Stopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public abstract class MixinMinecraft implements IMinecraft {

    @Accessor @Override public abstract Timer getTimer();
    @Accessor @Override public abstract void setSession(Session session);
    @Accessor @Override public abstract void setRightClickDelayTimer(int delay);
    @Accessor @Override public abstract Session getSession();
  //  @Accessor @Override public abstract void clickMouse();
    @Accessor @Override public abstract ServerData getCurrentServerData();

    @Shadow public EntityPlayerSP player;
    @Shadow public PlayerControllerMP playerController;

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "shutdown()V", at = @At("HEAD")) // saves the config when the game shuts down
    public void saveSettingsOnShutdown(CallbackInfo ci) {
        Stopper.saveConfig();
        System.out.println("Saved Aurora config!");
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
            GuiScreenDisplayedEvent screenEvent = new GuiScreenDisplayedEvent(guiScreenIn);
            Aurora.getInstance().getEventManager().dispatchEvent(screenEvent);
    }

    @Redirect(method = "sendClickBlockToController", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActive(EntityPlayerSP player){
        if(ModuleManager.isModuleEnabled("MultiTask")) return false;
        return this.player.isHandActive();
    }

    @Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean isHittingBlock(PlayerControllerMP playerControllerMP){
        if(ModuleManager.isModuleEnabled("MultiTask")) return false;
        return this.playerController.getIsHittingBlock();
    }


    //too fast
    /*@Inject(method = "runTick", at = @At("HEAD"))
      public void runLoop(CallbackInfo callbackInfo) {
      Aurora.getInstance().getEventManager().dispatchEvent(new TickEvent(EventStageable.EventStage.POST));
    }
     */

}
