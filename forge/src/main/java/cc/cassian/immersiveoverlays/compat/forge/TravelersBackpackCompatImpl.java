package cc.cassian.immersiveoverlays.compat.forge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.tiviacz.travelersbackpack.capability.CapabilityUtils;
import com.tiviacz.travelersbackpack.inventory.BackpackWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemStackHandler;

public class TravelersBackpackCompatImpl {

    public static void checkForImportantAccessories(Player player) {
        if (CapabilityUtils.isWearingBackpack(player)) {
            BackpackWrapper backpackWrapper = CapabilityUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                ItemStackHandler inventory = backpackWrapper.inventory;
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
    }
}
