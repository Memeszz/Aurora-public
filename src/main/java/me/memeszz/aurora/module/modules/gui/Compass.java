package me.memeszz.aurora.module.modules.gui;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

/*
ForgeHax!!!!
 */
public class Compass extends Module {
  public Compass() {
    super("Compass", Category.Render, "Draws A Compass On Ur Screen Thanks ForgeHax");
  }

  Setting.d scale;

  public void setup() {
    scale = this.registerD("Radius", "Radius",3, 1, 5);
  }

  private static final double HALF_PI = Math.PI / 2;
  ScaledResolution resolution = new ScaledResolution(mc);

  private enum Direction {
    N,
    W,
    S,
    E
  }

  @Override
  public void onRender() {
    final double centerX = resolution.getScaledWidth() * 1.11;
    final double centerY = resolution.getScaledHeight_double() * 1.8;

    for (Direction dir : Direction.values()) {
      double rad = getPosOnCompass(dir);
      FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), dir.name(), (int) (centerX + getX(rad)), (int) (centerY + getY(rad)), dir == Direction.N ? new Color(255, 0, 0, 255).getRGB() : new Color(255, 255, 255, 255).getRGB());
    }
  }

  private double getX(double rad) {
    return Math.sin(rad) * (scale.getValue() * 10);
  }

  private double getY(double rad) {
    final double epicPitch = MathHelper.clamp(Wrapper.getRenderEntity().rotationPitch + 30f, -90f, 90f);
    final double pitchRadians = Math.toRadians(epicPitch); // player pitch
    return Math.cos(rad) * Math.sin(pitchRadians) * (scale.getValue() * 10);
  }

  // return the position on the circle in radians
  private static double getPosOnCompass(Direction dir) {
    double yaw = Math.toRadians(MathHelper.wrapDegrees(Wrapper.getRenderEntity().rotationYaw)); // player yaw
    int index = dir.ordinal();
    return yaw + (index * HALF_PI);
  }
}
