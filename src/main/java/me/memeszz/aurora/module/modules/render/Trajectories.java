package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.mixin.accessor.IRenderManager;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.colour.HueCycler;
import me.memeszz.aurora.util.math.TrajectoryCalculator;
import me.memeszz.aurora.util.render.RenderUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Trajectories extends Module {
    public Trajectories() {
        super("Trajectories", Category.Render);
    }

    ArrayList<Vec3d> positions = new ArrayList<>();
    HueCycler cycler = new HueCycler(100);

    @Override
    public void onWorldRender(RenderEvent event) {
        try {
            mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityLivingBase)
                    .map(entity -> (EntityLivingBase) entity)
                    .forEach(entity -> {
                positions.clear();
                TrajectoryCalculator.ThrowingType tt = TrajectoryCalculator.getThrowType(entity);
                if (tt == TrajectoryCalculator.ThrowingType.NONE) return;
                TrajectoryCalculator.FlightPath flightPath = new TrajectoryCalculator.FlightPath(entity, tt);

                while (!flightPath.isCollided()) {
                    flightPath.onUpdate();
                    positions.add(flightPath.position);
                }

                BlockPos hit = null;
                if (flightPath.getCollidingTarget() != null) hit = flightPath.getCollidingTarget().getBlockPos();

                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                if (hit != null){
                    RenderUtil.prepare(GL11.GL_QUADS);
                    GL11.glColor4f(1,1,1,.3f);
                    RenderUtil.drawBox(hit, 0x33ffffff, RenderUtil.FACEMAP.get(flightPath.getCollidingTarget().sideHit));
                    RenderUtil.release();
                }

                if (positions.isEmpty()) return;
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);

                GL11.glLineWidth(2F);
                if (hit != null)
                    GL11.glColor3f(1f, 1f, 1f);
                else
                    cycler.setNext();
                GL11.glBegin(GL11.GL_LINES);

                Vec3d a = positions.get(0);
                GL11.glVertex3d(a.x - ((IRenderManager) mc.getRenderManager()).getRenderPosX(), a.y - ((IRenderManager) mc.getRenderManager()).getRenderPosY(), a.z - ((IRenderManager) mc.getRenderManager()).getRenderPosZ());
                for (Vec3d v : positions) {
                    GL11.glVertex3d(v.x - ((IRenderManager) mc.getRenderManager()).getRenderPosX(), v.y - ((IRenderManager) mc.getRenderManager()).getRenderPosY(), v.z - ((IRenderManager) mc.getRenderManager()).getRenderPosZ());
                    GL11.glVertex3d(v.x - ((IRenderManager) mc.getRenderManager()).getRenderPosX(), v.y - ((IRenderManager) mc.getRenderManager()).getRenderPosY(), v.z - ((IRenderManager) mc.getRenderManager()).getRenderPosZ());
                    if (hit == null)
                        cycler.setNext();
                }

                GL11.glEnd();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);

                cycler.reset();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}