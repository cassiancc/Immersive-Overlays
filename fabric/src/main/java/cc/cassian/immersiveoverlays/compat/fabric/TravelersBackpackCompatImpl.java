package cc.cassian.immersiveoverlays.compat.fabric;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.tiviacz.travelersbackpack.component.ComponentUtils;
import net.minecraft.world.entity.player.Player;

public class TravelersBackpackCompatImpl {

    public static void checkForImportantAccessories(Player player) {
        if (ComponentUtils.isWearingBackpack(player)) {
            var backpackWrapper =
            //? if >1.20 {
             ComponentUtils.getBackpackWrapper(player);
            //?} else {
            /*ComponentUtils.getBackpackInv(player);
            *///?}
            if (backpackWrapper != null) {
                var inventory = backpackWrapper.
                //? if >1.20 {
                 inventory;
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
                
                //?} else {
                /*getInventory();
                for (int i =0; i < inventory.getContainerSize(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getItem(i));
                }
                //?
            }
        }
    }
}
*/