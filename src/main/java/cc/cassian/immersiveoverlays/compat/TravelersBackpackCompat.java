package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
//? if neoforge {
/*import com.tiviacz.travelersbackpack.capability.AttachmentUtils;
import com.tiviacz.travelersbackpack.inventory.BackpackWrapper;
*///?} else if forge {
import com.tiviacz.travelersbackpack.capability.CapabilityUtils;
import net.minecraftforge.items.ItemStackHandler;
//?} else if fabric && <26 {
/*import com.tiviacz.travelersbackpack.component.ComponentUtils;
*///?}
import net.minecraft.world.entity.player.Player;

public class TravelersBackpackCompat {
    public static void checkForImportantAccessories(Player player) {
        //? if neoforge {
        /*if (AttachmentUtils.isWearingBackpack(player)) {
            BackpackWrapper backpackWrapper = AttachmentUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                var inventory = backpackWrapper.getStorage();
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
        *///?} else if forge {
        if (CapabilityUtils.isWearingBackpack(player)) {
            var backpackWrapper = CapabilityUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                var inventory = backpackWrapper.inventory;
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
        //?} else if fabric && <26 {
        /*if (ComponentUtils.isWearingBackpack(player)) {
            var backpackWrapper = ComponentUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                var inventory = backpackWrapper.getStorage();
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
        *///?}
    }
}
