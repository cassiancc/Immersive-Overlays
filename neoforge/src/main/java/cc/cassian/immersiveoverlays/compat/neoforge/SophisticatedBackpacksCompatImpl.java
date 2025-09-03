package cc.cassian.immersiveoverlays.compat.neoforge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;

import java.util.Optional;

public class SophisticatedBackpacksCompatImpl {

    public static void checkBackpackContents(ItemStack stack) {
        Optional<IBackpackWrapper> backpackWrapper = BackpackWrapper.fromExistingData(stack);
        if (backpackWrapper.isPresent()) {
            ItemStackHandler inventory = backpackWrapper.get().getInventoryHandler();
            for (int i =0; i < inventory.getSlots(); i++) {
                OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
            }
        }
    }
}
