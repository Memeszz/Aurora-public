package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.module.modules.render.BlockHighlight;
import me.memeszz.aurora.util.render.RenderUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.client.renderer.RenderGlobal.drawSelectionBoundingBox;
import static org.lwjgl.opengl.GL11.*;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {

    @Shadow
    private WorldClient world;

    /**
     * @author Memeszz
     */

    @Overwrite
    public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
        if (ModuleManager.isModuleEnabled("BlockHighlight")) {
            if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
                RenderUtil.prepare(GL11.GL_QUADS);
                glEnable(GL_LINE_SMOOTH);
                BlockPos blockpos = movingObjectPositionIn.getBlockPos();
                IBlockState iblockstate = this.world.getBlockState(blockpos);
                if (iblockstate.getMaterial() != Material.AIR && this.world.getWorldBorder().contains(blockpos)) {
                    double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                    double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                    double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                    drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox(this.world, blockpos).grow(0.0020000000949949026D).offset(-d3, -d4, -d5), BlockHighlight.red.getValue() / 255F, BlockHighlight.green.getValue() / 255F, BlockHighlight.blue.getValue() / 255F, BlockHighlight.alpha.getValue() / 255F);

                    drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox(this.world, blockpos).grow(0.0020000000949949026D).offset(-d3, -d4, -d5), BlockHighlight.red.getValue() / 255F, BlockHighlight.green.getValue() / 255F, BlockHighlight.blue.getValue() / 255F, BlockHighlight.alpha.getValue() / 255F);
                }
                RenderUtil.release();
                glDisable(GL_LINE_SMOOTH);
            }

        }

    }
}
