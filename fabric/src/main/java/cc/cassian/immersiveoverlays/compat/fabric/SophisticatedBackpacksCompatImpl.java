package cc.cassian.immersiveoverlays.compat.fabric;

import cc.cassian.immersiveoverlays.overlay.OverlayHelpers;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;

import java.util.Optional;

public class SophisticatedBackpacksCompatImpl {

    public static void checkBackpackContents(ItemStack stack) {
        //? if >1.21 {
        Optional<IBackpackWrapper> backpackWrapper = BackpackWrapper.fromExistingData(stack);
        if (backpackWrapper.isPresent()) {
            InventoryHandler inventory = backpackWrapper.get().getInventoryHandler();
            for (SingleSlotStorage<ItemVariant> slot : inventory.getSlots()) {
                OverlayHelpers.isImportantItemOrContainer(slot.getResource().toStack());
            }
        }
        //?} else {
        /*if (stack.getItem() instanceof BackpackItem) {
            IBackpackWrapper backpackWrapper = new BackpackWrapper(stack);
            InventoryHandler inventory = backpackWrapper.getInventoryHandler();
            for (int i =0; i < inventory.getBaseSlotLimit(); i++) {
                OverlayHelpers.isImportantItemOrContainer(inventory.getStackInSlot(i));
            }
        }
        *///?}
    }
}
