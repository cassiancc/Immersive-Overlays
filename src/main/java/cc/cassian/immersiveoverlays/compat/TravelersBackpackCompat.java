package cc.cassian.immersiveoverlays.compat;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
//? if neoforge {
/*import com.tiviacz.travelersbackpack.capability.AttachmentUtils;
import com.tiviacz.travelersbackpack.inventory.BackpackWrapper;
import net.neoforged.neoforge.items.ItemStackHandler;
*///?} else if forge {
/*import com.tiviacz.travelersbackpack.capability.CapabilityUtils;
import net.minecraftforge.items.ItemStackHandler;
*///?} else {
import com.tiviacz.travelersbackpack.component.ComponentUtils;
//?}
import net.minecraft.world.entity.player.Player;

public class TravelersBackpackCompat {
    public static void checkForImportantAccessories(Player player) {
        //? if neoforge {
        /*if (AttachmentUtils.isWearingBackpack(player)) {
            BackpackWrapper backpackWrapper = AttachmentUtils.getBackpackWrapper(player);
            if (backpackWrapper != null) {
                ItemStackHandler inventory = backpackWrapper.inventory;
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
        *///?} else if forge {
        /*if (CapabilityUtils.isWearingBackpack(player)) {
            var backpackWrapper =
                    //? if >1.20 {
                    CapabilityUtils.getBackpackWrapper(player);
            //?} else {
            /^CapabilityUtils.getBackpackInv(player);
             ^///?}
            if (backpackWrapper != null) {
                var inventory = backpackWrapper.
                        //? if >1.20 {
                        inventory;
                //?} else {
                /^getHandler();
                 ^///?}
                for (int i =0; i < inventory.getSlots(); i++) {
                    OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
                }
            }
        }
        *///?} else {
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
                *///?}
            }
        }
        //?}
    }
}
