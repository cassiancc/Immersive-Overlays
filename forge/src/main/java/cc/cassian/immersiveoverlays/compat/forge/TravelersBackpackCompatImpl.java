package cc.cassian.immersiveoverlays.compat.forge;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import com.tiviacz.travelersbackpack.capability.CapabilityUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemStackHandler;

public class TravelersBackpackCompatImpl {

    public static void checkForImportantAccessories(Player player) {
        if (CapabilityUtils.isWearingBackpack(player)) {
            var backpackWrapper =
            //? if >1.20 {
             CapabilityUtils.getBackpackWrapper(player);
            //?} else {
            /*CapabilityUtils.getBackpackInv(player);
            *///?}
            if (backpackWrapper != null) {
                var inventory = backpackWrapper.
                //? if >1.20 {
                 inventory; 
                //?} else {
                /*getHandler();
                //?
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
    }
}
*/