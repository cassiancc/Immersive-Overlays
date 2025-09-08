package cc.cassian.immersiveoverlays.compat.neoforge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.tiviacz.travelersbackpack.capability.AttachmentUtils;
import com.tiviacz.travelersbackpack.inventory.BackpackWrapper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TravelersBackpackCompatImpl {

    public static void checkForImportantAccessories(Player player) {
        if (AttachmentUtils.isWearingBackpack(player)) {
            BackpackWrapper backpackWrapper = AttachmentUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                ItemStackHandler inventory = backpackWrapper.inventory;
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
    }
}
