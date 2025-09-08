package cc.cassian.immersiveoverlays.compat.forge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;

public class SophisticatedBackpacksCompatImpl {

    public static void checkBackpackContents(ItemStack stack) {
        if (stack.getItem() instanceof BackpackItem) {
            IBackpackWrapper backpackWrapper = new BackpackWrapper(stack);
            ItemStackHandler inventory = backpackWrapper.getInventoryHandler();
            for (int i =0; i < inventory.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
            }
        }
    }
}
