package cc.cassian.immersiveoverlays.compat.fabric;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.tiviacz.travelersbackpack.component.ComponentUtils;
import com.tiviacz.travelersbackpack.inventory.BackpackWrapper;
import com.tiviacz.travelersbackpack.inventory.handler.ItemStackHandler;
import net.minecraft.world.entity.player.Player;

public class TravelersBackpackCompatImpl {

    public static void checkForImportantAccessories(Player player) {
        if (ComponentUtils.isWearingBackpack(player)) {
            BackpackWrapper backpackWrapper = ComponentUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                ItemStackHandler inventory = backpackWrapper.inventory;
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
    }
}
