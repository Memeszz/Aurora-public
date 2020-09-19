package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.block.BlockInteractionHelper;
import me.memeszz.aurora.util.entity.EntityUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Comparator;

public class AutoBedCraft extends Module {

    private BlockPos tablePostition = null;
    private int delay;
    private boolean hasCrafted;
    private boolean firstSlot;
    private boolean secondSlot;
    private boolean thirdSlot;
    private boolean fourthSlot;
    private boolean fifthSlot;
    private boolean sixthSlot;
    private boolean openCTable;
    Item woolForCrafting = Item.getItemFromBlock(Blocks.WOOL);

    public AutoBedCraft() {
        super("AutoBedCraft", Category.Player, "Crafts Beds Automatically perfect for 1.13 and up servers");
    }

    public void onEnable() {
        tablePostition = null;
        hasCrafted = false;
        firstSlot = false;
        secondSlot = false;
        thirdSlot = false;
        fourthSlot = false;
        fifthSlot = false;
        sixthSlot = false;
        openCTable = false;
        delay = 0;
        //wasInTable = false;


            int tableSlot = getTableSlot();

            //why not hopefully stops a kick error if ctable not in hotbar too lazy to add another filter thing
            if (tableSlot == -1) {

            } else
            tablePostition = BlockInteractionHelper.getSphere(BlockInteractionHelper.GetLocalPlayerPosFloored(), 4.0f, 4, false, true, 0).stream()
                    .filter(this::IsValidBlockPos)
                    .min(Comparator.comparing(p_Pos -> EntityUtil.getDistanceOfEntityToBlock(mc.player, p_Pos)))
                    .orElse(null);



            if (tablePostition == null)
                return;

            mc.player.inventory.currentItem = tableSlot;
            mc.playerController.updateController();
            BlockInteractionHelper.placeBlockScaffold(tablePostition);
        }


    @Listener
    public void onUpdate(UpdateEvent event) {
        delay++;
        if (mc.player == null || mc.world == null)
            this.disable();
        // Tells the server hey we want to open this screen aka the crafting table screen there is a delay so we can place then open it and not attempt to open then place
        if (delay > 3 && !(mc.currentScreen instanceof GuiCrafting) && !openCTable) {
            mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(tablePostition, EnumFacing.NORTH, EnumHand.MAIN_HAND, 0, 0, 0));
            openCTable = true;
        }
        // we're checking to see if we have started to craft before and if were in a crafting table
        if ( mc.currentScreen instanceof GuiCrafting && !hasCrafted) {
            // this is getting all of our slots for our inventory 1-9 is reserved for droppeds ctabes etc while its either 9-46 or 10-46 is our inv
            for (int i = 9; i < 46; i++) {
                ItemStack stacks = mc.player.openContainer.getSlot(i).getStack();

                if (stacks == ItemStack.EMPTY)
                    continue;
                Item woolForCrafting = Item.getItemFromBlock(Blocks.WOOL);
                Item planksForCrafting = Item.getItemFromBlock(Blocks.PLANKS);

                // here we're saying hey if this block is wool then u need to place it in these slots in the c table
                if (stacks.getItem() == woolForCrafting) {
                    // wool first slot

                    if (delay > 0 && !firstSlot) {
                        // here we are right clicking on the first stack of wool in our inventory this is why 32 gets placed in the first slot were only spliting it and picking it up rn
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, i, 1, ClickType.PICKUP, mc.player);
                        //Now were getting the stack of wool were holding of 32 and placing it in the first slot of the cTable
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, 1, 0, ClickType.PICKUP, mc.player);
                        firstSlot = true;
                    }
                    // wool second slot
                    if (delay > 4 && !secondSlot) {
                        // We now have 32 in our inv and 32 in our table i made it so it will only craft with blocks from our inv so it right clicks on the stack of 32 in out inv
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, i, 1, ClickType.PICKUP, mc.player);
                        // next it places this now stack of 16 that we're holding in the second slot of the c table
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, 2, 0, ClickType.PICKUP, mc.player);
                        // wool third slot
                        secondSlot = true;
                    }
                    if (delay > 8 && !thirdSlot) {
                        // here were doing it a bit different because we want to craft the most that we can we so instead of right clicking to split it left clicks to pick it up so we get 16
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, i, 0, ClickType.PICKUP, mc.player);
                        // places in the third slot of the ctable nothing special
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, 3, 0, ClickType.PICKUP, mc.player);
                        thirdSlot = true;
                    }
                }
                // here we're saying hey if this block is planks then u need to place it in these slots in the c table

                if (stacks.getItem() == planksForCrafting) {
                    // plank first slot
                    if (delay > 12 && !fourthSlot) {
                        // same as Wool just dif block but same code just dif crafting slots
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, i, 1, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, 4, 0, ClickType.PICKUP, mc.player);
                        fourthSlot = true;
                    }
                    // plank second slot
                    if (delay > 16 && !fifthSlot) {
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, i, 1, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, 5, 0, ClickType.PICKUP, mc.player);
                        // plank third slot
                        fifthSlot = true;
                    }
                    // ive added delays because if you do it to fast the server will only move like 2 items in the inventory there can be a setting for this or i could come back and optimize which i will do :D
                    if (delay > 20 && !sixthSlot) {
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, i, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(((GuiContainer) mc.currentScreen).inventorySlots.windowId, 6, 0, ClickType.PICKUP, mc.player);
                        sixthSlot = true;
                        hasCrafted = true;
                    }

                }


            }
        }
            if (firstSlot && secondSlot && thirdSlot && fourthSlot && fifthSlot && sixthSlot && hasCrafted && !(mc.currentScreen instanceof GuiCrafting)) {
                // gotta wait for bullet to fix this. this will toggle the module off once the auto crafting is complete
                this.disable();
                // this.disable is broken With Alpine

        }

    }

// finds valid places to place the CTable
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        IBlockState state = mc.world.getBlockState(p_Pos);

        if (state.getBlock() == Blocks.AIR && mc.world.getBlockState(p_Pos.up()).getBlock() == Blocks.AIR) {
            BlockInteractionHelper.ValidResult result = BlockInteractionHelper.valid(p_Pos);

            return result == BlockInteractionHelper.ValidResult.Ok;
        }

        return false;
    }
// gets the hotbar slot the CTable is in
    private int getTableSlot()
    {
        for (int I = 0; I < 9; ++I) {
            ItemStack stack = mc.player.inventory.getStackInSlot(I);
            if (stack != ItemStack.EMPTY) {
                if (Item.getIdFromItem(stack.getItem()) == 58)
                    return I;
                }
            }
        return -1;

    }
    //
}
